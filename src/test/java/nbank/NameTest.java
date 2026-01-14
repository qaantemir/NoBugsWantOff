package nbank;

import static io.restassured.RestAssured.given;

import generators.RandomData;

import models.CreateUserRequest;
import models.AuthLoginRequest;
import models.RoleType;
import models.CustomerProfileRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
        AuthLoginRequest authLoginRequest = AuthLoginRequest.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .build();
        CustomerProfileRequest customerProfileRequest = CustomerProfileRequest.builder()
                .name(RandomData.getValidName())
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest)
                .extract()
                .header("Authorization");

        new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .updateCustomerName(customerProfileRequest);

        new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .getCustomerProfile()
                .body("name", Matchers.equalTo(customerProfileRequest.getName()));

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "a B-c", "_ B", "a b c"})
    void invalidNameShouldReturnFail(String name) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username(RandomData.getValidUserName())
                .password(RandomData.getValidPassword())
                .role(RoleType.USER)
                .build();
        AuthLoginRequest authLoginRequest = AuthLoginRequest.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .build();
        CustomerProfileRequest customerProfileRequest = CustomerProfileRequest.builder()
                .name(name)
                .build();

        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.entityWasCreated())
                .createUser(createUserRequest);
        var authToken = new LoginRequester(RequestSpecs.unauthSpec(), ResponseSpecs.requestReturnsOk())
                .login(authLoginRequest)
                .extract()
                .header("Authorization");

        new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsBadRequest("Name must contain two words with letters only"))
                .updateCustomerName(customerProfileRequest);

        new CustomerRequester(RequestSpecs.userSpec(authToken), ResponseSpecs.requestReturnsOk())
                .getCustomerProfile()
                .body("name", Matchers.nullValue());
    }
}
