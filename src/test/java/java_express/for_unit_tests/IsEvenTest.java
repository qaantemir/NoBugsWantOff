package java_express.for_unit_tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IsEvenTest extends FunctionsTest {

    /**
     * Позитивные:
     * 2 -> true
     * 0 -> true
     * -4 -> true
     * 3 -> false
     * -5 -> false
     * Негативные
     * 3.14 -> не компилируется
     * Не инт (String) -> не компилируется
     */
    @DisplayName("isEven() -> Передаем четные значения, ожидаем true на выходе")
    @ParameterizedTest
    @ValueSource(ints = {2, 0, -4})
    void userGetTrueWhenInputEvenNum(int num) {
        boolean actualResult = functions.isEven(num);
        assertTrue(actualResult);
    }

    @DisplayName("isEven() -> Передаем не четные значения, ожидаем false на выходе")
    @ParameterizedTest
    @ValueSource(ints = {3, -5})
    void userGetFalseWhenInputOddNum(int num) {
        boolean actualResult = functions.isEven(num);
        assertFalse(actualResult);
    }
}