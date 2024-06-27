package com.verygoodbank.tes.validator.impl;

import com.verygoodbank.tes.model.AbstractTradeData;
import com.verygoodbank.tes.validator.TradeDataDateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class BaseTradeDataDateValidator implements TradeDataDateValidator {

    private final DateTimeFormatter formatter;

    public BaseTradeDataDateValidator(@Value("${trade-data.date-pattern}") final String tradeDataDatePattern) {
        this.formatter = DateTimeFormatter.ofPattern(tradeDataDatePattern);
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
