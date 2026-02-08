package api;

import api.generators.TestDataGenerator;
import api.models.AuthLoginRequest;
import api.models.CreateUserRequest;
import api.models.CreateUserResponse;
import api.models.CustomerProfileRequest;
import api.models.comparison.ModelAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.UnvalidatedRequester;
import api.requests.skelethon.requesters.ValidatedRequester;
import api.requests.steps.AdminSteps;
import api.specs.ErrorCode;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;

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
