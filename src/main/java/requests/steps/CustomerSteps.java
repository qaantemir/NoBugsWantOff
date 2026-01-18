package requests.steps;

import io.restassured.common.mapper.TypeRef;
import java.util.List;
import models.AccountsRequest;
import models.AuthLoginRequest;
import models.CreateUserResponse;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.UnvalidatedRequester;
import requests.skelethon.requesters.ValidatedRequester;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class CustomerSteps {
  public static List<AccountsRequest> getCustomerAccounts(AuthLoginRequest authLoginRequest) {
    return new UnvalidatedRequester(Endpoint.CUSTOMER_ACCOUNTS,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk()
    ).get().extract().as(new TypeRef<List<AccountsRequest>>() {});
  }

  public static CreateUserResponse getCustomerProfiles(AuthLoginRequest authLoginRequest) {
    return new ValidatedRequester<CreateUserResponse>(Endpoint.GET_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk())
        .get();
  }

}
