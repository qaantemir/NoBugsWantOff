package api.specs;

import api.configs.Config;
import api.generators.TestDataGenerator;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import api.models.AuthLoginRequest;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.UnvalidatedRequester;

public class RequestSpecs {

  private final static RequestSpecs INSTANCE = new RequestSpecs() {};
  private final static Map<String, String> authHeaders = new HashMap<>(
      Map.of("admin", "Basic YWRtaW46YWRtaW4="));

  private RequestSpecs() {};

  private static RequestSpecBuilder defaultRequestBuilder() {
    return new RequestSpecBuilder()
        .setAccept(ContentType.JSON)
        .setContentType(ContentType.JSON)
        .addFilters(List.of(new RequestLoggingFilter(),
            new ResponseLoggingFilter()))
        .setBaseUri(Config.getProperty("api.url") + Config.getProperty("api.apiVersion"));
  }

  public static RequestSpecification unauthSpec() {
    return defaultRequestBuilder().build();
  }

  public static RequestSpecification adminSpec() {
    return defaultRequestBuilder()
        .addHeader("Authorization", "Basic YWRtaW46YWRtaW4=")
        .build();
  }

  public static RequestSpecification authUser(AuthLoginRequest authLoginRequest) {
    var username = authLoginRequest.getUsername();
    var password = authLoginRequest.getPassword();
    return authUser(username, password);
  }

  public static RequestSpecification authUser(String username, String password) {
    String token;
    if (authHeaders.containsKey(username)) {
      token = authHeaders.get(username);
    } else {
      AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(username, password);
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

  public static String getAuthToken(String username, String password) {
    String token;
    if (authHeaders.containsKey(username)) {
      token = authHeaders.get(username);
    } else {
      AuthLoginRequest authLoginRequest = TestDataGenerator.Founded.getAuthLoginRequest(username, password);
      token = new UnvalidatedRequester(Endpoint.AUTH_LOGIN, unauthSpec(), ResponseSpecs.requestReturnsOk())
          .post(authLoginRequest)
          .extract()
          .header("Authorization");
      authHeaders.put(authLoginRequest.getUsername(), token);
    }

    return token;
  }

  public static RequestSpecification userSpec(String authToken) {
    return defaultRequestBuilder()
        .addHeader("Authorization", authToken)
        .build();
  }
}
