package ui;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

public class LoginPage extends BasePage<LoginPage> {
  SelenideElement usernameInputPlaceholder = $(Selectors.byAttribute("placeholder", "Username"));
  SelenideElement passwordInputPlaceholder = $(Selectors.byAttribute("placeholder", "Password"));
  SelenideElement loginButton = $(Selectors.byTagName("button"));

  public LoginPage login(String username, String password) {
    usernameInputPlaceholder.sendKeys(username);
    passwordInputPlaceholder.sendKeys(password);
    loginButton.click();
    return this;
  }

  @Override
  public String url() {
    return "/login";
  }
}
