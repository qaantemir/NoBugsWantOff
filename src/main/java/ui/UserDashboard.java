package ui;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class UserDashboard extends BasePage<UserDashboard> {
  SelenideElement pageTitle = $(Selectors.byText("User Dashboard"));
  SelenideElement welcomeText = $(Selectors.byClassName("welcome-text"));
  SelenideElement depositMoneyButton = $(Selectors.byText("\uD83D\uDCB0 Deposit Money"));
  SelenideElement transferButton = $(Selectors.byText("\uD83D\uDD04 Make a Transfer"));


  @Override
  public String url() {
    return "/dashboard";
  }

  public UserDashboard clickDepositMoney() {
    this.depositMoneyButton.click();
    return this;
  }

  public UserDashboard clickTransferButton() {
    this.transferButton.click();
    return this;
  }

}
