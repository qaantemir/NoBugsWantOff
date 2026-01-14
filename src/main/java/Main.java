import requests.UserRequester;
import requests.skelethon.Endpoint;
import specs.RequestSpecs;
import specs.ResponseSpecs;

public class Main {

  public static void main(String[] args) {
    Endpoint e = Endpoint.ACCOUNTS_TRANSACTIONS;
    System.out.println(e);
    System.out.println(e.getUrl());
    e.setPathParam("1");
    System.out.println(e.getUrl());

    System.out.println("accounts/%s/transactions".formatted("1"));

  }
}
