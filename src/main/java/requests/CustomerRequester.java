package requests;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.CustomerProfileRequest;

import static io.restassured.RestAssured.given;

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

    public ValidatableResponse updateCustomerName(CustomerProfileRequest customerProfileRequest) {
        return given()
                .spec(requestSpecification)
                .body(customerProfileRequest)
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
