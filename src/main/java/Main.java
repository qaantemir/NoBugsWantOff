import generators.TestDataGenerator;
import generators.TestDataGenerator.RandomData;
import models.AuthLoginRequest;
import models.CreateUserRequest;
import models.CreateUserResponse;
import models.CustomerProfileRequest;
import requests.skelethon.Endpoint;
import requests.steps.AccountsSteps;
import requests.steps.AdminSteps;
import requests.steps.CustomerSteps;

public class Main {

  public static void main(String[] args) {


    CreateUserRequest createUserRequest1 = AdminSteps.createUser();
    CreateUserRequest createUserRequest2 = AdminSteps.createUser();
    AuthLoginRequest authLoginRequest1 = TestDataGenerator.Founded.getAuthLoginRequest(createUserRequest1);
    AuthLoginRequest authLoginRequest2 = TestDataGenerator.Founded.getAuthLoginRequest(createUserRequest2);

    AccountsSteps.createAccount(authLoginRequest1);
    AccountsSteps.createAccount(authLoginRequest1);
    AccountsSteps.createAccount(authLoginRequest2);
    AccountsSteps.createAccount(authLoginRequest2);

    CreateUserResponse createUserResponse1 = CustomerSteps.getCustomerProfiles(authLoginRequest1);
    CreateUserResponse createUserResponse2 = CustomerSteps.getCustomerProfiles(authLoginRequest2);

    System.out.println(createUserResponse1);
    System.out.println(createUserResponse2);


  }
}
