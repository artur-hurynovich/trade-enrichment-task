package com.verygoodbank.tes.validator.impl;

import com.verygoodbank.tes.validator.DateFormatValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class BaseDateFormatValidator implements DateFormatValidator {

    private final DateTimeFormatter formatter;

    public BaseDateFormatValidator(@Value("${trade-data.date-pattern}") final String tradeDataDatePattern) {
        this.formatter = DateTimeFormatter.ofPattern(tradeDataDatePattern);
    }

    @Override
    public boolean isValidFormat(final String date) {
        try {
            formatter.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
