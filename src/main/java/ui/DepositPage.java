package ui;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import java.util.List;
import lombok.Getter;

@Getter
public class DepositPage extends BasePage<DepositPage> {
  ElementsCollection selectAccount = $$("select.form-control.account-selector option").filterBy(not(attribute("value", "")));
  SelenideElement amountPlaceholder = $(Selectors.byAttribute("placeholder", "Enter amount"));
  SelenideElement confirmDepositButton = $("button[class='btn btn-primary shadow-custom mt-4']");
  public final static Long MAX_DEPOSIT_VALUE = 5000L;



  @Override
  public String url() {
    return "/deposit";
  }

  public DepositPage depositMoney(SelenideElement accountSelect, Long amount) {
    Selenide.sleep(1000);
    accountSelect.click();
    this.amountPlaceholder.sendKeys(amount.toString());
    this.confirmDepositButton.click();
    return this;
  }

  public SelenideElement getTheOnlyOneAccount() {
    Selenide.sleep(1000);
    assertThat(this.selectAccount).hasSize(1);
    return this.selectAccount.first();
  }

  public DepositPage checkAndAcceptAlert(ui.Alert expectedAlert) {
    org.openqa.selenium.Alert alert = switchTo().alert();
    var alertText = alert.getText();
    assertThat(alertText).contains(expectedAlert.getAlertText());
    alert.accept();
    return this;
  }
}
