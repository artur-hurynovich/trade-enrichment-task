package com.verygoodbank.tes.validator.impl;

import com.verygoodbank.tes.model.AbstractTradeData;
import com.verygoodbank.tes.validator.TradeDataDateValidator;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BaseTradeDataDateValidator implements TradeDataDateValidator {

    private final DateTimeFormatter formatter;

    public BaseTradeDataDateValidator(final String tradeDataDatePatter) {
        this.formatter = DateTimeFormatter.ofPattern(tradeDataDatePatter);
    }

    @Override
    public boolean isValidDate(final AbstractTradeData tradeData) {
        final String tradeDataDate = tradeData.getDate();

        try {
            formatter.parse(tradeDataDate);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
