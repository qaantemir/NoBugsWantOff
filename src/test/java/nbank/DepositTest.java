package nbank;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DepositTest {

  @BeforeAll
  public static void setupTests() {
//    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.filters(
        List.of(new RequestLoggingFilter(),
            new ResponseLoggingFilter())
    );
  }

  @BeforeEach
  public void clearDb() {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "admin",
              "password": "admin"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .statusCode(HttpStatus.SC_OK);

    var s = given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .get("http://localhost:4111/api/v1/admin/users")
        .then()
        .extract()
        .response()
        .body().asString();

    if (!s.equals("[]")) {
      Integer id = given()
          .contentType(ContentType.JSON)
          .accept(ContentType.JSON)
          .header("Authorization", "Basic YWRtaW46YWRtaW4=")
          .get("http://localhost:4111/api/v1/admin/users")
          .then()
          .extract()
          .response()
          .jsonPath()
          .getInt("[0].id");

      given()
          .contentType(ContentType.JSON)
          .accept(ContentType.JSON)
          .header("Authorization", "Basic YWRtaW46YWRtaW4=")
          .delete("http://localhost:4111/api/v1/admin/users/%d".formatted(id));
    }
  }

  @ValueSource(ints = {1, 5000})
  @ParameterizedTest
  void validDepositValuesShouldReturnSuccess(int amount) {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "admin",
              "password": "admin"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .statusCode(HttpStatus.SC_OK);

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$",
              "role": "USER"
            }
            """)
        .post("http://localhost:4111/api/v1/admin/users")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

    var auth = given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .extract()
        .header("Authorization");

    var accIid = given()
        .accept(ContentType.ANY)
        .header("Authorization", auth)
        .post("http://localhost:4111/api/v1/accounts")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .response()
        .body()
        .jsonPath()
        .getString("id");

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", auth)
        .body("""
            {
              "id": %s,
              "balance": %d
            }
            """.formatted(accIid, amount))
        .post("http://localhost:4111/api/v1/accounts/deposit")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }


  @Test
  void validDepositOnNotExistAccountShouldReturnFail() {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "admin",
              "password": "admin"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .statusCode(HttpStatus.SC_OK);

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$",
              "role": "USER"
            }
            """)
        .post("http://localhost:4111/api/v1/admin/users")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

    var auth = given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .extract()
        .header("Authorization");

    var accIid = given()
        .accept(ContentType.ANY)
        .header("Authorization", auth)
        .post("http://localhost:4111/api/v1/accounts")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .response()
        .body()
        .jsonPath()
        .getString("id");

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", auth)
        .body("""
            {
              "id": 100,
              "balance": 1
            }
            """)
        .post("http://localhost:4111/api/v1/accounts/deposit")
        .then()
        .statusCode(HttpStatus.SC_FORBIDDEN);
  }

  @Test
  void validDepositOnAlienAccountShouldReturnFail() {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "admin",
              "password": "admin"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .statusCode(HttpStatus.SC_OK);

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$",
              "role": "USER"
            }
            """)
        .post("http://localhost:4111/api/v1/admin/users")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body("""
            {
              "username": "kate1999",
              "password": "verysTRongPassword33$",
              "role": "USER"
            }
            """)
        .post("http://localhost:4111/api/v1/admin/users")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

    var auth = given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .extract()
        .header("Authorization");

    given()
        .accept(ContentType.ANY)
        .header("Authorization", auth)
        .post("http://localhost:4111/api/v1/accounts")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .response()
        .body()
        .jsonPath()
        .getString("id");

    var accIid98 = given()
        .accept(ContentType.ANY)
        .header("Authorization", auth)
        .post("http://localhost:4111/api/v1/accounts")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .response()
        .body()
        .jsonPath()
        .getString("id");

    auth = given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "kate1999",
              "password": "verysTRongPassword33$"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .extract()
        .header("Authorization");

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", auth)
        .body("""
            {
              "id": %s,
              "balance": 1
            }
            """.formatted(accIid98))
        .post("http://localhost:4111/api/v1/accounts/deposit")
        .then()
        .statusCode(HttpStatus.SC_FORBIDDEN);
  }

  @ValueSource(ints = {-1, 0, 5001})
  @ParameterizedTest
  void invalidDepositValuesShouldReturnFail(int amount) {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "admin",
              "password": "admin"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .statusCode(HttpStatus.SC_OK);

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$",
              "role": "USER"
            }
            """)
        .post("http://localhost:4111/api/v1/admin/users")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

    var auth = given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .extract()
        .header("Authorization");

    var accIid = given()
        .accept(ContentType.ANY)
        .header("Authorization", auth)
        .post("http://localhost:4111/api/v1/accounts")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .response()
        .body()
        .jsonPath()
        .getString("id");

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", auth)
        .body("""
            {
              "id": %s,
              "balance": %d
            }
            """.formatted(accIid, amount))
        .post("http://localhost:4111/api/v1/accounts/deposit")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void doubleDepositValuesShouldReturnFail() {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "admin",
              "password": "admin"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .statusCode(HttpStatus.SC_OK);

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$",
              "role": "USER"
            }
            """)
        .post("http://localhost:4111/api/v1/admin/users")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

    var auth = given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .extract()
        .header("Authorization");

    var accIid = given()
        .accept(ContentType.ANY)
        .header("Authorization", auth)
        .post("http://localhost:4111/api/v1/accounts")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .response()
        .body()
        .jsonPath()
        .getString("id");

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", auth)
        .body("""
            {
              "id": %s,
              "balance": 0.1
            }
            """.formatted(accIid))
        .post("http://localhost:4111/api/v1/accounts/deposit")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }


  @Test
  void nullDepositValuesShouldReturnFail() {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "admin",
              "password": "admin"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .statusCode(HttpStatus.SC_OK);

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$",
              "role": "USER"
            }
            """)
        .post("http://localhost:4111/api/v1/admin/users")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

    var auth = given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .extract()
        .header("Authorization");

    var accIid = given()
        .accept(ContentType.ANY)
        .header("Authorization", auth)
        .post("http://localhost:4111/api/v1/accounts")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .response()
        .body()
        .jsonPath()
        .getString("id");

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", auth)
        .body("""
            {
              "id": %s,
              "balance": null
            }
            """.formatted(accIid))
        .post("http://localhost:4111/api/v1/accounts/deposit")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void stringDepositValuesShouldReturnFail() {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "admin",
              "password": "admin"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .statusCode(HttpStatus.SC_OK);

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$",
              "role": "USER"
            }
            """)
        .post("http://localhost:4111/api/v1/admin/users")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

    var auth = given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "username": "kate1998",
              "password": "verysTRongPassword33$"
            }
            """)
        .post("http://localhost:4111/api/v1/auth/login")
        .then()
        .extract()
        .header("Authorization");

    var accIid = given()
        .accept(ContentType.ANY)
        .header("Authorization", auth)
        .post("http://localhost:4111/api/v1/accounts")
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract()
        .response()
        .body()
        .jsonPath()
        .getString("id");

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", auth)
        .body("""
            {
              "id": %s,
              "balance": "null"
            }
            """.formatted(accIid))
        .post("http://localhost:4111/api/v1/accounts/deposit")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

}
