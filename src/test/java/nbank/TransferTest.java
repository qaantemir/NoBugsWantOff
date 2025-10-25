package nbank;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import java.util.List;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Data;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TransferTest {

  private static User admin = User.builder()
      .username("admin")
      .password("admin")
      .authToken("Basic YWRtaW46YWRtaW4=")
      .build();
  private static User user1 = User.builder()
      .username("kate0001")
      .password("verysTRongPassword33$")
      .build();;
  private static User user2 = User.builder()
      .username("kate0002")
      .password("verysTRongPassword33$")
      .build();;





  @BeforeAll
  public static void setupTests() {
    RestAssured.filters(
        List.of(new RequestLoggingFilter(),
            new ResponseLoggingFilter())
    );
  }

  @BeforeEach
  void setupTest() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "admin",
              "password": "admin"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login");

    for (var u : List.of(user1, user2)) {
      given()
          .contentType(ContentType.JSON)
          .accept(ContentType.JSON)
          .header("Authorization", "Basic YWRtaW46YWRtaW4=")
          .body("""
              {
                "username": "%s",
                "password": "%s",
                "role": "USER"
              }
              """.formatted(u.getUsername(), u.getPassword()))
          .post("http://localhost:4111/api/v1/admin/users");
    }

    for (var u : List.of(user1, user2)) {
      u.setAuthToken(given()
          .contentType(ContentType.JSON)
          .accept(ContentType.JSON)
          .body("""
              {
                "username": "%s",
                "password": "%s"
              }
              """.formatted(u.getUsername(), u.getPassword()))
          .post("http://localhost:4111/api/v1/auth/login")
          .then()
          .extract()
          .header("Authorization"));
    }

    for (var u : List.of(user1, user2)) {
      u.setAccId1(given()
          .accept(ContentType.ANY)
          .header("Authorization", u.getAuthToken())
          .post("http://localhost:4111/api/v1/accounts")
          .then()
          .extract()
          .response()
          .body()
          .jsonPath()
          .getInt("id"));

      u.setAccId2(given()
          .accept(ContentType.ANY)
          .header("Authorization", u.getAuthToken())
          .post("http://localhost:4111/api/v1/accounts")
          .then()
          .extract()
          .response()
          .body()
          .jsonPath()
          .getInt("id"));
    }
  }

  @AfterEach
  void clearUsers() {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "admin",
              "password": "admin"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login");

    ResponseBody rbody = given()
        .accept(ContentType.ANY)
        .header("Authorization", admin.getAuthToken())
        .get("http://localhost:4111/api/v1/admin/users")
        .then()
        .extract()
        .response()
        .body();

    user1.setId(rbody.jsonPath().getInt("[0].id"));
    user2.setId(rbody.jsonPath().getInt("[1].id"));

    for (var u : List.of(user1, user2)) {
      given()
          .contentType(ContentType.JSON)
          .accept(ContentType.JSON)
          .header("Authorization", admin.getAuthToken())
          .delete("http://localhost:4111/api/v1/admin/users/%d".formatted(u.getId()));
    }
  }

//  - Позитив: Перевод между своими существующими аккаунтами, баланс 10000, перевод 1
//  - Позитив: Перевод между своими существующими аккаунтами, баланс 10000, перевод 10000
//  - Позитив: Перевод со своего аккаунт на чужой существующий аккаунт, баланс 10000, перевод 1
//  - Позитив: Перевод со своего аккаунт на чужой существующий аккаунт, баланс 10000, перевод 10000



  @Test
  void oneUnitValueShouldBetweenOwnerAccountsReturnSuccess() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user1.getAccId1(), user1.getAccId2(), 1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  void fiveThousandUnitsBetweenOwnerAccountsShouldReturnSuccess() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user1.getAccId1(), user1.getAccId2(), 5000))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  void oneUnitValueShouldBetweenOwnerAndAlienAccountsReturnSuccess() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user1.getAccId1(), user2.getAccId1(), 1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  void fiveThousandUnitsBetweenOwnerAndAlienAccountsShouldReturnSuccess() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user1.getAccId1(), user2.getAccId1(), 5000))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  void transferZeroShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user1.getAccId1(), user2.getAccId1(), 0))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void transferMoreThan10001ShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user1.getAccId1(), user2.getAccId1(), 10001))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void transferFromNotExistAccountShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user1.getAccId1() + 1, user2.getAccId1(), 1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }


  @Test
  void transferToNotExistAccountShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user2.getAccId1(), user1.getAccId1(), 1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_FORBIDDEN);
  }

  @Test
  void transferNegativeValueAccountShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user1.getAccId1(), user1.getAccId2(), -1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void transferFloatValueAccountShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": %f
            }
            """.formatted(user1.getAccId1(), user1.getAccId2(), 0.1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void nullValueOnSenderAccountIdShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": null,
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user2.getAccId1(), 1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void nullValueOnReceiverAccountIdShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": null,
              "amount": %d
            }
            """.formatted(user1.getAccId1(), 1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void nullValueOnAmountShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": null
            }
            """.formatted(user1.getAccId1(), user2.getAccId1()))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void stringValueOnSenderAccountIdShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": "%d",
              "receiverAccountId": %d,
              "amount": %d
            }
            """.formatted(user1.getAccId1(), user2.getAccId1(), 1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void stringValueOnReceiverAccountIdShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": "%d",
              "amount": %d
            }
            """.formatted(user1.getAccId1(), user2.getAccId1(), 1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void stringValueOnAmountShouldReturnFail() {

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "id": %d,
              "balance": %d
            }
            """.formatted(user1.getAccId1(), 5000))
        .post("http://localhost:4111/api/v1/accounts/deposit");

    given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .header("Authorization", user1.getAuthToken())
        .body("""
            {
              "senderAccountId": %d,
              "receiverAccountId": %d,
              "amount": "%d"
            }
            """.formatted(user1.getAccId1(), user2.getAccId1(), 1))
        .when()
        .post("http://localhost:4111/api/v1/accounts/transfer")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

}

@Builder
@Data
class User {

  private int id;
  private String username;
  private String password;
  private String authToken;
  private int accId1;
  private int accId2;
}