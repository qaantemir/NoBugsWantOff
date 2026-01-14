package requests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.SneakyThrows;
import models.CreateUserRequest;
import models.CreateUserResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserRequester extends BaseRequest {

    public UserRequester(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        super(requestSpecification, responseSpecification);
    }

    public ValidatableResponse createUser(CreateUserRequest createUserRequest) {
        return given()
                .spec(requestSpecification)
                .body(createUserRequest)
                .post("/api/v1/admin/users")
                .then()
                .spec(responseSpecification);
    }

    @SneakyThrows
    public List<CreateUserResponse> getAllUsers() {
        String body = given()
                .spec(requestSpecification)
                .get("/api/v1/admin/users")
                .then()
                .spec(responseSpecification)
                .extract().body().asString();

        ObjectMapper mapper = new ObjectMapper();

        List<CreateUserResponse> users = mapper.readValue(body, new TypeReference<List<CreateUserResponse>>() {});

        return users;
    }

    public void deleteUser(long id) {
        given()
                .spec(requestSpecification)
                .delete("/api/v1/admin/users/%d".formatted(id))
                .then()
                .spec(responseSpecification);
    }

    public void deleteAllUsers() {
        List<Long> userIdsList = getAllUsers().stream().map(CreateUserResponse::getId).toList();

        for (long userId : userIdsList) {
            deleteUser(userId);
        }
    }

}
