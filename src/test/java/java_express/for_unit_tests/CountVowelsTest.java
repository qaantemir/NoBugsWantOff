package java_express.for_unit_tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CountVowelsTest extends FunctionsTest {

    /**
     *  Позитивные:
     *  haha -> 2
     *  a -> 1
     *  aeiou -> 5
     *  AAA -> 3
     *  bbb -> 0
     *  f -> 0
     *  "" -> 0
     *  Негативные:
     *  null -> IllegalArgumentException
     *  Угловые:
     *
     */

    static Stream<Arguments> wordForCounterWithVowels() {
        return Stream.of(
                Arguments.of("haha", 2),
                Arguments.of("a", 1),
                Arguments.of("AAA", 3),
                Arguments.of("aeiou", 5)

        );
    }

    @DisplayName("countVowels Передаем значения с гласными")
    @ParameterizedTest
    @MethodSource("wordForCounterWithVowels")
    void userInputWordWithVowelsAndGetThemAmount(String word, int expectedAmount) {
        int actualResult = functions.countVowels(word);
        assertEquals(expectedAmount, actualResult);
    }


    @DisplayName("countVowels Передаем значения без гласных")
    @ParameterizedTest
    @ValueSource(strings = {"bbb", "f", "", "WWW"})
    void userInputWordWithoutVowelsAndGetZero(String word) {
        int actualResult = functions.countVowels(word);
        int expectedResult = 0;
        assertEquals(expectedResult, actualResult);
    }

    @DisplayName("countVowels Передаем null")
    @Test
    void userInputNullAndGetException() {
        assertThrows(IllegalArgumentException.class, () -> functions.countVowels(null));
    }
}
