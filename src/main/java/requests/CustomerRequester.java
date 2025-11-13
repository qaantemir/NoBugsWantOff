package requests;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.UpdateNameRequest;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CustomerRequester extends BaseRequest {
    public CustomerRequester(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        super(requestSpecification, responseSpecification);
    }

    public ValidatableResponse getCustomerProfile() {
        return given()
                .spec(requestSpecification)
                .get("/api/v1/customer/profile")
                .then()
                .spec(responseSpecification);
    }

    public ValidatableResponse updateCustomerName(UpdateNameRequest updateNameRequest) {
        return given()
                .spec(requestSpecification)
                .body(updateNameRequest)
                .put("/api/v1/customer/profile")
                .then()
                .spec(responseSpecification);
    }

    public ValidatableResponse getCustomerAccounts() {
        return given()
                .spec(requestSpecification)
                .get("/api/v1/customer/accounts")
                .then()
                .spec(responseSpecification);
    }
}
