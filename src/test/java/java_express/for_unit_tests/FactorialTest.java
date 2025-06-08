package java_express.for_unit_tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FactorialTest extends FunctionsTest {

    static Stream<Arguments> valuesMoreThanZeroForFactorial() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(1, 1),
                Arguments.of(5,120),
                Arguments.of(7, 5040)
        );
    }

    @DisplayName("factorial передаем простые небольшие числа")
    @ParameterizedTest
    @MethodSource("valuesMoreThanZeroForFactorial")
    void userInputNumMoreThanZeroAndGetFactorial(int num, int expectedResult) {
        int actualResult = functions.factorial(num);
        assertEquals(expectedResult, actualResult);
    }

    @DisplayName("factorial передаем негативное число")
    @Test
    void userInputNegativeNumAndGetException() {
        assertThrows(IllegalArgumentException.class,
                () -> functions.factorial(-10));
    }
}
