package ui;

import static com.codeborne.selenide.Condition.have;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.refresh;
import static org.assertj.core.api.Assertions.assertThat;

import api.generators.TestDataGenerator;
import api.models.AccountsDepositRequest;
import api.models.AccountsRequest;
import api.models.AuthLoginRequest;
import api.models.CreateUserRequest;
import api.models.CreateUserResponse;
import api.models.CustomerProfileRequest;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.UnvalidatedRequester;
import api.requests.steps.AccountsSteps;
import api.requests.steps.AdminSteps;
import api.requests.steps.CustomerSteps;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferUiTest extends BaseUiTest {

  private CreateUserRequest createUserRequest;
  private AuthLoginRequest authLoginRequest;
  private CreateUserResponse createUserResponse;
  private AccountsDepositRequest accountsDepositRequest;
  private CustomerProfileRequest customerProfileRequest;
  private Long senderAccountId;
  private Long receiverAccountId;

  @BeforeEach
  void createUsersAndAccounts() {
    createUserRequest = AdminSteps.createUser();
    authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(createUserRequest);

    customerProfileRequest = TestDataGenerator.generate(CustomerProfileRequest.class);

    new UnvalidatedRequester(Endpoint.PUT_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk()
    ).put(customerProfileRequest);

    AccountsSteps.createAccount(authLoginRequest);
    AccountsSteps.createAccount(authLoginRequest);

    createUserResponse = CustomerSteps.getCustomerProfiles(authLoginRequest);

    senderAccountId = createUserResponse.getAccounts().getFirst().getId();
    receiverAccountId = createUserResponse.getAccounts().get(1).getId();

    accountsDepositRequest = TestDataGenerator.Founded.getAccountsDepositRequest(
        senderAccountId, 5000D);

    AccountsSteps.depositMoneyWithValidatedResponse(authLoginRequest, accountsDepositRequest);
    AccountsSteps.depositMoneyWithValidatedResponse(authLoginRequest, accountsDepositRequest);
  }

  @Test
  void transferValidValueShouldBeSuccess() {
    authAsUser(authLoginRequest);

    SelenideElement accountElement = new TransferPage()
        .open().selectAccount.first();

    new TransferPage().transferMoney(accountElement,
            customerProfileRequest.getName(),
            "ACC" + receiverAccountId.toString(),
            TransferPage.MAX_TRANSFER_VALUE)
        .checkAndAcceptAlert(Alert.SUCCESSFULLY_TRANSFERRED)
        .refresh();

    SelenideElement receiverAccountElement = new TransferPage()
        .open()
        .selectAccount
        .filter(have(text(receiverAccountId.toString())))
        .first(); // не получилось проверку на 123 без слипа сделать

    Selenide.sleep(1000);

    receiverAccountElement
        .shouldHave(text("Balance: $%d.00".formatted(TransferPage.MAX_TRANSFER_VALUE.longValue())));


    List<AccountsRequest> accountsRequestList = CustomerSteps.getCustomerAccounts(authLoginRequest);

    assertThat(accountsRequestList).size().isEqualTo(2);
    assertThat(
        accountsRequestList.stream()
            .filter(el -> el.getAccountNumber().equals("ACC" + receiverAccountId.toString()))
            .toList()
            .getFirst()
            .getBalance()).isEqualTo(TransferPage.MAX_TRANSFER_VALUE);
  }

  @Test
  void transferInvalidValueShouldBeFail() {
    authAsUser(authLoginRequest);

    SelenideElement accountElement = new TransferPage()
        .open().selectAccount.first();

    new TransferPage().transferMoney(accountElement,
            customerProfileRequest.getName(),
            "ACC" + receiverAccountId.toString(),
            TransferPage.MAX_TRANSFER_VALUE + 0.1)
        .checkAndAcceptAlert(Alert.UNSUCCESSFUL_TRANSFERRED)
        .refresh();

    SelenideElement receiverAccountElement = new TransferPage()
        .open()
        .selectAccount
        .filter(have(text(receiverAccountId.toString())))
        .first(); // не получилось проверку на 123 без слипа сделать

    Selenide.sleep(1000);

    receiverAccountElement
        .shouldHave(text(TransferPage.DEFAULT_BALANCE_FIELD_VALUE));


    List<AccountsRequest> accountsRequestList = CustomerSteps.getCustomerAccounts(authLoginRequest);

    assertThat(accountsRequestList).size().isEqualTo(2);
    assertThat(
        accountsRequestList.stream()
            .filter(el -> el.getAccountNumber().equals("ACC" + receiverAccountId.toString()))
            .toList()
            .getFirst()
            .getBalance()).isEqualTo(0.);
  }
}
