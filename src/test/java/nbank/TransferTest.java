package nbank;

import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import generators.RandomData;

import java.util.List;
import java.util.stream.Stream;

import lombok.SneakyThrows;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import requests.AccountsRequester;
import requests.CustomerRequester;
import requests.LoginRequester;
import requests.UserRequester;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class TransferTest extends BaseTest {

    private AllUserInfo allUserInfo1 = new AllUserInfo();
    private AllUserInfo allUserInfo2 = new AllUserInfo();
    private ObjectMapper objectMapper;


    @BeforeEach
    void setupTest() {
        objectMapper = new ObjectMapper();
        CreateUserRequest createUserRequest1 = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        CreateUserRequest createUserRequest2 = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();

        allUserInfo1.setCreateUserResponse(new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.requestReturnsCreated())
                .createUser(createUserRequest1)
                .extract().as(CreateUserResponse.class));
        allUserInfo2.setCreateUserResponse(new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.requestReturnsCreated())
                .createUser(createUserRequest2)
                .extract().as(CreateUserResponse.class));

        allUserInfo1.getCreateUserResponse().setPassword(createUserRequest1.getPassword());
        allUserInfo2.getCreateUserResponse().setPassword(createUserRequest2.getPassword());

        AuthLoginRequest authLoginRequest1 = AuthLoginRequest.builder()
                .username(allUserInfo1.getCreateUserResponse().getUsername())
                .password(allUserInfo1.getCreateUserResponse().getPassword())
                .build();

        AuthLoginRequest authLoginRequest2 = AuthLoginRequest.builder()
                .username(allUserInfo2.getCreateUserResponse().getUsername())
                .password(allUserInfo2.getCreateUserResponse().getPassword())
                .build();

        allUserInfo1.setAuthToken(new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest1)
                .extract().header("Authorization"));
        allUserInfo2.setAuthToken(new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest2)
                .extract().header("Authorization"));

        for (var u : List.of(allUserInfo1, allUserInfo2)) {
            new AccountsRequester(RequestSpecs.userSpec(u.getAuthToken()), ResponseSpecs.requestReturnsCreated())
                    .createAccount();
            new AccountsRequester(RequestSpecs.userSpec(u.getAuthToken()), ResponseSpecs.requestReturnsCreated())
                    .createAccount();
            u.setCreateUserResponse(new CustomerRequester(RequestSpecs.userSpec(u.getAuthToken()), ResponseSpecs.requestReturnsOk())
                    .getCustomerProfile()
                    .extract()
                    .body()
                    .as(CreateUserResponse.class));
        }
    }

//  - Позитив: Перевод между своими существующими аккаунтами, баланс 10000, перевод 1
//  - Позитив: Перевод между своими существующими аккаунтами, баланс 10000, перевод 10000
//  - Позитив: Перевод со своего аккаунт на чужой существующий аккаунт, баланс 10000, перевод 1
//  - Позитив: Перевод со своего аккаунт на чужой существующий аккаунт, баланс 10000, перевод 10000

    @SneakyThrows
    @ValueSource(doubles = {0.1, 10000.})
    @ParameterizedTest
    void transferBetweenOwnerAccountsShouldReturnSuccess(double amount) {
        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
                .id(allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .balance(5000D)
                .build();

        AccountsTransferRequest accountsTransferRequest = AccountsTransferRequest.builder()
                .senderAccountId(allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .receiverAccountId(allUserInfo1.getCreateUserResponse().getAccounts().get(1).getId())
                .amount(amount)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(accountsDepositRequest);

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(accountsDepositRequest);

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .transferMoney(accountsTransferRequest);

        var bo = new CustomerRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract().asPrettyString();

        List<AccountsRequest> accountsRequestList = objectMapper.readValue(bo, new TypeReference<List<AccountsRequest>>() {
        });

        double resultBalance = accountsRequestList
                .stream()
                .filter(a -> a.getId() == allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .findFirst()
                .get()
                .getBalance();

        softly.assertThat(10000 - accountsTransferRequest.getAmount()).isEqualTo(resultBalance);

    }

    @SneakyThrows
    @ValueSource(doubles = {0.1, 10000.})
    @ParameterizedTest
    void oneUnitValueShouldBetweenOwnerAndAlienAccountsReturnSuccess(double amount) {
        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
                .id(allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .balance(5000D)
                .build();

        AccountsTransferRequest accountsTransferRequest = AccountsTransferRequest.builder()
                .senderAccountId(allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .receiverAccountId(allUserInfo2.getCreateUserResponse().getAccounts().get(0).getId())
                .amount(amount)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(accountsDepositRequest);

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(accountsDepositRequest);

        var balanceBeforeAsString = new CustomerRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract().asPrettyString();


        List<AccountsRequest> accountsRequestListBefore = objectMapper.readValue(balanceBeforeAsString, new TypeReference<List<AccountsRequest>>() {
        });

        double balanceBefore = accountsRequestListBefore
                .stream()
                .filter(a -> a.getId() == allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .findFirst()
                .get()
                .getBalance();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .transferMoney(accountsTransferRequest);

        var balanceAfterAsString = new CustomerRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract().asPrettyString();

        List<AccountsRequest> accountsRequestListAfter = objectMapper.readValue(balanceAfterAsString, new TypeReference<List<AccountsRequest>>() {
        });

        double balanceAfter = accountsRequestListAfter
                .stream()
                .filter(a -> a.getId() == allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .findFirst()
                .get()
                .getBalance();

        softly.assertThat(10000 - accountsTransferRequest.getAmount()).isEqualTo(balanceAfter);
    }

    private static Stream<Arguments> invalidTransferValues() {
        return Stream.of(
                Arguments.of(-0.1, "Transfer amount must be at least 0.01"),
                Arguments.of(0, "Transfer amount must be at least 0.01"),
                Arguments.of(10000.1, "Transfer amount cannot exceed 10000")

        );
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("invalidTransferValues")
    void transferInvalidTransferValuesShouldReturnFail(double amount, String errorCode) {
        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
                .id(allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .balance(5000D)
                .build();

        AccountsTransferRequest accountsTransferRequest = AccountsTransferRequest.builder()
                .senderAccountId(allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .receiverAccountId(allUserInfo2.getCreateUserResponse().getAccounts().get(0).getId())
                .amount(amount)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(accountsDepositRequest);

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(accountsDepositRequest);

        var balanceBeforeAsString = new CustomerRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract().asPrettyString();


        List<AccountsRequest> accountsRequestListBefore = objectMapper.readValue(balanceBeforeAsString, new TypeReference<List<AccountsRequest>>() {
        });

        double balanceBefore = accountsRequestListBefore
                .stream()
                .filter(a -> a.getId() == allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .findFirst()
                .get()
                .getBalance();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsBadRequest(errorCode))
                .transferMoney(accountsTransferRequest);


    }

    @Test
    void transferFromNotExistAccountShouldReturnFail() {

        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
                .id(allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .balance(5000D)
                .build();

        AccountsTransferRequest accountsTransferRequest = AccountsTransferRequest.builder()
                .senderAccountId((long) allUserInfo1.getCreateUserResponse().getAccounts().size() + 1)
                .receiverAccountId(allUserInfo2.getCreateUserResponse().getAccounts().get(0).getId())
                .amount(1D)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsForbidden("Unauthorized access to account"))
                .transferMoney(accountsTransferRequest);
    }


    @Test
    void transferToNotExistAccountShouldReturnFail() {

        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
                .id(allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .balance(5000D)
                .build();

        AccountsTransferRequest accountsTransferRequest = AccountsTransferRequest.builder()
                .senderAccountId(allUserInfo1.getCreateUserResponse().getAccounts().get(0).getId())
                .receiverAccountId((long) allUserInfo2.getCreateUserResponse().getAccounts().size() + 1)
                .amount(1D)
                .build();


        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(accountsDepositRequest);

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsBadRequest("Invalid transfer: insufficient funds or invalid accounts"))
                .transferMoney(accountsTransferRequest);
    }


}