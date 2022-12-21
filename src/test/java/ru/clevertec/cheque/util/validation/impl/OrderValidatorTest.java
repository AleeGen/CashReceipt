package ru.clevertec.cheque.util.validation.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderValidatorTest {
    private static OrderValidator validator;

    private static Stream<Arguments> argsValid() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("gs5C}:c2/"),
                Arguments.of("    "),
                Arguments.of("1-a"),
                Arguments.of("1-2-3-4"),
                Arguments.of("-6-3"),
                Arguments.of("3-4 card1234"),
                Arguments.of("1-2card-1234"),
                Arguments.of("card-1234"),
                Arguments.of("2-3 card-12345")
        );
    }

    private static Stream<Arguments> argsNotValid() {
        return Stream.of(
                Arguments.of("1-1"),
                Arguments.of("0-15"),
                Arguments.of("23-6 5-13"),
                Arguments.of("78-11 5-23 113-2"),
                Arguments.of("23-6 5-13 card-1234"),
                Arguments.of("23-6     5-13       card-6666     ")
        );
    }

    @BeforeAll
    static void init() {
        validator = new OrderValidator();
    }

    @ParameterizedTest(name = "{index} : {0}")
    @MethodSource(value = "argsNotValid")
    void checkValidateShouldReturnTrue(String str) {
        boolean actual = validator.validate(str);
        assertThat(actual).isTrue();
    }

    @ParameterizedTest(name = "{index} : {0}")
    @MethodSource(value = "argsValid")
    void checkValidateShouldReturnFalse(String str) {
        boolean actual = validator.validate(str);
        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @NullSource
    void checkValidateShouldThrowNullPointerException(String data) {
        assertThrows(NullPointerException.class, () -> validator.validate(data));
    }

}