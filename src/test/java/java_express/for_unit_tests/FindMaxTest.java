package java_express.for_unit_tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindMaxTest extends FunctionsTest {

    /**
     * Позитивные:
     * [1, 2, 3] -> 3
     * [4, 4] -> 4
     * [5] -> 5
     * Угловые:
     * [] -> []
     */

    static Stream<Arguments> validValuesForMax() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3}, 3),
                Arguments.of(new int[]{4, 4}, 4),
                Arguments.of(new int[]{5}, 5)
        );
    }

    @DisplayName("findMax Передаем валидные массивы")
    @ParameterizedTest
    @MethodSource("validValuesForMax")
    void userInputValidArrayAndGetMaxValue(int[] intArray, int expectedResult) {
        int actualResult = functions.findMax(intArray);
        assertEquals(expectedResult, actualResult);
    }

    @DisplayName("findMax Передаем пустой массив с нулевым капасити")
    @Test
    void userInputEmptyArrayWithCapacityEqualsZeroAndGetException() {
        assertThrows(NoSuchElementException.class, () -> functions.findMax(new int[0]));
    }

    @DisplayName("findMax Передаем пустой массив с ненулевым капасити")
    @Test
    void userInputEmptyArrayWithCapacityMoreThanZeroZeroAndGetException() {
        assertThrows(NoSuchElementException.class, () -> functions.findMax(new int[10]));
    }


}
