package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.service.TradeDataCsvWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseTradeDataCsvWriter implements TradeDataCsvWriter {

    private static final String SEPARATOR = ",";
    private static final String NEW_LINE = "\n";

    @Override
    public byte[] write(final List<String[]> lines) {
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
