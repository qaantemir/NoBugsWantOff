package requests.skelethon;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import models.AccountsDepositRequest;
import models.AccountsRequest;
import models.AccountsRequestList;
import models.AccountsTransactionsResponse;
import models.AccountsTransferRequest;
import models.AccountsTransferResponse;
import models.AuthLoginRequest;
import models.AuthLoginResponse;
import models.BaseModel;
import models.CreateUserRequest;
import models.CreateUserResponse;
import models.CreateUserResponseList;
import models.CustomerProfileRequest;
import models.CustomerProfileResponse;
import models.MockModel;

@Getter
@AllArgsConstructor
public enum Endpoint {
  GET_ADMIN_USERS(
      "/admin/users",
      CreateUserRequest.class,
      CreateUserResponseList.class
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
      "/admin/users",
      CreateUserRequest.class,
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
   * Тело запроса пустое
   * Требуется указать path param id
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
   */
  CUSTOMER_ACCOUNTS(
      "customer/accounts",
      MockModel.class,
      AccountsRequestList.class
  );

  private String url;
  private final Class<? extends BaseModel> requestModel;
  private final Class<? extends BaseModel> responseModel;

  public void setPathParam(String pathParam) {
    this.url = this.url.formatted(pathParam);
  }
}
