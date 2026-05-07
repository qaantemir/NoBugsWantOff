package ui;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.refresh;
import static org.assertj.core.api.Assertions.assertThat;

import api.generators.TestDataGenerator;
import api.models.AuthLoginRequest;
import api.models.CreateUserRequest;
import api.models.CreateUserResponse;
import api.models.CustomerProfileRequest;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.ValidatedRequester;
import api.requests.steps.AdminSteps;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.api.Test;

public class NameUiTest extends BaseUiTest {

  @Test
  void validNameShouldBeSetup() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);
    String name = TestDataGenerator.generate(CustomerProfileRequest.class).getName();

    authAsUser(authLoginRequest);

    new UserDashboard()
        .open()
        .clickOnUserinfo()
        .getUserinfoName()
        .shouldHave(exactText(UserDashboard.USER_INFO_DEFAULT_NAME));

    new EditProfilePage()
        .updateName(name)
        .checkAndAcceptAlert(Alert.NAME_UPDATED_SUCCESSFULLY);

    new UserDashboard()
        .open()
        .clickOnUserinfo()
        .getUserinfoName()
        .shouldHave(exactText(name));

    var actualName = new ValidatedRequester<CreateUserResponse>(
        Endpoint.GET_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk()
    ).get().getName();

    assertThat(actualName).isEqualTo(name);  }

  @Test
  void invalidNameShouldReturnFail() {
    CreateUserRequest createUserRequest = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(
        createUserRequest);
    String name = TestDataGenerator.generate(CustomerProfileRequest.class).getName() + "123";

    authAsUser(authLoginRequest);

    new UserDashboard()
        .open()
        .clickOnUserinfo()
        .getUserinfoName()
        .shouldHave(exactText(UserDashboard.USER_INFO_DEFAULT_NAME));

    new EditProfilePage().
        updateName(name)
        .checkAndAcceptAlert(Alert.NAME_MUST_CONTAIN_TWO_WORDS_WITH_LETTERS_ONLY);

    new UserDashboard()
        .open()
        .clickOnUserinfo()
        .getUserinfoName()
        .shouldHave(exactText(UserDashboard.USER_INFO_DEFAULT_NAME));

    var actualName = new ValidatedRequester<CreateUserResponse>(
        Endpoint.GET_CUSTOMER_PROFILE,
        RequestSpecs.authUser(authLoginRequest),
        ResponseSpecs.requestReturnsOk()
    ).get().getName();

    assertThat(actualName).isNull();
  }
}
