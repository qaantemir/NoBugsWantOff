package requests;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.DepositRequest;
import models.TransferRequest;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class AccountsRequester extends BaseRequest {
    public AccountsRequester(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        super(requestSpecification, responseSpecification);
    }

    public ValidatableResponse createAccount() {
        return given()
                .spec(requestSpecification)
                .post("/api/v1/accounts")
                .then()
                .spec(responseSpecification);
    }

    public ValidatableResponse transferMoney(TransferRequest transferRequest) {
        return given()
                .spec(requestSpecification)
                .body(transferRequest)
                .post("/api/v1/accounts/transfer")
                .then()
                .spec(responseSpecification);
    }

    public ValidatableResponse depositMoney(DepositRequest depositRequest) {
        return given()
                .spec(requestSpecification)
                .body(depositRequest)
                .post("/api/v1/accounts/deposit")
                .then()
                .spec(responseSpecification);
    }

    public ValidatableResponse getAccountTransactions(long id) {
        return given()
                .spec(requestSpecification)
                .get("/api/v1/accounts/%d/transactions".formatted(id))
                .then()
                .spec(responseSpecification);
    }

    public ValidatableResponse deleteAccount(long id) {
        return given()
                .spec(requestSpecification)
                .delete("/api/v1/accounts/%d".formatted(id))
                .then()
                .spec(responseSpecification);
    }



}
