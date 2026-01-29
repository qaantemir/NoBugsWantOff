package ui;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import generators.TestDataGenerator;
import models.AuthLoginRequest;
import models.CreateUserRequest;
import models.CreateUserResponse;
import models.CustomerProfileRequest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.ValidatedRequester;
import requests.steps.AdminSteps;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class NameUiTest extends BaseUiTest {

  @Test
  void validNameShouldBeSetup() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);

    open("/login");

    $(Selectors.byAttribute("placeholder", "Username")).sendKeys(createUserRequest.getUsername());
    $(Selectors.byAttribute("placeholder", "Password")).sendKeys(createUserRequest.getPassword());
    $(Selectors.byTagName("button")).shouldHave(exactText("Login")).click();

    $(Selectors.byText("User Dashboard")).shouldBe(Condition.visible);

    $(Selectors.byClassName("welcome-text")).shouldHave(exactText("Welcome, noname!"));

    $(Selectors.byClassName("profile-header"))
        .shouldBe(Condition.visible)
        .shouldHave(Condition.text("noname"))
        .shouldHave(Condition.text(createUserRequest.getUsername()))
        .click();

    $(Selectors.byText("✏️ Edit Profile")).shouldBe(Condition.visible);

    var name = TestDataGenerator.generate(CustomerProfileRequest.class).getName();
    $(Selectors.byAttribute("placeholder", "Enter new name")).sendKeys(name);

    $("button.btn.btn-primary.mt-3")
        .shouldHave(Condition.exactText("💾 Save Changes"))
        .click();

    Alert alert = switchTo().alert();

    assertThat(alert.getText()).contains("✅ Name updated successfully!");

    alert.accept();

    Selenide.refresh();

    $(Selectors.byClassName("profile-header"))
        .shouldBe(Condition.visible)
        .shouldHave(Condition.text(name))
        .shouldHave(Condition.text(createUserRequest.getUsername()));

    var actualName = new ValidatedRequester<CreateUserResponse>(
        Endpoint.GET_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk()
    ).get().getName();

    assertThat(actualName).isEqualTo(name);
  }

  @Test
  void invalidNameShouldReturnFail() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);

    open("/login");

    $(Selectors.byAttribute("placeholder", "Username")).sendKeys(createUserRequest.getUsername());
    $(Selectors.byAttribute("placeholder", "Password")).sendKeys(createUserRequest.getPassword());
    $(Selectors.byTagName("button")).shouldHave(exactText("Login")).click();

    $(Selectors.byText("User Dashboard")).shouldBe(Condition.visible);

    $(Selectors.byClassName("welcome-text")).shouldHave(exactText("Welcome, noname!"));

    $(Selectors.byClassName("profile-header"))
        .shouldBe(Condition.visible)
        .shouldHave(Condition.text("noname"))
        .shouldHave(Condition.text(createUserRequest.getUsername()))
        .click();

    $(Selectors.byText("✏️ Edit Profile")).shouldBe(Condition.visible);

    var name = TestDataGenerator.generate(CustomerProfileRequest.class).getName();
    $(Selectors.byAttribute("placeholder", "Enter new name")).sendKeys(name + 1);

    $("button.btn.btn-primary.mt-3")
        .shouldHave(Condition.exactText("💾 Save Changes"))
        .click();

    Alert alert = switchTo().alert();

    assertThat(alert.getText()).contains("Name must contain two words with letters only");

    alert.accept();

    Selenide.refresh();

    $(Selectors.byClassName("profile-header"))
        .shouldBe(Condition.visible)
        .shouldHave(Condition.text("noname"))
        .shouldHave(Condition.text(createUserRequest.getUsername()));

    var actualName = new ValidatedRequester<CreateUserResponse>(
        Endpoint.GET_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk()
    ).get().getName();

    assertThat(actualName).isNull();
  }
}
