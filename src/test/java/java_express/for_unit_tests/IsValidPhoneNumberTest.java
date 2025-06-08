package java_express.for_unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class IsValidPhoneNumberTest extends FunctionsTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "+1 1234567890",
            "+321 1234567890",
            "+33 1234567890"
    })
    void userInputValidNumbersWithoutAnotherSymbolsAndGetTrue(String num) {
        boolean actualResult = functions.isValidPhoneNumber(num);
        assertTrue(actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1 1234567890",
            "+11234567890",
            "+1 123456789",
            "+1 123456789123",
            "+1432 456789123",
            "+1 asd1234223",
            "+a2 asd1234223",
            "-2 1234567890",
            "+1234567890",
            "",
            "dfsg"
    })
    void userInputInvalidNumberAndGetFalseAndGetFalse(String num) {
        boolean actualResult = functions.isValidPhoneNumber(num);
        assertFalse(actualResult);
    }

    @Test
    void userInputNullAndGetException() {
        assertThrows(NullPointerException.class,
                () -> functions.isValidPhoneNumber(null));
    }
}
