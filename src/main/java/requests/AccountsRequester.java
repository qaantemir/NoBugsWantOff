package requests;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import models.AccountsDepositRequest;
import models.AccountsTransferRequest;

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

    public ValidatableResponse transferMoney(AccountsTransferRequest accountsTransferRequest) {
        return given()
                .spec(requestSpecification)
                .body(accountsTransferRequest)
                .post("/api/v1/accounts/transfer")
                .then()
                .spec(responseSpecification);
    }

    public ValidatableResponse depositMoney(AccountsDepositRequest accountsDepositRequest) {
        return given()
                .spec(requestSpecification)
                .body(accountsDepositRequest)
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
