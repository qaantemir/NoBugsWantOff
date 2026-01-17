package nbank;

import generators.TestDataGenerator;
import models.AuthLoginRequest;
import models.CreateUserRequest;
import models.CreateUserResponse;
import models.CustomerProfileRequest;
import models.comparison.ModelAssertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.NonValidatedRequester;
import requests.skelethon.requesters.ValidatedRequester;
import requests.steps.AdminSteps;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class NameTest extends BaseTest {

  @Test
  void nameWithTwoWordsOneSymbolEachShouldBeValid1() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(createUserRequest);
    CustomerProfileRequest customerProfileRequest = TestDataGenerator.generate(CustomerProfileRequest.class);

    new NonValidatedRequester(Endpoint.PUT_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk()
    ).put(customerProfileRequest);

    CreateUserResponse createUserResponse = new ValidatedRequester<CreateUserResponse>(
        Endpoint.GET_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk()
    ).get();

    ModelAssertions.assertThatModels(customerProfileRequest, createUserResponse);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "a", "a B-c", "_ B", "a b c"})
  void invalidNameShouldReturnFail(String name) {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(createUserRequest);
    CustomerProfileRequest customerProfileRequest = CustomerProfileRequest.builder()
        .name(name)
        .build();

    new NonValidatedRequester(Endpoint.PUT_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsBadRequest("Name must contain two words with letters only")
    ).put(customerProfileRequest);

    new NonValidatedRequester(
        Endpoint.GET_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk()
    ).get().body("name", Matchers.nullValue());;
  }
}
