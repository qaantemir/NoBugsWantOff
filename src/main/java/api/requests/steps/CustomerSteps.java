package api.requests.steps;

import io.restassured.common.mapper.TypeRef;
import java.util.List;
import api.models.AccountsRequest;
import api.models.AuthLoginRequest;
import api.models.CreateUserResponse;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.UnvalidatedRequester;
import api.requests.skelethon.requesters.ValidatedRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;

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
