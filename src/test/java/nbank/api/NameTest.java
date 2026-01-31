package nbank.api;

import generators.TestDataGenerator;
import models.AuthLoginRequest;
import models.CreateUserRequest;
import models.CreateUserResponse;
import models.CustomerProfileRequest;
import models.comparison.ModelAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.UnvalidatedRequester;
import requests.skelethon.requesters.ValidatedRequester;
import requests.steps.AdminSteps;
import specs.ErrorCode;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class NameTest extends BaseTest {

  @Test
  void nameWithTwoWordsOneSymbolEachShouldBeValid1() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);
    CustomerProfileRequest customerProfileRequest = TestDataGenerator.generate(
        CustomerProfileRequest.class);

    new UnvalidatedRequester(Endpoint.PUT_CUSTOMER_PROFILE,
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
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);
    CustomerProfileRequest customerProfileRequest = CustomerProfileRequest.builder()
        .name(name)
        .build();

    new UnvalidatedRequester(Endpoint.PUT_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsBadRequest(ErrorCode.CUSTOMER_PROFILE_BAD_REQUEST_NAME_MUST_CONTAIN)
    ).put(customerProfileRequest);

    var actualName = new ValidatedRequester<CreateUserResponse>(
        Endpoint.GET_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk()
    ).get().getName();

    softly.assertThat(actualName).isNull();
    ;
  }
}
