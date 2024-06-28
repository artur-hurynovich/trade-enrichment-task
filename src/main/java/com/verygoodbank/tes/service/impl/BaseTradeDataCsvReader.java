package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.exception.CsvReadException;
import com.verygoodbank.tes.service.TradeDataCsvReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class BaseTradeDataCsvReader implements TradeDataCsvReader {

    private static final String SEPARATOR = ",";

    private static final String FAILED_TO_READ_FILE_MSG = "Failed to read file: ";

    @Override
    public List<String[]> read(final MultipartFile file) {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return bufferedReader
                    .lines()
                    .map(line -> line.split(SEPARATOR))
                    .toList();
        } catch (final IOException e) {
            throw new CsvReadException(FAILED_TO_READ_FILE_MSG + file.getOriginalFilename(), e);
        }
    }
}
