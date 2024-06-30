package com.verygoodbank.tes.validator.impl;

import com.verygoodbank.tes.validator.DateFormatValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Base {@code DateFormatValidator} implementation
 */
@Service
public class BaseDateFormatValidator implements DateFormatValidator {

    private final DateTimeFormatter formatter;

    public BaseDateFormatValidator(@Value("${trade-data.date-pattern}") final String tradeDataDatePattern) {
        this.formatter = DateTimeFormatter.ofPattern(tradeDataDatePattern);
    }

    /**
     * See {@link DateFormatValidator#isValidFormat(String)}
     */
    @Override
    public boolean isValidFormat(@NonNull final String date) {
        try {
            formatter.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
