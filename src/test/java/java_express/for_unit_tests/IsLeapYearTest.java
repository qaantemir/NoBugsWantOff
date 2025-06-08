package java_express.for_unit_tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsLeapYearTest extends FunctionsTest {

    /**
     * Високосные:
     * 2020, 2000, 1600
     * Не високосные
     * 2021, 2001, 1601
     * Делятся на 100, но не на 400
     * 1900, 2100
     */

    @DisplayName("isLeapYear Передаем високосные года")
    @ParameterizedTest
    @ValueSource(ints = {2020, 2000, 1600})
    void userInputLeapYearAndGetTrue(int year) {
        boolean actualResult = functions.isLeapYear(year);
        assertTrue(actualResult);
    }

    @DisplayName("isLeapYear Передаем не високосные года")
    @ParameterizedTest
    @ValueSource(ints = {2021, 2001, 1601})
    void userInputNotLeapYearAndGetFalse(int year) {
        boolean actualResult = functions.isLeapYear(year);
        assertFalse(actualResult);
    }

    @DisplayName("isLeapYear Передаем года, которые делятся на 100, но не на 400")
    @ParameterizedTest
    @ValueSource(ints = {1900, 2100})
    void userInputYearWhichDivideHundredAndNotDivideFourHundreds(int year) {
        boolean actualResult = functions.isLeapYear(year);
        assertFalse(actualResult);
    }


}
