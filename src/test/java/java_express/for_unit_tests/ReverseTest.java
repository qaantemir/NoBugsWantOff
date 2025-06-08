package java_express.for_unit_tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReverseTest extends FunctionsTest {

    /**
     * Позитивные:
     * "arch" -> "hcra"
     * "a" -> "a"
     * "CUT" -> "TUC"
     * ""
     * Негативные:
     * null
     */

    static Stream<Arguments> validValuesForReverse() {
        return Stream.of(
                Arguments.of("arch", "hcra"),
                Arguments.of("a", "a"),
                Arguments.of("CUT", "TUC"),
                Arguments.of("", "")
        );
    }

    @DisplayName("reverse Передаем валидные значения")
    @ParameterizedTest
    @MethodSource("validValuesForReverse")
    void userInputValidValuesAndGetReversedStrings(String inputString, String expectedResult) {
        String actualResult = functions.reverse(inputString);
        assertEquals(expectedResult, actualResult);
    }

    @DisplayName("reverse Передаем null")
    @Test
    void userInputNullAndGetNull() {
        assertEquals(null, functions.reverse(null));
    }
}
