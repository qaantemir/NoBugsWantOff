package nbank.api;

import generators.TestDataGenerator;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import models.AccountsDepositRequest;
import models.AccountsTransferRequest;
import models.AuthLoginRequest;
import models.CreateUserRequest;
import models.CreateUserResponse;
import org.junit.jupiter.api.BeforeEach;
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

public class TransferTest extends BaseTest {

  private CreateUserRequest createUserRequest1;
  private CreateUserRequest createUserRequest2;
  private AuthLoginRequest authLoginRequest1;
  private AuthLoginRequest authLoginRequest2;
  private CreateUserResponse createUserResponse1;
  private CreateUserResponse createUserResponse2;


  @BeforeEach
  void createUsersAndAccounts() {
    createUserRequest1 = AdminSteps.createUser();
    createUserRequest2 = AdminSteps.createUser();
    authLoginRequest1 = TestDataGenerator.Founded.getAuthLoginRequest(createUserRequest1);
    authLoginRequest2 = TestDataGenerator.Founded.getAuthLoginRequest(createUserRequest2);

    AccountsSteps.createAccount(authLoginRequest1);
    AccountsSteps.createAccount(authLoginRequest1);
    AccountsSteps.createAccount(authLoginRequest2);
    AccountsSteps.createAccount(authLoginRequest2);

    createUserResponse1 = CustomerSteps.getCustomerProfiles(authLoginRequest1);
    createUserResponse2 = CustomerSteps.getCustomerProfiles(authLoginRequest2);
  }

  @SneakyThrows
  @ValueSource(doubles = {0.1, 10000.})
  @ParameterizedTest
  void transferBetweenOwnerAccountsShouldReturnSuccess(double amount) {
    Long senderAccountId = createUserResponse1.getAccounts().get(0).getId();
    Long receiverAccountId = createUserResponse1.getAccounts().get(1).getId();

    AccountsDepositRequest accountsDepositRequest = TestDataGenerator.Founded.getAccountsDepositRequest(
        senderAccountId, 5000D);
    AccountsTransferRequest accountsTransferRequest = TestDataGenerator.Founded.getAccountsTransferRequest(
        senderAccountId, receiverAccountId, amount);

    AccountsSteps.depositMoneyWithValidatedResponse(authLoginRequest1, accountsDepositRequest);
    AccountsSteps.depositMoneyWithValidatedResponse(authLoginRequest1, accountsDepositRequest);

    AccountsSteps.transferMoneyWithValidatedResponse(authLoginRequest1, accountsTransferRequest);

    CreateUserResponse createUserResponseAfterTransfer = CustomerSteps.getCustomerProfiles(
        authLoginRequest1);

    softly.assertThat(createUserResponseAfterTransfer.getAccounts().get(1).getBalance())
        .isEqualTo(amount);
  }

  @SneakyThrows
  @ValueSource(doubles = {0.1, 10000.})
  @ParameterizedTest
  void oneUnitValueShouldBetweenOwnerAndAlienAccountsReturnSuccess(double amount) {
    Long senderAccountId = createUserResponse1.getAccounts().getFirst().getId();
    Long receiverAccountId = createUserResponse2.getAccounts().getFirst().getId();

    AccountsDepositRequest accountsDepositRequest = TestDataGenerator.Founded.getAccountsDepositRequest(
        senderAccountId, 5000D);
    AccountsTransferRequest accountsTransferRequest = TestDataGenerator.Founded.getAccountsTransferRequest(
        senderAccountId, receiverAccountId, amount);

    AccountsSteps.depositMoneyWithValidatedResponse(authLoginRequest1, accountsDepositRequest);
    AccountsSteps.depositMoneyWithValidatedResponse(authLoginRequest1, accountsDepositRequest);

    AccountsSteps.transferMoneyWithValidatedResponse(authLoginRequest1, accountsTransferRequest);

    CreateUserResponse createUserResponseAfterTransfer = CustomerSteps.getCustomerProfiles(
        authLoginRequest2);

    softly.assertThat(createUserResponseAfterTransfer.getAccounts().getFirst().getBalance())
        .isEqualTo(amount);
  }

  private static Stream<Arguments> invalidTransferValues() {
    return Stream.of(
        Arguments.of(-0.1, ErrorCode.ACCOUNT_TRANSFER_BAD_REQUEST_DEPOSIR_AMOUNT_MUST_BE_AT_LEAST),
        Arguments.of(0, ErrorCode.ACCOUNT_TRANSFER_BAD_REQUEST_DEPOSIR_AMOUNT_MUST_BE_AT_LEAST),
        Arguments.of(10000.1, ErrorCode.ACCOUNT_DEPOSIT_BAD_REQUEST_DEPOSIT_AMOUNT_CANNOT_EXCEED)
    );
  }

  @SneakyThrows
  @ParameterizedTest
  @MethodSource("invalidTransferValues")
  void transferInvalidTransferValuesShouldReturnFail(double amount, ErrorCode errorCode) {
    Long senderAccountId = createUserResponse1.getAccounts().getFirst().getId();
    Long receiverAccountId = createUserResponse2.getAccounts().getFirst().getId();

    AccountsDepositRequest accountsDepositRequest = TestDataGenerator.Founded.getAccountsDepositRequest(
        senderAccountId, 5000D);
    AccountsTransferRequest accountsTransferRequest = TestDataGenerator.Founded.getAccountsTransferRequest(
        senderAccountId, receiverAccountId, amount);

    AccountsSteps.depositMoneyWithValidatedResponse(authLoginRequest1, accountsDepositRequest);
    AccountsSteps.depositMoneyWithValidatedResponse(authLoginRequest1, accountsDepositRequest);

    AccountsSteps.transferMoneyWithUnvalidatedResponse(authLoginRequest1, accountsTransferRequest,
        ResponseSpecs.requestReturnsBadRequest(errorCode));

    CreateUserResponse createUserResponseAfterTransfer = CustomerSteps.getCustomerProfiles(
        authLoginRequest2);

    softly.assertThat(createUserResponseAfterTransfer.getAccounts().getFirst().getBalance())
        .isEqualTo(0);
  }

  @Test
  void transferFromNotExistAccountShouldReturnFail() {
    Long senderAccountId = createUserResponse1.getAccounts().getFirst().getId() + 666;
    Long receiverAccountId = createUserResponse2.getAccounts().getFirst().getId();

    AccountsTransferRequest accountsTransferRequest = TestDataGenerator.Founded.getAccountsTransferRequest(
        senderAccountId, receiverAccountId, 1.);

    AccountsSteps.transferMoneyWithUnvalidatedResponse(authLoginRequest1, accountsTransferRequest,
        ResponseSpecs.requestReturnsForbidden(ErrorCode.FORBIDDEN_UNAUTHORIZED_ACCESS));

    CreateUserResponse createUserResponseAfterTransfer = CustomerSteps.getCustomerProfiles(
        authLoginRequest2);

    softly.assertThat(createUserResponseAfterTransfer.getAccounts().getFirst().getBalance())
        .isEqualTo(0);
  }


  @Test
  void transferToNotExistAccountShouldReturnFail() {
    Long senderAccountId = createUserResponse1.getAccounts().getFirst().getId();
    Long receiverAccountId = createUserResponse2.getAccounts().getFirst().getId() + 666;

    AccountsDepositRequest accountsDepositRequest = TestDataGenerator.Founded.getAccountsDepositRequest(
        senderAccountId, 5000D);
    AccountsTransferRequest accountsTransferRequest = TestDataGenerator.Founded.getAccountsTransferRequest(
        senderAccountId, receiverAccountId, 1.);

    AccountsSteps.depositMoneyWithValidatedResponse(authLoginRequest1, accountsDepositRequest);
    AccountsSteps.depositMoneyWithValidatedResponse(authLoginRequest1, accountsDepositRequest);

    AccountsSteps.transferMoneyWithUnvalidatedResponse(authLoginRequest1, accountsTransferRequest,
        ResponseSpecs.requestReturnsBadRequest(ErrorCode.ACCOUNT_TRANSFER_BAD_REQUEST_INVALID_TRANSFER));

    CreateUserResponse createUserResponseAfterTransfer = CustomerSteps.getCustomerProfiles(
        authLoginRequest2);

    softly.assertThat(createUserResponseAfterTransfer.getAccounts().getFirst().getBalance())
        .isEqualTo(0);
  }


}