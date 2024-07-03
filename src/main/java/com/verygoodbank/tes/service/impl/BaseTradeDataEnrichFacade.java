package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.exception.CsvReadException;
import com.verygoodbank.tes.exception.CsvWriteException;
import com.verygoodbank.tes.service.TradeDataEnrichFacade;
import com.verygoodbank.tes.service.TradeDataMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * Base {@code TradeDataEnrichFacade} implementation
 */
@Service
public class BaseTradeDataEnrichFacade implements TradeDataEnrichFacade {

    private static final String NEW_LINE = "\n";

    private static final String FAILED_TO_READ_FILE_MSG = "Failed to read file: ";
    private static final String FAILED_TO_WRITE_FILE_MSG = "Failed to write file";

    private final TradeDataMapper mapper;

    public BaseTradeDataEnrichFacade(final TradeDataMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * See {@link TradeDataEnrichFacade#enrich(MultipartFile)}. {@code TradeDataCsvReader} is used to read the original
     * {@code MultipartFile}. {@code TradeDataMapper} is used to map both headers and trade data entries
     */
    @Override
    public byte[] enrich(@NonNull final MultipartFile file) {
        //@formatter:off
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            //@formatter:on
            bufferedReader
                    .lines()
                    .map(mapper::map)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(line -> line + NEW_LINE)
                    .map(String::getBytes)
                    .forEach(bytes -> write(outputStream, bytes));

            return outputStream.toByteArray();
        } catch (final IOException e) {
            throw new CsvReadException(FAILED_TO_READ_FILE_MSG + file.getOriginalFilename(), e);
        }
    }

    private void write(final ByteArrayOutputStream outputStream, final byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (final IOException e) {
            throw new CsvWriteException(FAILED_TO_WRITE_FILE_MSG, e);
        }
    }
}
