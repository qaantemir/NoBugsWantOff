package java_express.for_unit_tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.ValueSources;

import javax.print.attribute.standard.MediaSize;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindSecondMaxTest extends FunctionsTest {

    static Stream<Arguments> validArraysForFindSecondMax() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3}, 2),
                Arguments.of(new int[]{4, 2, 3}, 3),
                Arguments.of(new int[]{4, 7}, 4),
                Arguments.of(new int[]{40, 7, -10}, 7),
                Arguments.of(new int[]{-40, -100, -10}, -40),
                Arguments.of(new int[]{100, 100}, 100)
        );
    }

    @ParameterizedTest
    @MethodSource("validArraysForFindSecondMax")
    void userInputValidValuesAndGetValidResult(int[] intArray, int expectedResult) {
        int actualResult = functions.findSecondMax(intArray);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void userInpuArrayWithOneElementAndGetException() {
        assertThrows(NoSuchElementException.class,
                () -> functions.findMax(new int[]{1}));
    }

    @Test
    void userInputEmptyArrayAndGetException() {
        assertThrows(NoSuchElementException.class,
                () -> functions.findMax(new int[0]));

    }
}
