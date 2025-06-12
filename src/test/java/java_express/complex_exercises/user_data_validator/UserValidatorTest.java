package java_express.complex_exercises.user_data_validator;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserValidatorTest {
    UserValidator<Person> userValidator;
    Person person;

    @BeforeEach
    void setupTest() {
        userValidator = new UserValidator<>();
    }


    /**
     * Позитивные кейсы с флагом тру:
     * Корректный пользователь -> ок
     * name "" -> exception
     * name "nick" -> exception
     * name null -> exception
     * age 17 -> exception
     * age 18 -> ок
     * age 100 -> ок
     * age 101 -> exception
     * email - скопировать
     * Позитивные кейсы с флагом фолс:
     * null везде -> ок
     *
     * Негативные кейсы
     * age -1
     */


    static Stream<Arguments> validValuesForUserValidation() {
        return Stream.of(
                Arguments.of(new Person("Ivy", 18, "ivy@valve.com")),
                Arguments.of(new Person("Ivy", 100, "ivy@valve.com"))
        );
    }

    static Stream<Arguments> invalidValuesForUserValidation() {
        return Stream.of(
                Arguments.of(new Person("", 22, "ivy@valve.com")),
                Arguments.of(new Person("ivy", 22, "ivy@valve.com")),
                Arguments.of(new Person(null, 22, "ivy@valve.com")),
                Arguments.of(new Person("Ivy", 17, "ivy@valve.com")),
                Arguments.of(new Person("Ivy", 101, "ivy@valve.com")),
                Arguments.of(new Person("Ivy", -101, "ivy@valve.com"))

        );
    }



    @SneakyThrows
    @ParameterizedTest
    @MethodSource("validValuesForUserValidation")
    void userValidDataGetTrueOnValidationEnabled(Person person) {
        UserValidator.setValidation(true);
        boolean actualResult = userValidator.validateUserData(person);
        assertTrue(actualResult);
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("invalidValuesForUserValidation")
    void userWithBlankNameShouldGetExceptionOnValidationEnabled(Person person) {
        UserValidator.setValidation(true);
        assertThrows(InvalidUserException.class, () -> userValidator.validateUserData(person));
    }

    @SneakyThrows
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
        UserValidator.setValidation(true);
        assertTrue(userValidator.validateUserData(new Person("Yoshi", 99, email)));
    }

    @SneakyThrows
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
        UserValidator.setValidation(true);
        assertThrows(InvalidUserException.class,
                () -> userValidator.validateUserData(new Person("Yoshi", 99, email)));
    }

    @SneakyThrows
    @Test
    void userInputNullEmailAndGetFalse() {
        UserValidator.setValidation(true);
        assertThrows(InvalidUserException.class,
                () -> userValidator.validateUserData(new Person("Yoshi", 99, null)));
    }

    @SneakyThrows
    @Test
    void userDontValidateWithoutFlag() {
        UserValidator.setValidation(false);
        assertTrue(userValidator.validateUserData(new Person(null, null, null)));
    }
}