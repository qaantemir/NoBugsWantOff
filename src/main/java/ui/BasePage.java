package ui;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

public abstract class BasePage<T extends BasePage> {
  SelenideElement userinfo = $(Selectors.byClassName("user-info"));
  SelenideElement userinfoName = $(Selectors.byClassName("user-name"));
  SelenideElement userinfoUsername = $(Selectors.byClassName("user-username"));

  public abstract String url();

  public T open() {
    return Selenide.open(url(), (Class<T>) this.getClass());
  }

  public <T extends BasePage>  T getPage(Class<T> pageClass) {
    return Selenide.page(pageClass);
  }

  public T clickOnUserinfo() {
    this.userinfo.click();
    return (T) this;
  }
}
