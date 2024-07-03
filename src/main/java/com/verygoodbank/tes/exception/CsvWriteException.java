package com.verygoodbank.tes.exception;

/**
 * A common {@code RuntimeException} extension thrown on any {@code IOException} during the writing a CSV file
 */
public class CsvWriteException extends RuntimeException {

    public CsvWriteException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
