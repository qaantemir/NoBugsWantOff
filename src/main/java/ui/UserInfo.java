package ui;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

public interface UserInfo<T extends BasePage> {

  SelenideElement userinfo = $(Selectors.byClassName("user-info"));
  SelenideElement userinfoName = $(Selectors.byClassName("user-name"));
  SelenideElement userinfoUsername = $(Selectors.byClassName("user-username"));

  default T userinfo() {
    this.userinfo.click();
    return (T) this;
  }

}
