package com.verygoodbank.tes.validator.impl;

import com.verygoodbank.tes.model.AbstractTradeData;
import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.validator.TradeDataDateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

class BaseTradeDataDateValidatorTest {

    private final TradeDataDateValidator validator = new BaseTradeDataDateValidator("yyyyMMdd");

    @ParameterizedTest
    @MethodSource("isValidDateTestArguments")
    void given_tradeData_when_isValidDate_then_returnCorrectValidationResult(final AbstractTradeData tradeData,
                                                                             final boolean expectedValidationResult) {
        final boolean actualValidationResult = validator.isValidDate(tradeData);

        Assertions.assertEquals(expectedValidationResult, actualValidationResult);
    }

    private static List<Arguments> isValidDateTestArguments() {
        return List.of(
                Arguments.of(generateTradeData("20240628"), true),
                Arguments.of(generateTradeData("28062024"), false),
                Arguments.of(generateTradeData("2024-06-28"), false),
                Arguments.of(generateTradeData("2024/06/28"), false),
                Arguments.of(generateTradeData("20241328"), false),
                Arguments.of(generateTradeData("20240632"), false));
    }

    private static AbstractTradeData generateTradeData(final String date) {
        return new IdentifiedTradeData(date, null, null, null);
    }
}
