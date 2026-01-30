package ui;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

import com.codeborne.selenide.Configuration;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BaseUiTest {
  @BeforeAll
  static void setup() {
    Configuration.remote = "http://localhost:4444/wd/hub";
    Configuration.baseUrl = "http://nginx";
    Configuration.browser = "firefox";
    Configuration.browserSize = "1920x1080";

    Configuration.browserCapabilities.setCapability("selenoid:options",
        Map.of("enableVNC", true, "enableLog", true)
    );
  }
}
