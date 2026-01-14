package requests.skelethon.requesters;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.BaseModel;
import requests.BaseRequest;
import requests.skelethon.Endpoint;
import requests.skelethon.HttpRequest;
import requests.skelethon.interfaces.CrudEndpointInterface;

public class ValidatedRequester<M extends BaseModel> extends HttpRequest implements CrudEndpointInterface {

  private NonValidatedRequester nonValidatedRequester;

  public ValidatedRequester(Endpoint endpoint,
      RequestSpecification requestSpecification,
      ResponseSpecification responseSpecification) {
    super(endpoint, requestSpecification, responseSpecification);
    this.nonValidatedRequester = new NonValidatedRequester(endpoint, requestSpecification, responseSpecification);
  }

  @Override
  public M get() {
    return (M) nonValidatedRequester.get().extract().as(endpoint.getResponseModel());
  }

  @Override
  public ValidatableResponse get(Long id) {
    return given()
        .spec(requestSpecification)
        .get(endpoint.getUrl().formatted(id.toString()))
        .then()
        .spec(responseSpecification);
  }

  @Override
  public ValidatableResponse post() {
    return given()
        .spec(requestSpecification)
        .post(endpoint.getUrl())
        .then()
        .spec(responseSpecification);
  }

  @Override
  public ValidatableResponse post(BaseModel model) {
    return given()
        .spec(requestSpecification)
        .body(model)
        .post(endpoint.getUrl())
        .then()
        .spec(responseSpecification);
  }

  @Override
  public ValidatableResponse put(BaseModel model) {
    return given()
        .spec(requestSpecification)
        .body(model)
        .put(endpoint.getUrl())
        .then()
        .spec(responseSpecification);
  }

  @Override
  public ValidatableResponse delete(Long id) {
    return given()
        .spec(requestSpecification)
        .delete(endpoint.getUrl() + "/" + id.toString())
        .then()
        .spec(responseSpecification);
  }
}
