package com.verygoodbank.tes.validator.impl;

import com.verygoodbank.tes.validator.DateFormatValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

class BaseDateFormatValidatorTest {

    private final DateFormatValidator validator = new BaseDateFormatValidator("yyyyMMdd");

    @ParameterizedTest
    @MethodSource("isValidDateTestArguments")
    void given_tradeData_when_isValidFormat_then_returnCorrectValidationResult(final String date,
                                                                               final boolean expectedValidationResult) {
        final boolean actualValidationResult = validator.isValidFormat(date);

        Assertions.assertEquals(expectedValidationResult, actualValidationResult);
    }

    private static List<Arguments> isValidDateTestArguments() {
        return List.of(
                Arguments.of("20240628", true),
                Arguments.of("28062024", false),
                Arguments.of("2024-06-28", false),
                Arguments.of("2024/06/28", false),
                Arguments.of("20241328", false),
                Arguments.of("20240632", false));
    }
}
