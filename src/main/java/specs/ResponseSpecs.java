package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ResponseSpecs {
    private ResponseSpecs() {};

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

    public static ResponseSpecification requestReturnsNotFound(String errorKey, ErrorCode errorCode) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .expectBody(errorKey, Matchers.equalTo(errorCode.getErrorCode()))
                .build();
    }

    public static ResponseSpecification requestReturnsBadRequest(ErrorCode errorCode) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody(Matchers.equalTo(errorCode.getErrorCode()))
                .build();
    }

    public static ResponseSpecification requestReturnsForbidden(ErrorCode errorCode) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_FORBIDDEN)
                .expectBody(Matchers.equalTo(errorCode.getErrorCode()))
                .build();
    }

    public static ResponseSpecification requestReturnsUnauthorized(String errorKey, ErrorCode errorCode) {
        return defaultResponseBuilder()
                .expectStatusCode(HttpStatus.SC_UNAUTHORIZED)
                .expectBody(errorKey, Matchers.equalTo(errorCode.getErrorCode()))
                .build();
    }
}