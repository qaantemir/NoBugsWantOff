package ui;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.Alert;

@Getter
public class EditProfilePage extends BasePage<EditProfilePage> {
  SelenideElement pageTitle = $(Selectors.byText("✏️ Edit Profile"));
  SelenideElement nameInputPlaceholder = $(Selectors.byAttribute("placeholder", "Enter new name"));
  SelenideElement saveChangesButton = $("button.btn.btn-primary.mt-3");

  @Override
  public String url() {
    return "/edit-profile";
  }

  public EditProfilePage updateName(String name) {
    this.nameInputPlaceholder.sendKeys(name);
    this.saveChangesButton.click();
    return this;
  }

  public EditProfilePage checkAndAcceptAlert(ui.Alert expectedAlert) {
    Alert alert = switchTo().alert();
    var alertText = alert.getText();
    assertThat(alertText).contains(expectedAlert.getAlertText());
    alert.accept();
    return this;
  }

}
