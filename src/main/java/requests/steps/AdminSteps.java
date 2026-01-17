package requests.steps;

import generators.TestDataGenerator;
import models.CreateUserRequest;
import models.CreateUserResponse;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.ValidatedRequester;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class AdminSteps {
  public static CreateUserRequest createUser() {
    CreateUserRequest createUserRequest = TestDataGenerator.generate(CreateUserRequest.class);

    new ValidatedRequester<CreateUserResponse>(
        Endpoint.POST_ADMIN_USERS,
        RequestSpecs.adminSpec(),
        ResponseSpecs.requestReturnsCreated())
        .post(createUserRequest);

    return createUserRequest;
  }
}
