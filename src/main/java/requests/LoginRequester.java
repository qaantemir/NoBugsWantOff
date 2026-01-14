package requests;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.AuthLoginRequest;

import static io.restassured.RestAssured.given;

public class LoginRequester extends BaseRequest {

    public LoginRequester(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        super(requestSpecification, responseSpecification);
    }

    public ValidatableResponse login(AuthLoginRequest authLoginRequest) {
        return given()
                .spec(requestSpecification)
                .body(authLoginRequest)
                .post("/api/v1/auth/login")
                .then()
                .spec(responseSpecification);
    }
}
