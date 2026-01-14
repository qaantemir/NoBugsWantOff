package requests.skelethon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import models.AccountsDepositRequest;
import models.AccountsRequest;
import models.AccountsTransactionsResponse;
import models.AccountsTransferRequest;
import models.AccountsTransferResponse;
import models.AuthLoginRequest;
import models.AuthLoginResponse;
import models.BaseModel;
import models.CreateUserRequest;
import models.CreateUserResponse;
import models.CustomerProfileRequest;

@Getter
@AllArgsConstructor
public enum Endpoint {
  /**
   * При запросе POST вернется CreateUserResponse
   * При запросе GET вернется массив CreateUserResponse
   * При запросе DELETE вернется просто текст
   */
  ADMIN_USERS(
      "/admin/users",
      CreateUserRequest.class,
      CreateUserResponse.class
  ),
  ACCOUNTS(
      "/accounts",
      BaseModel.class,
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
  ACCOUNTS_TRANSACTIONS(
      "accounts/%s/transactions",
      BaseModel.class,
      AccountsTransactionsResponse.class
  ),
  AUTH_LOGIN(
      "auth/login",
      AuthLoginRequest.class,
      AuthLoginResponse.class
  ),
  /**
   * При запросе GET вернется CreateUserResponse
   * При запросе PUT вернется CustomerProfileResponse
   */
  CUSTOMER_PROFILE(
      "customer/profile",
      CustomerProfileRequest.class,
      CreateUserResponse.class //
  ),
  /**
   * На самом деле возвращается массив из AccountsRequest
   */
  CUSTOMER_ACCOUNTS(
      "customer/accounts",
      BaseModel.class,
      AccountsRequest.class
  );

  private String url;
  private final Class<? extends BaseModel> requestModel;
  private final Class<? extends BaseModel> responseModel;

  public void setPathParam(String pathParam) {
    this.url = this.url.formatted(pathParam);
  }
}
