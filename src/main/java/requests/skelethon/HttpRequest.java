package requests.skelethon;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public abstract class HttpRequest {
  protected Endpoint endpoint;
  protected RequestSpecification requestSpecification;
  protected ResponseSpecification responseSpecification;

  public HttpRequest(Endpoint endpoint, RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
    this.endpoint = endpoint;
    this.requestSpecification = requestSpecification;
    this.responseSpecification = responseSpecification;
  }
}
