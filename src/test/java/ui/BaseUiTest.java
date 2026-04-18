package ui;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

import api.configs.Config;
import api.generators.TestDataGenerator;
import api.models.AuthLoginRequest;
import api.specs.RequestSpecs;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BaseUiTest {

  @BeforeAll
  static void setup() {
    Configuration.remote = Config.getProperty("selenide.remote");
    Configuration.baseUrl = Config.getProperty("selenide.url");
    Configuration.browser = Config.getProperty("selenide.browser");
    Configuration.browserSize = Config.getProperty("selenide.browserSize");
//    Configuration.holdBrowserOpen = true;
    Configuration.timeout = 2000;
    Configuration.pageLoadTimeout = 2000;
    Configuration.pollingInterval = 2000;

    Configuration.browserCapabilities.setCapability("selenoid:options",
        Map.of("enableVNC", true, "enableLog", true)
    );
  }

  public static void waitForPageToLoad() {
    Selenide.sleep(1000); // ждать 1 секунду после каждого действия
  }

  public void authAsUser(String username, String password) {
    open("/");
    var authToken = RequestSpecs.getAuthToken(username, password);
    executeJavaScript("localStorage.setItem('authToken', arguments[0])", authToken);
  }

  public void authAsUser(AuthLoginRequest authLoginRequest) {
    var username = authLoginRequest.getUsername();
    var password = authLoginRequest.getPassword();
    authAsUser(username, password);
  }

}
