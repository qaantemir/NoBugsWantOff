package ui;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

public class TransferPage extends BasePage<TransferPage> {
  ElementsCollection selectAccount = $$("select.form-control.account-selector option").filterBy(not(attribute("value", "")));
  SelenideElement recipientNamePlaceholder = $(Selectors.byAttribute("placeholder", "Enter recipient name"));
  SelenideElement recipientAccountNumberPlaceholder = $(Selectors.byAttribute("placeholder", "Enter recipient account number"));
  SelenideElement amountPlaceholder = $(Selectors.byAttribute("placeholder", "Enter amount"));
  SelenideElement confirmCheckBox = $(Selectors.byId("confirmCheck"));
  SelenideElement confirmDepositButton = $("button[class='btn-primary shadow-custom green-btn mt-4']");
  public final static Double MAX_TRANSFER_VALUE = 10000.;

  @Override
  public String url() {
    return "/transfer";
  }

  public TransferPage transferMoney(SelenideElement accountSelect, String recipientName, String recipientAccountNumber, Double amount) {
    Selenide.sleep(1000);
    accountSelect.click();
    this.recipientNamePlaceholder.setValue(recipientName);
    this.recipientAccountNumberPlaceholder.sendKeys(recipientAccountNumber);
    this.amountPlaceholder.sendKeys(amount.toString());
    this.confirmCheckBox.click();
    this.confirmDepositButton.click();
    return this;
  }

  public TransferPage checkAndAcceptAlert(ui.Alert expectedAlert) {
    org.openqa.selenium.Alert alert = switchTo().alert();
    var alertText = alert.getText();
    assertThat(alertText).contains(expectedAlert.getAlertText());
    alert.accept();
    return this;
  }
}
