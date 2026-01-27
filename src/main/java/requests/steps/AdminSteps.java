package requests.steps;

import generators.TestDataGenerator;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import models.CreateUserRequest;
import models.CreateUserResponse;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.UnvalidatedRequester;
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

  public static List<CreateUserResponse> getAllUsers() {
    return new UnvalidatedRequester(Endpoint.GET_ADMIN_USERS,
        RequestSpecs.adminSpec(),
        ResponseSpecs.requestReturnsOk())
        .get().extract().as(new TypeRef<List<CreateUserResponse>>() {
        });
  }

  public static List<Long> getAllUsersIdList() {
    return getAllUsers().stream().map(CreateUserResponse::getId).toList();
  }


  public static void deleteUser(Long id) {
    new UnvalidatedRequester(Endpoint.DELETE_ADMIN_USERS,
        RequestSpecs.adminSpec(),
        ResponseSpecs.requestReturnsOk()
    ).delete(id);
  }


  public static void deleteAllUsers() {
    List<Long> userIdsList = getAllUsersIdList();

    for (long userId : userIdsList) {
      deleteUser(userId);
    }
  }
}
