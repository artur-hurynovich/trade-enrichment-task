package com.verygoodbank.tes.validator;

import org.springframework.lang.NonNull;

/**
 * An interface to validate if the specified date matches a pattern
 */
public interface DateFormatValidator {

    /**
     * The method validates if the specified date matches a pattern
     *
     * @param date original date, can't be null
     * @return true if the original date matches a pattern, false - otherwise
     */
    boolean isValidFormat(@NonNull String date);
}
