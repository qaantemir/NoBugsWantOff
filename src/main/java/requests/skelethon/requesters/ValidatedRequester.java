package requests.skelethon.requesters;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.BaseModel;
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
  public M get(Long id) {
    return (M) nonValidatedRequester.get(id).extract().as(endpoint.getResponseModel());
  }

  @Override
  public M post() {
    return (M) nonValidatedRequester.post().extract().as(endpoint.getResponseModel());

  }

  @Override
  public M post(BaseModel model) {
    return (M) nonValidatedRequester.post(model).extract().as(endpoint.getResponseModel());
  }

  @Override
  public M put(BaseModel model) {
    return (M) nonValidatedRequester.put(model).extract().as(endpoint.getResponseModel());
  }

  @Override
  public String delete(Long id) {
    return nonValidatedRequester.delete(id).extract().as(String.class);
  }
}
