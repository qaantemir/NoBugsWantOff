import generators.TestDataGenerator;
import models.CreateUserRequest;
import models.CustomerProfileRequest;
import requests.skelethon.Endpoint;

public class Main {

  public static void main(String[] args) {
    Endpoint e = Endpoint.ACCOUNTS_TRANSACTIONS;
    System.out.println(e);
    System.out.println(e.getUrl());
    e.setPathParam("1");
    System.out.println(e.getUrl());

    System.out.println("accounts/%s/transactions".formatted("1"));

//    System.out.println(TestDataGenerator.generate(CreateUserRequest.class));
    System.out.println(TestDataGenerator.generate(CustomerProfileRequest.class));


  }
}
