package java_express.for_unit_tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsValidEmailTest extends FunctionsTest {

    /**
     * Позитив
     * aqa@nobug.me -> true
     * AA@mail.ru -> true
     * a@MAIL.NET -> true
     * a@mail.NET -> true
     * a@MAIL.net -> true
     * ivan.ivan@y.org -> true
     * sova@456.ru -> true
     * qwerty@mail.123 -> true
     *
     * @.com -> false
     * @. -> false
     * @mail.com -> false
     * a@.com -> false
     * a@mailcom -> false
     * a@mail,com -> false
     * a@mail. -> false
     * a@mail.ahah.ru -> false
     * ""
     *
     * Негатив:
     * null
     * ""
     */

    @DisplayName("isValidEmail Вводим корректные email")
    @ParameterizedTest
    @ValueSource(strings = {
            "aqa@nobug.me",
            "AA@mail.ru",
            "a@MAIL.NET",
            "a@mail.NET",
            "a@MAIL.net",
            "ivan.ivan@y.org",
            "sova@456.ru",
            "qwerty@mail.123",
            "a@mail.ahah.ru"
    })
    void userInputValidEmailAndGetTrue(String email) {
        assertTrue(functions.isValidEmail(email));
    }

    @DisplayName("isValidEmail Вводим некорректные email")
    @ParameterizedTest
    @ValueSource(strings = {
            "@.com",
            "@.",
            "@mail.com",
            "a@.com",
            "a@mailcom",
            "a@mail,com",
            "a@mail."
    })
    void userInputInvalidEmailAndGetFalse(String email) {
        assertFalse(functions.isValidEmail(email));
    }

    @DisplayName("isValidEmail Вводим null")
    @Test
    void userInputNullEmailAndGetFalse() {
        assertFalse(functions.isValidEmail(null));
    }


}
