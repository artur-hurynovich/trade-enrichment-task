package com.verygoodbank.tes.dao.impl;

import com.verygoodbank.tes.dao.ProductNameDao;
import com.verygoodbank.tes.exception.CsvReadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@code ProductNameDao} implementation based on storing product name to product id mappings in a {@code Map}
 * loaded from a CSV file. Once mappings are loaded from the CSV file they remain unchanged during
 * the whole instance lifecycle
 */
@Repository
public class CsvFileProductNameDao implements ProductNameDao {

    private static final String LINE_SEPARATOR = ",";

    private static final String FAILED_TO_READ_CSV_FILE_MSG = "Failed to read CSV file: ";

    private final Map<String, String> productNameByProductId;

    public CsvFileProductNameDao(@Value("${product.csv-file-name}") final String csvFileName) {
        this.productNameByProductId = buildProductNameByProductId(csvFileName);
    }

    private static Map<String, String> buildProductNameByProductId(final String csvFileName) {
        try (final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ClassPathResource(csvFileName).getInputStream()))) {
            return bufferedReader
                    .lines()
                    .skip(1L)
                    .map(line -> line.split(LINE_SEPARATOR))
                    .collect(Collectors.toUnmodifiableMap(lineArray -> lineArray[0], lineArray -> lineArray[1]));
        } catch (final IOException e) {
            throw new CsvReadException(FAILED_TO_READ_CSV_FILE_MSG + csvFileName, e);
        }
    }

    /**
     * See {@link ProductNameDao#getByProductId(String)}
     */
    @Override
    public Optional<String> getByProductId(@NonNull final String productId) {
        final String productName = productNameByProductId.get(productId);

        return Optional.ofNullable(productName);
    }
}
