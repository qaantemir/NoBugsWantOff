package ui;

import static com.codeborne.selenide.Condition.text;
import static org.assertj.core.api.Assertions.assertThat;

import api.generators.TestDataGenerator;
import api.models.AccountsRequest;
import api.models.AuthLoginRequest;
import api.models.CreateUserRequest;
import api.requests.steps.AccountsSteps;
import api.requests.steps.AdminSteps;
import api.requests.steps.CustomerSteps;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DepositUiTest extends BaseUiTest {

  private final long DEPOSIT_AMOUNT = 5000L;

  @Test
  void validDepositByUserShouldReturnSuccess() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);
    AccountsSteps.createAccount(authLoginRequest);

    authAsUser(authLoginRequest);

    SelenideElement accountElement = new DepositPage()
        .open()
        .getTheOnlyOneAccount()
        .shouldHave(text(DepositPage.DEFAULT_BALANCE_FIELD_VALUE));

    new DepositPage()
        .open()
        .depositMoney(accountElement, DEPOSIT_AMOUNT)
        .checkAndAcceptAlert(ui.Alert.SUCCESSFULLY_DEPOSITED)
        .refresh();

    new DepositPage()
        .open()
        .getTheOnlyOneAccount().shouldBe(Condition.clickable)
        .shouldHave(text(DepositPage.MAX_DEPOSIT_VALUE));

    List<AccountsRequest> accountsRequestList = CustomerSteps.getCustomerAccounts(authLoginRequest);

    assertThat(accountsRequestList).size().isEqualTo(1);
    assertThat(accountsRequestList.getFirst().getBalance()).isEqualTo(DEPOSIT_AMOUNT);
  }

  @Test
  void invalidDepositByUserShouldReturnFail() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);
    AccountsSteps.createAccount(authLoginRequest);

    authAsUser(authLoginRequest);

    SelenideElement accountElement = new DepositPage()
        .open()
        .getTheOnlyOneAccount()
        .shouldHave(text(DepositPage.DEFAULT_BALANCE_FIELD_VALUE));

    new DepositPage()
        .open()
        .depositMoney(accountElement, DEPOSIT_AMOUNT + 1)
        .checkAndAcceptAlert(ui.Alert.UNSUCCESSFUL_DEPOSITED)
        .refresh();

    new DepositPage()
        .open()
        .getTheOnlyOneAccount().shouldBe(Condition.clickable)
        .shouldHave(text(DepositPage.DEFAULT_BALANCE_FIELD_VALUE));

    List<AccountsRequest> accountsRequestList = CustomerSteps.getCustomerAccounts(authLoginRequest);

    assertThat(accountsRequestList).size().isEqualTo(1);
    assertThat(accountsRequestList.getFirst().getBalance()).isEqualTo(0.);
  }
}
