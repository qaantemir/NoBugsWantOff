package java_express.for_unit_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.sound.midi.SoundbankResource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CountWordsTest extends FunctionsTest {

    static Stream<Arguments> validSentences() {
        return Stream.of(
                Arguments.of("oh my god!", 3),
                Arguments.of("oh my god !", 3),
                Arguments.of("ha-ha, so fun!!!", 3),
                Arguments.of("fun", 1),
                Arguments.of("oh sheet here we go     again ", 6),
                Arguments.of("oh sheet here we go again     ", 6),
                Arguments.of("!!!", 0),
                Arguments.of("", 0),
                Arguments.of("   ", 0)
        );
    }

    @ParameterizedTest
    @MethodSource("validSentences")
    void userInputValidNotEmptySentenceAndGetWordAmount(String sentence, int expectedResult) {
        int actualResult = functions.countWords(sentence);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void userInputNullAndGetNullPointerExceiption() {
        assertThrows(NullPointerException.class,
                () -> functions.countWords(null));
    }
}
