package api;

import generators.TestDataGenerator;
import java.util.List;
import java.util.stream.Stream;
import models.AccountsDepositRequest;
import models.AccountsRequest;
import models.AuthLoginRequest;
import models.CreateUserRequest;
import models.comparison.ModelAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import requests.steps.AccountsSteps;
import requests.steps.AdminSteps;
import requests.steps.CustomerSteps;
import specs.ErrorCode;
import specs.ResponseSpecs;

public class DepositTest extends BaseTest {

  @ValueSource(doubles = {0.01, 5000.})
  @ParameterizedTest
  void validDepositValuesShouldReturnSuccess(double amount) {
    CreateUserRequest createUserRequest = AdminSteps.createUser();

    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);

    AccountsRequest accountsRequest = AccountsSteps.createAccount(authLoginRequest);

    AccountsDepositRequest accountsDepositRequest = TestDataGenerator.Founded.getAccountsDepositRequest(
        accountsRequest.getId(), amount);

    AccountsRequest accountsRequest1 = AccountsSteps.depositMoneyWithValidatedResponse(
        authLoginRequest, accountsDepositRequest);

    List<AccountsRequest> accountsRequestList = CustomerSteps.getCustomerAccounts(authLoginRequest);

    ModelAssertions.assertThatModels(accountsRequest1, accountsRequestList.getFirst());
  }


  @Test
  void validDepositOnNotExistAccountShouldReturnFail() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();

    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);

    AccountsDepositRequest accountsDepositRequest = TestDataGenerator.Founded.getAccountsDepositRequest(
        TestDataGenerator.RandomData.getRandomLong(), 100.);

    AccountsSteps.depositMoneyWithUnvalidatedResponse(authLoginRequest,
        accountsDepositRequest,
        ResponseSpecs.requestReturnsForbidden(ErrorCode.FORBIDDEN_UNAUTHORIZED_ACCESS));

    List<AccountsRequest> accountsRequestList = CustomerSteps.getCustomerAccounts(authLoginRequest);

    softly.assertThat(accountsRequestList).isEmpty();
  }

  private static Stream<Arguments> invalidDepositAmounts() {
    return Stream.of(
        Arguments.of(-0.11, ErrorCode.ACCOUNT_DEPOSIT_BAD_REQUEST_DEPOSIT_AMOUNT_MUST_BE_AT_LEAST),
        Arguments.of(0, ErrorCode.ACCOUNT_DEPOSIT_BAD_REQUEST_DEPOSIT_AMOUNT_MUST_BE_AT_LEAST),
        Arguments.of(5000.01, ErrorCode.ACCOUNT_DEPOSIT_BAD_REQUEST_DEPOSIT_AMOUNT_CANNOT_EXCEED)
    );
  }

  @MethodSource("invalidDepositAmounts")
  @ParameterizedTest
  void invalidDepositValuesShouldReturnFail(double amount, ErrorCode errorcode) {
    CreateUserRequest createUserRequest = AdminSteps.createUser();

    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);

    AccountsRequest accountsRequest = AccountsSteps.createAccount(authLoginRequest);

    AccountsDepositRequest accountsDepositRequest = TestDataGenerator.Founded.getAccountsDepositRequest(
        accountsRequest.getId(), amount);

    AccountsSteps.depositMoneyWithUnvalidatedResponse(authLoginRequest,
        accountsDepositRequest,
        ResponseSpecs.requestReturnsBadRequest(errorcode));

    List<AccountsRequest> accountsRequestList = CustomerSteps.getCustomerAccounts(authLoginRequest);

    softly.assertThat(accountsRequestList.getFirst().getBalance()).isEqualTo(0);
  }

}
