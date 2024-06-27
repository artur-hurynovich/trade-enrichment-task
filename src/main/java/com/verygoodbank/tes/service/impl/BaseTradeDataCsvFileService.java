package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.exception.TreeDataCsvFileParseException;
import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.model.impl.NamedTradeData;
import com.verygoodbank.tes.service.TradeDataCsvFileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class BaseTradeDataCsvFileService implements TradeDataCsvFileService {

    private static final String LINE_SEPARATOR = ",";

    private static final String FAILED_TO_PARSE_FILE_MSG = "Failed to parser file: ";

    private static final String HEADER = "date,product_name,currency,price";
    private static final String NEW_LINE = "\n";

    @Override
    public List<IdentifiedTradeData> parse(final MultipartFile file) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return reader
                    .lines()
                    .skip(1L)
                    .map(line -> line.split(LINE_SEPARATOR))
                    .map(this::buildIdentifiedTradeData)
                    .toList();
        } catch (final IOException e) {
            throw new TreeDataCsvFileParseException(FAILED_TO_PARSE_FILE_MSG + file.getOriginalFilename(), e);
        }
    }

    private IdentifiedTradeData buildIdentifiedTradeData(final String[] lineArray) {
        final String date = lineArray[0];
        final String productId = lineArray[1];
        final String currency = lineArray[2];
        final String price = lineArray[3];

        return new IdentifiedTradeData(date, currency, price, productId);
    }

    @Override
    public Resource write(final List<NamedTradeData> namedTradeDataList) {
        final String resultString = buildString(namedTradeDataList);

        return new ByteArrayResource(resultString.getBytes());
    }

    private String buildString(final List<NamedTradeData> namedTradeDataList) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(HEADER)
                .append(NEW_LINE);
        namedTradeDataList.forEach(namedTradeData -> stringBuilder
                .append(namedTradeData.getDate())
                .append(LINE_SEPARATOR)
                .append(namedTradeData.getProductName())
                .append(LINE_SEPARATOR)
                .append(namedTradeData.getCurrency())
                .append(LINE_SEPARATOR)
                .append(namedTradeData.getPrice())
                .append(NEW_LINE));

        return stringBuilder
                .toString()
                .trim();
    }
}
