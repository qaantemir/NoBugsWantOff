package nbank;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import generators.RandomData;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;

import java.util.List;
import java.util.stream.Stream;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import models.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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

        allUserInfo1.setProfileRequest(new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest1)
                .extract().as(ProfileRequest.class));
        allUserInfo2.setProfileRequest(new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest2)
                .extract().as(ProfileRequest.class));

        allUserInfo1.getProfileRequest().setPassword(createUserRequest1.getPassword());
        allUserInfo2.getProfileRequest().setPassword(createUserRequest2.getPassword());

        LoginRequest loginRequest1 = LoginRequest.builder()
                .username(allUserInfo1.getProfileRequest().getUsername())
                .password(allUserInfo1.getProfileRequest().getPassword())
                .build();

        LoginRequest loginRequest2 = LoginRequest.builder()
                .username(allUserInfo2.getProfileRequest().getUsername())
                .password(allUserInfo2.getProfileRequest().getPassword())
                .build();

        allUserInfo1.setAuthToken(new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(loginRequest1)
                .extract().header("Authorization"));
        allUserInfo2.setAuthToken(new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(loginRequest2)
                .extract().header("Authorization"));

        for (var u : List.of(allUserInfo1, allUserInfo2)) {
            new AccountsRequester(RequestSpecs.userSpec(u.getAuthToken()), ResponseSpecs.entityWasCreated())
                    .createAccount();
            new AccountsRequester(RequestSpecs.userSpec(u.getAuthToken()), ResponseSpecs.entityWasCreated())
                    .createAccount();
            u.setProfileRequest(new CustomerRequester(RequestSpecs.userSpec(u.getAuthToken()), ResponseSpecs.requestReturnsOk())
                    .getCustomerProfile()
                    .extract()
                    .body()
                    .as(ProfileRequest.class));
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
        DepositRequest depositRequest = DepositRequest.builder()
                .id(allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .balance(5000)
                .build();

        TransferRequest transferRequest = TransferRequest.builder()
                .senderAccountId(allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .receiverAccountId(allUserInfo1.getProfileRequest().getAccounts().get(1).getId())
                .amount(amount)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(depositRequest);

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(depositRequest);

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .transferMoney(transferRequest);

        var bo = new CustomerRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract().asPrettyString();

        List<AccountsRequest> accountsRequestList = objectMapper.readValue(bo, new TypeReference<List<AccountsRequest>>() {
        });

        double resultBalance = accountsRequestList
                .stream()
                .filter(a -> a.getId() == allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .findFirst()
                .get()
                .getBalance();

        softly.assertThat(10000 - transferRequest.getAmount()).isEqualTo(resultBalance);

    }

    @SneakyThrows
    @ValueSource(doubles = {0.1, 10000.})
    @ParameterizedTest
    void oneUnitValueShouldBetweenOwnerAndAlienAccountsReturnSuccess(double amount) {
        DepositRequest depositRequest = DepositRequest.builder()
                .id(allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .balance(5000)
                .build();

        TransferRequest transferRequest = TransferRequest.builder()
                .senderAccountId(allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .receiverAccountId(allUserInfo2.getProfileRequest().getAccounts().get(0).getId())
                .amount(amount)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(depositRequest);

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(depositRequest);

        var balanceBeforeAsString = new CustomerRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract().asPrettyString();


        List<AccountsRequest> accountsRequestListBefore = objectMapper.readValue(balanceBeforeAsString, new TypeReference<List<AccountsRequest>>() {
        });

        double balanceBefore = accountsRequestListBefore
                .stream()
                .filter(a -> a.getId() == allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .findFirst()
                .get()
                .getBalance();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .transferMoney(transferRequest);

        var balanceAfterAsString = new CustomerRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract().asPrettyString();

        List<AccountsRequest> accountsRequestListAfter = objectMapper.readValue(balanceAfterAsString, new TypeReference<List<AccountsRequest>>() {
        });

        double balanceAfter = accountsRequestListAfter
                .stream()
                .filter(a -> a.getId() == allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .findFirst()
                .get()
                .getBalance();

        softly.assertThat(10000 - transferRequest.getAmount()).isEqualTo(balanceAfter);
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
        DepositRequest depositRequest = DepositRequest.builder()
                .id(allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .balance(5000)
                .build();

        TransferRequest transferRequest = TransferRequest.builder()
                .senderAccountId(allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .receiverAccountId(allUserInfo2.getProfileRequest().getAccounts().get(0).getId())
                .amount(amount)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(depositRequest);

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(depositRequest);

        var balanceBeforeAsString = new CustomerRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract().asPrettyString();


        List<AccountsRequest> accountsRequestListBefore = objectMapper.readValue(balanceBeforeAsString, new TypeReference<List<AccountsRequest>>() {
        });

        double balanceBefore = accountsRequestListBefore
                .stream()
                .filter(a -> a.getId() == allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .findFirst()
                .get()
                .getBalance();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsBadRequest(errorCode))
                .transferMoney(transferRequest);


    }

    @Test
    void transferFromNotExistAccountShouldReturnFail() {

        DepositRequest depositRequest = DepositRequest.builder()
                .id(allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .balance(5000)
                .build();

        TransferRequest transferRequest = TransferRequest.builder()
                .senderAccountId(allUserInfo1.getProfileRequest().getAccounts().size() + 1)
                .receiverAccountId(allUserInfo2.getProfileRequest().getAccounts().get(0).getId())
                .amount(1)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsForbidden("Unauthorized access to account"))
                .transferMoney(transferRequest);
    }


    @Test
    void transferToNotExistAccountShouldReturnFail() {

        DepositRequest depositRequest = DepositRequest.builder()
                .id(allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .balance(5000)
                .build();

        TransferRequest transferRequest = TransferRequest.builder()
                .senderAccountId(allUserInfo1.getProfileRequest().getAccounts().get(0).getId())
                .receiverAccountId(allUserInfo2.getProfileRequest().getAccounts().size() + 1)
                .amount(1)
                .build();


        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsOk())
                .depositMoney(depositRequest);

        new AccountsRequester(RequestSpecs.userSpec(allUserInfo1.getAuthToken()), ResponseSpecs.requestReturnsBadRequest("Invalid transfer: insufficient funds or invalid accounts"))
                .transferMoney(transferRequest);
    }


}