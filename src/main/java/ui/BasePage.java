package ui;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public abstract class BasePage<T extends BasePage> {
  private final SelenideElement userinfo = $(Selectors.byClassName("user-info"));
  private final SelenideElement userinfoName = $(Selectors.byClassName("user-name"));
  private final SelenideElement userinfoUsername = $(Selectors.byClassName("user-username"));
  public final static String USER_INFO_DEFAULT_NAME = "noname";
  public final static String DEFAULT_BALANCE_FIELD_VALUE = "Balance: $0.00";

  public abstract String url();

  public T open() {
    return Selenide.open(url(), (Class<T>) this.getClass());
  }

  public T refresh() {
    Selenide.refresh();
    return (T) this;
  }

  public <T extends BasePage>  T getPage(Class<T> pageClass) {
    return Selenide.page(pageClass);
  }

  public T clickOnUserinfo() {
    Selenide.sleep(1000);
    this.userinfo.click();
    return (T) this;
  }
}
