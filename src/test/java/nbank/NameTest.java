package nbank;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import generators.RandomData;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

import java.util.List;
import java.util.stream.Stream;

import models.CreateUserRequest;
import models.LoginRequest;
import models.RoleType;
import models.UpdateNameRequest;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import requests.CustomerRequester;
import requests.LoginRequester;
import requests.UserRequester;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class NameTest extends BaseTest {

    @Test
    void nameWithTwoWordsOneSymbolEachShouldBeValid1() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        LoginRequest loginRequest = LoginRequest.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .build();
        UpdateNameRequest updateNameRequest = UpdateNameRequest.builder()
                .name(RandomData.getValidName())
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(loginRequest)
                .extract()
                .header("Authorization");

        new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .updateCustomerName(updateNameRequest);

        new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .getCustomerProfile()
                .body("name", Matchers.equalTo(updateNameRequest.getName()));

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "a B-c", "_ B", "a b c"})
    void invalidNameShouldReturnFail(String name) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        LoginRequest loginRequest = LoginRequest.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .build();
        UpdateNameRequest updateNameRequest = UpdateNameRequest.builder()
                .name(name)
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(loginRequest)
                .extract()
                .header("Authorization");

        new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsBadRequest("Name must contain two words with letters only"))
                .updateCustomerName(updateNameRequest);

        new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .getCustomerProfile()
                .body("name", Matchers.nullValue());
    }
}
