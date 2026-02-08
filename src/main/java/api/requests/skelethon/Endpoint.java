package api.requests.skelethon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import api.models.AccountsDepositRequest;
import api.models.AccountsRequest;
import api.models.AccountsTransactionsResponse;
import api.models.AccountsTransferRequest;
import api.models.AccountsTransferResponse;
import api.models.AuthLoginRequest;
import api.models.AuthLoginResponse;
import api.models.BaseModel;
import api.models.CreateUserRequest;
import api.models.CreateUserResponse;
import api.models.CustomerProfileRequest;
import api.models.CustomerProfileResponse;
import api.models.MockModel;

@Getter
@AllArgsConstructor
public enum Endpoint {
  /**
   * Тело запроса пустое
   * Возвращается массив из объектов CreateUserResponse
   */
  GET_ADMIN_USERS(
      "/admin/users",
      CreateUserRequest.class,
      CreateUserResponse.class
  ),
  POST_ADMIN_USERS(
      "/admin/users",
      CreateUserRequest.class,
      CreateUserResponse.class
  ),
  /**
   * При запросе DELETE в теле ответа вернется просто text/application
   */
  DELETE_ADMIN_USERS(
      "/admin/users/%s",
      MockModel.class,
      MockModel.class
  ),
  /**
   * Тело запроса пустое
   */
  ACCOUNTS(
      "/accounts",
      MockModel.class,
      AccountsRequest.class
  ),
  ACCOUNTS_TRANSFER(
      "/accounts/transfer",
      AccountsTransferRequest.class,
      AccountsTransferResponse.class
  ),
  ACCOUNTS_DEPOSIT(
      "/accounts/deposit",
      AccountsDepositRequest.class,
      AccountsRequest.class
  ),
  /**
   * Тело запроса пустое Требуется указать path param id
   */
  ACCOUNTS_TRANSACTIONS(
      "accounts/%s/transactions",
      MockModel.class,
      AccountsTransactionsResponse.class
  ),
  AUTH_LOGIN(
      "auth/login",
      AuthLoginRequest.class,
      AuthLoginResponse.class
  ),
  GET_CUSTOMER_PROFILE(
      "customer/profile",
      BaseModel.class,
      CreateUserResponse.class
  ),
  PUT_CUSTOMER_PROFILE(
      "customer/profile",
      CustomerProfileRequest.class,
      CustomerProfileResponse.class
  ),
  /**
   * Тело запроса пустое
   * Возвращается массив из объектов AccountsRequest
   */
  CUSTOMER_ACCOUNTS(
      "customer/accounts",
      MockModel.class,
      AccountsRequest.class
  );

  private String url;
  private final Class<? extends BaseModel> requestModel;
  private final Class<? extends BaseModel> responseModel;

  public void setPathParam(String pathParam) {
    this.url = this.url.formatted(pathParam);
  }
}
