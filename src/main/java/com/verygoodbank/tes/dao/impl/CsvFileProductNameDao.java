package com.verygoodbank.tes.dao.impl;

import com.verygoodbank.tes.dao.ProductNameDao;
import com.verygoodbank.tes.exception.CsvFileReadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CsvFileProductNameDao implements ProductNameDao {

    private static final String LINE_SEPARATOR = ",";

    private static final String FAILED_TO_READ_CSV_FILE_EXCEPTION_MSG = "Failed to read CSV file with path: ";

    private final Map<String, String> productNameByProductId;

    public CsvFileProductNameDao(@Value("${product.csv-file-path}") final String csvFilePath) {
        final Path path = Paths.get(csvFilePath);

        this.productNameByProductId = buildProductNameByProductId(path);
    }

    private static Map<String, String> buildProductNameByProductId(final Path path) {
        try {
            return Files
                    .readAllLines(path)
                    .stream()
                    .skip(1L)
                    .map(line -> line.split(LINE_SEPARATOR))
                    .collect(Collectors.toUnmodifiableMap(lineArray -> lineArray[0], lineArray -> lineArray[1]));
        } catch (final IOException e) {
            throw new CsvFileReadException(FAILED_TO_READ_CSV_FILE_EXCEPTION_MSG + path, e);
        }
    }

    @Override
    public Optional<String> getByProductId(final String productId) {
        final String productName = productNameByProductId.get(productId);

        return Optional.ofNullable(productName);
    }
}
