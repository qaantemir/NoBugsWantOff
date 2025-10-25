package nbank;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.stream.Stream;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class NameTest {
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

  private static Stream<Arguments> invalidNames() {
    return Stream.of(
        Arguments.of(" ", HttpStatus.SC_BAD_REQUEST),
        Arguments.of("", HttpStatus.SC_BAD_REQUEST),
        Arguments.of("a", HttpStatus.SC_BAD_REQUEST),
        Arguments.of("a B-c", HttpStatus.SC_BAD_REQUEST),
        Arguments.of("_ B", HttpStatus.SC_BAD_REQUEST)
    );
  }

  @Test
  void nameWithTwoWordsOneSymbolEachShouldBeValid() {
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

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", auth)
        .body("""
            {
              "name": "a B"
            }
            """)
        .put("http://localhost:4111/api/v1/customer/profile")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

  @MethodSource("invalidNames")
  @ParameterizedTest
  void invalidNameShouldReturnFail(String name, int statusCode) {
    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body("""
            {
              "name": "admin",
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
              "name": "kate1998",
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
              "name": "kate1998",
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
              "name": "%s"
            }
            """.formatted(name))
        .put("http://localhost:4111/api/v1/customer/profile")
        .then()
        .statusCode(statusCode);
  }

  @Test
  void intTypeOfNameShouldReturnFail() {
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

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", auth)
        .body("""
            {
              "name": 1
            }
            """)
        .put("http://localhost:4111/api/v1/customer/profile")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  void nullValuerOfNameShouldReturnFail() {
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

    given()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .header("Authorization", auth)
        .body("""
            {
              "name": null
            }
            """)
        .put("http://localhost:4111/api/v1/customer/profile")
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }
}
