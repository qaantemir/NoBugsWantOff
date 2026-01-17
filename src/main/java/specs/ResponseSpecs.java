package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ResponseSpecs {
    private ResponseSpecs() {
    }

    ;

    private static ResponseSpecBuilder defaultResponseBuilder() {
        return new ResponseSpecBuilder();
    }

    public static ResponseSpecification requestReturnsCreated() {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_CREATED)
                .build();
    }

    public static ResponseSpecification requestReturnsOk() {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification requestReturnsNotFound(String errorKey, String errorCode) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .expectBody(errorKey, Matchers.equalTo(errorCode))
                .build();
    }

    public static ResponseSpecification requestReturnsBadRequest(String errorCode) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(Matchers.equalTo(errorCode))
                .build();
    }

    public static ResponseSpecification requestReturnsForbidden(String errorCode) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_FORBIDDEN)
                .expectBody(Matchers.equalTo(errorCode))
                .build();
    }

    public static ResponseSpecification requestReturnsUnauthorized(String errorKey, String errorCode) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_UNAUTHORIZED)
                .expectBody(errorKey, Matchers.equalTo(errorCode))
                .build();
    }

}
