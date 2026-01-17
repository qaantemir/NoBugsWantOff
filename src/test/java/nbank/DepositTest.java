package nbank;

import static io.restassured.RestAssured.given;

import generators.RandomData;

import generators.TestDataGenerator;
import java.util.stream.Stream;

import models.*;
import models.comparison.ModelAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import requests.AccountsRequester;
import requests.CustomerRequester;
import requests.LoginRequester;
import requests.UserRequester;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.NonValidatedRequester;
import requests.skelethon.requesters.ValidatedRequester;
import requests.steps.AdminSteps;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class DepositTest extends BaseTest {

    @ValueSource(doubles = {0.01, 5000.})
    @ParameterizedTest
    void validDepositValuesShouldReturnSuccess(double amount) {
        // Создать CreateUserRequest
        // Создать AuthLoginRequest
        // Запрос CreateUserRequest на admin/users (создание юзера)
        // Запрос AuthLoginRequest на auth/login (Получение ауфтокена для CreateUserRequest)
        // Запрос на accounts (создание счета для CreateUserRequest) и получение accIid
        // Создать AccountsDepositRequest (с accIid для CreateUserRequest)
        // Запрос AccountsDepositRequest на accounts/deposit (пополнение счета accIid для CreateUserRequest)
        // Запрос на customer/accounts (получение информации по счетам, сравнение баланса счета с ОР)

        CreateUserRequest createUserRequest = AdminSteps.createUser();
        AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(createUserRequest);

        AccountsRequest accountsRequest = new ValidatedRequester<AccountsRequest>(Endpoint.ACCOUNTS,
            RequestSpecs.authUser(authLoginRequest),
            ResponseSpecs.requestReturnsCreated())
            .post();

        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
            .id(accountsRequest.getId())
            .balance(amount)
            .build();

        AccountsRequest accountsRequest1 = new ValidatedRequester<AccountsRequest>(Endpoint.ACCOUNTS_DEPOSIT,
            RequestSpecs.authUser(authLoginRequest),
            ResponseSpecs.requestReturnsOk())
            .post(accountsDepositRequest);

//        AccountsRequestList accountsRequestList = new ValidatedRequester<AccountsRequestList>(Endpoint.CUSTOMER_ACCOUNTS,
//            RequestSpecs.authUser(authLoginRequest),
//            ResponseSpecs.requestReturnsOk()
//            ).get();
//
//        ModelAssertions.assertThatModels(accountsRequest1, accountsRequestList.getAccountsRequestList().getFirst());

//
//        AccountsRequest accountsRequestBeforeDeposit; // сюда собрать информацию до пополнения
//        AccountsDepositRequest accountsDepositRequest; // или сюда
//        AccountsRequestList accountsRequestList1; // или сюда
//
//        CreateUserRequest createUserRequest = CreateUserRequest.builder()
//                .username(RandomData.getValidUserName())
//                .password(RandomData.getValidPassword())
//                .role(RoleType.USER)
//                .build();
//        AuthLoginRequest authLoginRequest = AuthLoginRequest.builder()
//                .username(createUserRequest.getUsername())
//                .password(createUserRequest.getPassword())
//                .build();
//
//        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.requestReturnsCreated())
//                .createUser(createUserRequest);
//        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
//                .login(authLoginRequest)
//                .extract()
//                .header("Authorization");
//
//        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsCreated())
//                .createAccount()
//                .extract()
//                .response()
//                .body()
//                .as(AccountsRequest.class)
//                .getId();
//
//        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
//                .id(accIid)
//                .balance(amount)
//                .build();
//
//        new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
//                .depositMoney(accountsDepositRequest);
//
        var result = new CustomerRequester(RequestSpecs.authUser(authLoginRequest), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract()
                .response()
                .body()
                .as(CustomerAccountsResponse.class)
                .getAccountsRequestList().getFirst().getBalance();

        System.out.println(result);
//
//        softly.assertThat(result).isEqualTo(accountsDepositRequest.getBalance());
    }


    @Test
    void validDepositOnNotExistAccountShouldReturnFail() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        AuthLoginRequest authLoginRequest = AuthLoginRequest.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.requestReturnsCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsCreated())
                .createAccount()
                .extract()
                .response()
                .body()
                .as(AccountsRequest.class)
                .getId();

        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
                .id(accIid)
                .balance(1D)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsForbidden("Unauthorized access to account"))
                .depositMoney(accountsDepositRequest);
    }

    @Test
    void validDepositOnAlienAccountShouldReturnFail() {
        CreateUserRequest createUserRequest1 = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        AuthLoginRequest authLoginRequest1 = AuthLoginRequest.builder()
                .username(createUserRequest1.getUsername())
                .password(createUserRequest1.getPassword())
                .build();
        CreateUserRequest createUserRequest2 = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        AuthLoginRequest authLoginRequest2 = AuthLoginRequest.builder()
                .username(createUserRequest2.getUsername())
                .password(createUserRequest2.getPassword())
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.requestReturnsCreated())
                .createUser(createUserRequest1);
        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.requestReturnsCreated())
                .createUser(createUserRequest2);
        var authToken1 = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest1)
                .extract()
                .header("Authorization");
        var authToken2 = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest2)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken2), ResponseSpecs.requestReturnsCreated())
                .createAccount()
                .extract()
                .response()
                .body()
                .as(AccountsRequest.class)
                .getId();

        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
                .id(accIid)
                .balance(1D)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(authToken1), ResponseSpecs.requestReturnsForbidden("Unauthorized access to account"))
                .depositMoney(accountsDepositRequest);
    }

    private static Stream<Arguments> invalidDepositAmounts() {
        return Stream.of(
                Arguments.of(-0.11, "Deposit amount must be at least 0.01"),
                Arguments.of(0, "Deposit amount must be at least 0.01"),
                Arguments.of(5000.01, "Deposit amount cannot exceed 5000")
        );
    }

    @MethodSource("invalidDepositAmounts")
    @ParameterizedTest
    void invalidDepositValuesShouldReturnFail(double amount, String errorcode) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        AuthLoginRequest authLoginRequest = AuthLoginRequest.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.requestReturnsCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsCreated())
                .createAccount()
                .extract()
                .response()
                .body()
                .as(AccountsRequest.class)
                .getId();

        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
                .id(accIid)
                .balance(amount)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsBadRequest(errorcode))
                .depositMoney(accountsDepositRequest);

        var result = new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract()
                .response()
                .body()
                .as(AccountsRequest.class)
                .getId();
        softly.assertThat(result).isEqualTo(0);
    }

}
