package com.verygoodbank.tes.service;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * An interface used to write a {@code List} of Strings
 */
public interface TradeDataCsvWriter {

    /**
     * The method writes a {@code List} of Strings to the byte array
     *
     * @param lines a {@code List} of Strings representing some data, can't be null
     * @return a byte array written from the original {@code List} of Strings
     */
    byte[] write(@NonNull List<String[]> lines);
}
