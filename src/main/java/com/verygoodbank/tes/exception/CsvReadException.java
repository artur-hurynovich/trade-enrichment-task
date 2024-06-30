package com.verygoodbank.tes.exception;

/**
 * A common {@code RuntimeException} extension thrown on any {@code IOException} during the reading of a CSV file
 */
public class CsvReadException extends RuntimeException {

    public CsvReadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
