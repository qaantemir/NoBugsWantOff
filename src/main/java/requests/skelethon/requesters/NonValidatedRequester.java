package requests.skelethon.requesters;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.BaseModel;
import models.MockModel;
import requests.skelethon.Endpoint;
import requests.skelethon.HttpRequest;
import requests.skelethon.interfaces.CrudEndpointInterface;

public class NonValidatedRequester extends HttpRequest implements CrudEndpointInterface {

  public NonValidatedRequester(Endpoint endpoint,
      RequestSpecification requestSpecification,
      ResponseSpecification responseSpecification) {
    super(endpoint, requestSpecification, responseSpecification);
  }

  @Override
  public ValidatableResponse get() {
    return given()
        .spec(requestSpecification)
        .get(endpoint.getUrl())
        .then()
        .spec(responseSpecification);
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
    var body = model == null  ? "" : model;

    System.out.println(model.toString());
    System.out.println(endpoint.getUrl());
    return given()
        .spec(requestSpecification)
        .body(body)
        .post(endpoint.getUrl())
        .then()
        .spec(responseSpecification);
  }

  @Override
  public ValidatableResponse put(BaseModel model) {
    var body = model == null  ? "" : model;
    return given()
        .spec(requestSpecification)
        .body(body)
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
