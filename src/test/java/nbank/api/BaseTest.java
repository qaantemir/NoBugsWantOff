package nbank.api;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import requests.steps.AdminSteps;

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
        AdminSteps.deleteAllUsers();
    }
}
