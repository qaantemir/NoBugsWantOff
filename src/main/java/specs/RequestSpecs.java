package specs;

import configs.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.AuthLoginRequest;
import requests.skelethon.Endpoint;
import requests.skelethon.requesters.UnvalidatedRequester;

public class RequestSpecs {

  private final static RequestSpecs INSTANCE = new RequestSpecs() {
  };
  // TODO возможно потребуется метод с доступом к хедерам по юзернейму
  private final static Map<String, String> authHeaders = new HashMap<>(
      Map.of("admin", "Basic YWRtaW46YWRtaW4="));

  private RequestSpecs() {
  }

  ;

  private static RequestSpecBuilder defaultRequestBuilder() {
    return new RequestSpecBuilder()
        .setAccept(ContentType.JSON)
        .setContentType(ContentType.JSON)
        .addFilters(List.of(new RequestLoggingFilter(),
            new ResponseLoggingFilter()))
        .setBaseUri(Config.getProperty("url") + Config.getProperty("apiVersion"));
  }

//  private static RequestSpecBuilder defaultRequestBuilder() {
//    return new RequestSpecBuilder()
//        .setAccept(ContentType.JSON)
//        .setContentType(ContentType.JSON)
//        .addFilters(List.of(new RequestLoggingFilter(),
//            new ResponseLoggingFilter()))
//        .setBaseUri("http://localhost:4111");
//  }

  public static RequestSpecification unauthSpec() {
    return defaultRequestBuilder().build();
  }

  public static RequestSpecification adminSpec() {
    return defaultRequestBuilder()
        .addHeader("Authorization", "Basic YWRtaW46YWRtaW4=")
        .build();
  }

  public static RequestSpecification authUser(AuthLoginRequest authLoginRequest) {
    String token;
    if (authHeaders.containsKey(authLoginRequest.getUsername())) {
      token = authHeaders.get(authLoginRequest.getUsername());
    } else {
      token = new UnvalidatedRequester(Endpoint.AUTH_LOGIN, unauthSpec(), ResponseSpecs.requestReturnsOk())
          .post(authLoginRequest)
          .extract()
          .header("Authorization");
      authHeaders.put(authLoginRequest.getUsername(), token);
    }
    return defaultRequestBuilder()
        .addHeader("Authorization", token)
        .build();
  }

  public static RequestSpecification userSpec(String authToken) {
    return defaultRequestBuilder()
        .addHeader("Authorization", authToken)
        .build();
  }


}
