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
import com.codeborne.selenide.SelenideElement;
import generators.TestDataGenerator;
import java.util.List;
import models.AccountsDepositRequest;
import models.AuthLoginRequest;
import models.CreateUserRequest;
import models.CreateUserResponse;
import models.CustomerProfileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.UnvalidatedRequester;
import requests.steps.AccountsSteps;
import requests.steps.AdminSteps;
import requests.steps.CustomerSteps;
import specs.RequestSpecs;
import specs.ResponseSpecs;

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
    open("/login");

    $(Selectors.byAttribute("placeholder", "Username")).sendKeys(authLoginRequest.getUsername());
    $(Selectors.byAttribute("placeholder", "Password")).sendKeys(authLoginRequest.getPassword());
    $(Selectors.byTagName("button")).shouldHave(exactText("Login")).click();

    $(Selectors.byText("User Dashboard")).shouldBe(Condition.visible);

    $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).click();

    $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).shouldBe(Condition.visible);

    List<SelenideElement> options = $$(
        "select[class='form-control account-selector'] option").stream().toList();

    assertThat(options).isNotNull();
    assertThat(options).size().isEqualTo(3);

    options = options.stream()
        .filter(e -> e.getAttribute("value") != null && e.getAttribute("value").matches("\\d+"))
        .toList();

    assertThat(
        options.stream()
            .anyMatch(e -> e.getText().matches(".*Balance: \\$10000\\.00.*")))
        .isTrue();

    options.stream()
        .filter(e -> e.getText().matches(".*Balance: \\$10000\\.00.*")).toList().getFirst()
        .click();

    $(Selectors.byAttribute("placeholder", "Enter recipient name")).sendKeys(
        customerProfileRequest.getName());
    $(Selectors.byAttribute("placeholder", "Enter recipient account number")).sendKeys(
        "ACC" + receiverAccountId.toString());
    $(Selectors.byAttribute("placeholder", "Enter amount")).sendKeys("10000");

    $(Selectors.byId("confirmCheck")).click();

    $("button[class='btn-primary shadow-custom green-btn mt-4']").shouldHave(text("Send Transfer"))
        .click();

    Alert alert = switchTo().alert();

    assertThat(alert.getText()).contains(
        "✅ Successfully transferred $10000 to account ACC%d!".formatted(receiverAccountId));

    alert.accept();

    Selenide.refresh();

    options = $$("select[class='form-control account-selector'] option").stream().toList();

    assertThat(options).isNotNull();
    assertThat(options).size().isEqualTo(3);

    options = options.stream()
        .filter(e -> e.getAttribute("value") != null && e.getAttribute("value").matches("\\d+"))
        .toList();

    assertThat(
        options.stream()
            .anyMatch(
                e -> e.getAttribute("value").equals(receiverAccountId.toString()) && e.getText()
                    .matches(".*Balance: \\$10000\\.00.*")))
        .isTrue();

    createUserResponse = CustomerSteps.getCustomerProfiles(authLoginRequest);

    var receiverBalance = createUserResponse.getAccounts().get(1).getBalance();

    assertThat(receiverBalance).isEqualTo(10000.);

  }

  @Test
  void transferInvalidValueShouldBeSuccess() {
    open("/login");

    $(Selectors.byAttribute("placeholder", "Username")).sendKeys(authLoginRequest.getUsername());
    $(Selectors.byAttribute("placeholder", "Password")).sendKeys(authLoginRequest.getPassword());
    $(Selectors.byTagName("button")).shouldHave(exactText("Login")).click();

    $(Selectors.byText("User Dashboard")).shouldBe(Condition.visible);

    $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).click();

    $(Selectors.byText("\uD83D\uDD04 Make a Transfer")).shouldBe(Condition.visible);

    List<SelenideElement> options = $$(
        "select[class='form-control account-selector'] option").stream().toList();

    assertThat(options).isNotNull();
    assertThat(options).size().isEqualTo(3);

    options = options.stream()
        .filter(e -> e.getAttribute("value") != null && e.getAttribute("value").matches("\\d+"))
        .toList();

    assertThat(
        options.stream()
            .anyMatch(e -> e.getText().matches(".*Balance: \\$10000\\.00.*")))
        .isTrue();

    options.stream()
        .filter(e -> e.getText().matches(".*Balance: \\$10000\\.00.*")).toList().getFirst()
        .click();

    $(Selectors.byAttribute("placeholder", "Enter recipient name")).sendKeys(
        customerProfileRequest.getName());
    $(Selectors.byAttribute("placeholder", "Enter recipient account number")).sendKeys(
        "ACC" + receiverAccountId.toString());
    $(Selectors.byAttribute("placeholder", "Enter amount")).sendKeys("10000.01");

    $(Selectors.byId("confirmCheck")).click();

    $("button[class='btn-primary shadow-custom green-btn mt-4']").shouldHave(text("Send Transfer"))
        .click();

    Alert alert = switchTo().alert();

    assertThat(alert.getText()).contains("❌ Error: Transfer amount cannot exceed 10000");

    alert.accept();

    Selenide.refresh();

    options = $$("select[class='form-control account-selector'] option").stream().toList();

    assertThat(options).isNotNull();
    assertThat(options).size().isEqualTo(3);

    options = options.stream()
        .filter(e -> e.getAttribute("value") != null && e.getAttribute("value").matches("\\d+"))
        .toList();

    assertThat(
        options.stream()
            .anyMatch(
                e -> e.getAttribute("value").equals(receiverAccountId.toString()) && e.getText()
                    .matches(".*Balance: \\$0\\.00.*")))
        .isTrue();

    createUserResponse = CustomerSteps.getCustomerProfiles(authLoginRequest);

    var receiverBalance = createUserResponse.getAccounts().get(1).getBalance();

    assertThat(receiverBalance).isEqualTo(0);
  }
}
