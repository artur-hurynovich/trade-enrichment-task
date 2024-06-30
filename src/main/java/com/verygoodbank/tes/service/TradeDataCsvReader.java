package com.verygoodbank.tes.service;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * An interface used to read the data from {@code MultipartFile} and map this data to a {@code List} of Strings
 */
public interface TradeDataCsvReader {

    /**
     * The method reads the data from {@code MultipartFile} and map this data to a {@code List} of Strings
     *
     * @param file original {@code MultipartFile} to read the data from, can't be null
     * @return a {@code List} of Strings mapped to the original {@code MultipartFile} data
     */
    List<String[]> read(@NonNull MultipartFile file);
}
