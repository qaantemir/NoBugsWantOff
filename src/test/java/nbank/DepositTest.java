package nbank;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import generators.RandomData;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

import java.util.List;
import java.util.stream.Stream;

import models.*;
import org.apache.http.HttpStatus;
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

public class DepositTest extends BaseTest {

    @ValueSource(doubles = {0.01, 5000.})
    @ParameterizedTest
    void validDepositValuesShouldReturnSuccess(double amount) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        LoginRequest loginRequest = LoginRequest.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(loginRequest)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.entityWasCreated())
                .createAccount()
                .extract()
                .response()
                .body()
                .as(AccountsRequest.class)
                .getId();

        DepositRequest depositRequest = DepositRequest.builder()
                .id(accIid)
                .balance(amount)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .depositMoney(depositRequest);

        var result = new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .getCustomerAccounts()
                .extract()
                .response()
                .body()
                .as(CustomerAccountsResponse.class)
                .getAccountsRequestList().getFirst().getBalance();

        softly.assertThat(result).isEqualTo(depositRequest.getBalance());
    }


    @Test
    void validDepositOnNotExistAccountShouldReturnFail() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        LoginRequest loginRequest = LoginRequest.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(loginRequest)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.entityWasCreated())
                .createAccount()
                .extract()
                .response()
                .body()
                .as(AccountsRequest.class)
                .getId();

        DepositRequest depositRequest = DepositRequest.builder()
                .id(accIid)
                .balance(1)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsForbidden("Unauthorized access to account"))
                .depositMoney(depositRequest);
    }

    @Test
    void validDepositOnAlienAccountShouldReturnFail() {
        CreateUserRequest createUserRequest1 = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        LoginRequest loginRequest1 = LoginRequest.builder()
                .username(createUserRequest1.getUsername())
                .password(createUserRequest1.getPassword())
                .build();
        CreateUserRequest createUserRequest2 = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        LoginRequest loginRequest2 = LoginRequest.builder()
                .username(createUserRequest2.getUsername())
                .password(createUserRequest2.getPassword())
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest1);
        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest2);
        var authToken1 = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(loginRequest1)
                .extract()
                .header("Authorization");
        var authToken2 = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(loginRequest2)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken2), ResponseSpecs.entityWasCreated())
                .createAccount()
                .extract()
                .response()
                .body()
                .as(AccountsRequest.class)
                .getId();

        DepositRequest depositRequest = DepositRequest.builder()
                .id(accIid)
                .balance(1)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(authToken1), ResponseSpecs.requestReturnsForbidden("Unauthorized access to account"))
                .depositMoney(depositRequest);
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
        LoginRequest loginRequest = LoginRequest.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(loginRequest)
                .extract()
                .header("Authorization");

        var accIid = new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.entityWasCreated())
                .createAccount()
                .extract()
                .response()
                .body()
                .as(AccountsRequest.class)
                .getId();

        DepositRequest depositRequest = DepositRequest.builder()
                .id(accIid)
                .balance(amount)
                .build();

        new AccountsRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsBadRequest(errorcode))
                .depositMoney(depositRequest);

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
