package requests.skelethon.requesters;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.BaseModel;
import requests.skelethon.Endpoint;
import requests.skelethon.HttpRequest;
import requests.skelethon.interfaces.CrudEndpointInterface;

public class ValidatedRequester<M extends BaseModel> extends HttpRequest implements CrudEndpointInterface {

  private UnvalidatedRequester unvalidatedRequester;

  public ValidatedRequester(Endpoint endpoint,
      RequestSpecification requestSpecification,
      ResponseSpecification responseSpecification) {
    super(endpoint, requestSpecification, responseSpecification);
    this.unvalidatedRequester = new UnvalidatedRequester(endpoint, requestSpecification, responseSpecification);
  }

  @Override
  public M get() {
    return (M) unvalidatedRequester.get().extract().as(endpoint.getResponseModel());
  }

  @Override
  public M get(Long id) {
    return (M) unvalidatedRequester.get(id).extract().as(endpoint.getResponseModel());
  }

  @Override
  public M post() {
    return (M) unvalidatedRequester.post().extract().as(endpoint.getResponseModel());

  }

  @Override
  public M post(BaseModel model) {
    return (M) unvalidatedRequester.post(model).extract().as(endpoint.getResponseModel());
  }

  @Override
  public M put(BaseModel model) {
    return (M) unvalidatedRequester.put(model).extract().as(endpoint.getResponseModel());
  }

  @Override
  public String delete(Long id) {
    return unvalidatedRequester.delete(id).extract().as(String.class);
  }
}
