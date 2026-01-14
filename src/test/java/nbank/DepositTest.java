package nbank;

import static io.restassured.RestAssured.given;

import generators.RandomData;

import java.util.stream.Stream;

import models.*;
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

public class DepositTest extends BaseTest {

    @ValueSource(doubles = {0.01, 5000.})
    @ParameterizedTest
    void validDepositValuesShouldReturnSuccess(double amount) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        AuthLoginRequest authLoginRequest = AuthLoginRequest.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.entityWasCreated())
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

        new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .depositMoney(accountsDepositRequest);

        var result = new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract()
                .response()
                .body()
                .as(CustomerAccountsResponse.class)
                .getAccountsRequestList().getFirst().getBalance();

        softly.assertThat(result).isEqualTo(accountsDepositRequest.getBalance());
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

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.entityWasCreated())
                .createAccount()
                .extract()
                .response()
                .body()
                .as(AccountsRequest.class)
                .getId();

        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
                .id(accIid)
                .balance(1)
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

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest1);
        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest2);
        var authToken1 = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest1)
                .extract()
                .header("Authorization");
        var authToken2 = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest2)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken2), ResponseSpecs.entityWasCreated())
                .createAccount()
                .extract()
                .response()
                .body()
                .as(AccountsRequest.class)
                .getId();

        AccountsDepositRequest accountsDepositRequest = AccountsDepositRequest.builder()
                .id(accIid)
                .balance(1)
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

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.entityWasCreated())
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
