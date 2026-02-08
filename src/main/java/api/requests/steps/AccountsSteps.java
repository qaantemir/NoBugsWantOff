package api.requests.steps;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import api.models.AccountsDepositRequest;
import api.models.AccountsRequest;
import api.models.AccountsTransferRequest;
import api.models.AccountsTransferResponse;
import api.models.AuthLoginRequest;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.UnvalidatedRequester;
import api.requests.skelethon.requesters.ValidatedRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;

public class AccountsSteps {

  public static AccountsRequest createAccount(AuthLoginRequest authLoginRequest) {
     return new ValidatedRequester<AccountsRequest>(Endpoint.ACCOUNTS,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsCreated())
        .post();
  }

  public static AccountsRequest depositMoneyWithValidatedResponse(AuthLoginRequest authLoginRequest, AccountsDepositRequest accountsDepositRequest) {
    return new ValidatedRequester<AccountsRequest>(Endpoint.ACCOUNTS_DEPOSIT,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk())
        .post(accountsDepositRequest);
  }

  public static ValidatableResponse depositMoneyWithUnvalidatedResponse(AuthLoginRequest authLoginRequest, AccountsDepositRequest accountsDepositRequest, ResponseSpecification responseSpecification) {
    return new UnvalidatedRequester(Endpoint.ACCOUNTS_DEPOSIT,
        RequestSpecs.authUser(authLoginRequest),
        responseSpecification)
        .post(accountsDepositRequest);
  }

  public static AccountsTransferResponse transferMoneyWithValidatedResponse(AuthLoginRequest authLoginRequest, AccountsTransferRequest accountsTransferRequest) {
    return new ValidatedRequester<AccountsTransferResponse>(Endpoint.ACCOUNTS_TRANSFER,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk())
        .post(accountsTransferRequest);
  }

  public static ValidatableResponse transferMoneyWithUnvalidatedResponse(AuthLoginRequest authLoginRequest, AccountsTransferRequest accountsTransferRequest, ResponseSpecification responseSpecification) {
    return new UnvalidatedRequester(Endpoint.ACCOUNTS_TRANSFER,
        RequestSpecs.authUser(authLoginRequest),
        responseSpecification)
        .post(accountsTransferRequest);
  }

}
