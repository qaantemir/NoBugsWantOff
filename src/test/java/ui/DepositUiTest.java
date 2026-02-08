package ui;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.refresh;
import static org.assertj.core.api.Assertions.assertThat;

import api.generators.TestDataGenerator;
import api.models.AccountsRequest;
import api.models.AuthLoginRequest;
import api.models.CreateUserRequest;
import api.requests.steps.AccountsSteps;
import api.requests.steps.AdminSteps;
import api.requests.steps.CustomerSteps;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DepositUiTest extends BaseUiTest {

  @Test
  void validDepositByUserShouldReturnSuccess() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);
    AccountsSteps.createAccount(authLoginRequest);

    authAsUser(authLoginRequest);

    DepositPage depositPage = new DepositPage();
    depositPage.open();

    Selenide.sleep(1000);

    assertThat(depositPage.selectAccount).hasSize(1);

    SelenideElement accountElement = depositPage.selectAccount.first();
    accountElement.shouldBe(Condition.clickable).shouldHave(text("Balance: $0.00"));

    depositPage.depositMoney(accountElement, 5000L);

    depositPage.checkAndAcceptAlert(ui.Alert.SUCCESSFULLY_DEPOSITED);

    refresh();

    depositPage.open();

    depositPage.getSelectAccount().first().shouldBe(Condition.clickable)
        .shouldHave(text("Balance: $5000.00"));

    List<AccountsRequest> accountsRequestList = CustomerSteps.getCustomerAccounts(authLoginRequest);

    assertThat(accountsRequestList).size().isEqualTo(1);
    assertThat(accountsRequestList.getFirst().getBalance()).isEqualTo(5000.);
  }

  @Test
  void invalidDepositByUserShouldReturnFail() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);
    AccountsSteps.createAccount(authLoginRequest);

    authAsUser(authLoginRequest);

    DepositPage depositPage = new DepositPage();
    depositPage.open();

    Selenide.sleep(1000);

    assertThat(depositPage.selectAccount).hasSize(1);

    SelenideElement accountElement = depositPage.selectAccount.first();
    accountElement.shouldBe(Condition.clickable).shouldHave(text("Balance: $0.00"));

    depositPage.depositMoney(accountElement, 1000000L);

    depositPage.checkAndAcceptAlert(ui.Alert.UNSUCCESSFUL_DEPOSITED);

    refresh();

    depositPage.open();

    Selenide.sleep(1000);

    depositPage.getSelectAccount().first().shouldBe(Condition.clickable)
        .shouldHave(text("Balance: $0.00"));

    List<AccountsRequest> accountsRequestList = CustomerSteps.getCustomerAccounts(authLoginRequest);

    assertThat(accountsRequestList).size().isEqualTo(1);
    assertThat(accountsRequestList.getFirst().getBalance()).isEqualTo(0.);
  }
}
