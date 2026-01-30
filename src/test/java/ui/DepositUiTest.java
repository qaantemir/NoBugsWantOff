package ui;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import generators.TestDataGenerator;
import java.util.List;
import models.AccountsRequest;
import models.AuthLoginRequest;
import models.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import requests.steps.AccountsSteps;
import requests.steps.AdminSteps;
import requests.steps.CustomerSteps;

public class DepositUiTest extends BaseUiTest {

  @Test
  void validDepositByUserShouldReturnSuccess() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);
    AccountsSteps.createAccount(authLoginRequest);

    open("/login");

    $(Selectors.byAttribute("placeholder", "Username")).sendKeys(authLoginRequest.getUsername());
    $(Selectors.byAttribute("placeholder", "Password")).sendKeys(authLoginRequest.getPassword());
    $(Selectors.byTagName("button")).shouldHave(exactText("Login")).click();

    $(Selectors.byText("User Dashboard")).shouldBe(Condition.visible);

    $(Selectors.byText("\uD83D\uDCB0 Deposit Money")).click();

    $(Selectors.byText("\uD83D\uDCB0 Deposit Money")).shouldBe(Condition.visible);

    var options = $$("select[class='form-control account-selector'] option");

    assertThat(options).isNotNull();
    assertThat(options.size()).isEqualTo(2);

    var option = options.stream()
        .filter(e -> e.getAttribute("value") != null && !e.getAttribute("value").isBlank())
        .toList()
        .getFirst();

    option.shouldBe(Condition.clickable).shouldHave(text("Balance: $0.00"));

    option.click();

    $(Selectors.byAttribute("placeholder", "Enter amount")).sendKeys("5000");

    $("button[class='btn btn-primary shadow-custom mt-4']").shouldHave(
        exactText("\uD83D\uDCB5 Deposit")).click();

    Alert alert = switchTo().alert();

    alert.accept();

    $(Selectors.byText("User Dashboard")).shouldBe(Condition.visible);

    $(Selectors.byText("\uD83D\uDCB0 Deposit Money")).click();

    $(Selectors.byText("\uD83D\uDCB0 Deposit Money")).shouldBe(Condition.visible);

    options = $$("select[class='form-control account-selector'] option");

    option = options.stream()
        .filter(e -> e.getAttribute("value") != null && !e.getAttribute("value").isBlank())
        .toList()
        .getFirst();

    option.shouldBe(Condition.clickable).shouldHave(text("Balance: $5000.00"));

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

    open("/login");

    $(Selectors.byAttribute("placeholder", "Username")).sendKeys(authLoginRequest.getUsername());
    $(Selectors.byAttribute("placeholder", "Password")).sendKeys(authLoginRequest.getPassword());
    $(Selectors.byTagName("button")).shouldHave(exactText("Login")).click();

    $(Selectors.byText("User Dashboard")).shouldBe(Condition.visible);

    $(Selectors.byText("\uD83D\uDCB0 Deposit Money")).click();

    $(Selectors.byText("\uD83D\uDCB0 Deposit Money")).shouldBe(Condition.visible);

    var options = $$("select[class='form-control account-selector'] option");

    assertThat(options).isNotNull();
    assertThat(options.size()).isEqualTo(2);

    var option = options.stream()
        .filter(e -> e.getAttribute("value") != null && !e.getAttribute("value").isBlank())
        .toList()
        .getFirst();

    option.shouldBe(Condition.clickable).shouldHave(text("Balance: $0.00"));

    option.click();

    $(Selectors.byAttribute("placeholder", "Enter amount")).sendKeys("1000000");

    $("button[class='btn btn-primary shadow-custom mt-4']").shouldHave(
        exactText("\uD83D\uDCB5 Deposit")).click();

    Alert alert = switchTo().alert();

    assertThat(alert.getText()).contains("❌ Please deposit less or equal to 5000$.");

    alert.accept();

    $(Selectors.byText("\uD83D\uDCB0 Deposit Money")).shouldBe(Condition.visible);

    option = options.stream()
        .filter(e -> e.getAttribute("value") != null && !e.getAttribute("value").isBlank())
        .toList()
        .getFirst();

    option.shouldBe(Condition.clickable).shouldHave(text("Balance: $0.00"));

    Selenide.refresh();

    option = options.stream()
        .filter(e -> e.getAttribute("value") != null && !e.getAttribute("value").isBlank())
        .toList()
        .getFirst();

    option.shouldBe(Condition.clickable).shouldHave(text("Balance: $0.00"));

    List<AccountsRequest> accountsRequestList = CustomerSteps.getCustomerAccounts(authLoginRequest);

    assertThat(accountsRequestList).size().isEqualTo(1);
    assertThat(accountsRequestList.getFirst().getBalance()).isEqualTo(0);
  }
}
