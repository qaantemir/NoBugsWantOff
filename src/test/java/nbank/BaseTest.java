package nbank;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import requests.UserRequester;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public abstract class BaseTest {
    SoftAssertions softly;

    @BeforeEach
    public void setupTests() {
        this.softly = new SoftAssertions();
    }

    @AfterEach
    public void afterTest() {
        this.softly.assertAll();
    }

    @AfterEach
    void clearDb() {
        new UserRequester(RequestSpecs.adminSpec(), ResponseSpecs.requestReturnsOk())
                .deleteAllUsers();
    }
}
