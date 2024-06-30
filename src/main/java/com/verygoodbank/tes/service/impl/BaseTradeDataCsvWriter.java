package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.service.TradeDataCsvWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Base {@code TradeDataCsvWriter} implementation
 */
@Service
public class BaseTradeDataCsvWriter implements TradeDataCsvWriter {

    private static final String SEPARATOR = ",";
    private static final String NEW_LINE = "\n";

    /**
     * See {@link TradeDataCsvWriter#write(List)}
     */
    @Override
    public byte[] write(@NonNull final List<String[]> lines) {
        final StringBuilder stringBuilder = new StringBuilder();
        lines.forEach(line -> stringBuilder
                .append(String.join(SEPARATOR, line))
                .append(NEW_LINE));

        return stringBuilder
                .toString()
                .trim()
                .getBytes();
    }
}
