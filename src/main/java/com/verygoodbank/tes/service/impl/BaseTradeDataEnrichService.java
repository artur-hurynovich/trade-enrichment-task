package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.service.TradeDataEnrichService;
import com.verygoodbank.tes.service.TradeDataMapper;
import com.verygoodbank.tes.validator.DateFormatValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseTradeDataEnrichService implements TradeDataEnrichService {

    private static final String[] HEADER = {"date", "product_name", "currency", "price"};

    private static final String NON_VALID_DATE_MSG = "Non-valid format of the date: {}";

    private final Logger logger = LoggerFactory.getLogger(BaseTradeDataEnrichService.class);

    private final DateFormatValidator validator;

    private final TradeDataMapper mapper;

    public BaseTradeDataEnrichService(final DateFormatValidator validator, final TradeDataMapper mapper) {
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public List<String[]> enrich(final List<String[]> originalLines) {
        final List<String[]> resultLines = new ArrayList<>();
        resultLines.add(HEADER);

        final List<String[]> enrichedLines = originalLines
                .stream()
                .skip(1L)
                .filter(originalLine -> {
                    final String date = originalLine[0];
                    if (validator.isValidFormat(date)) {
                        return true;
                    } else {
                        logger.warn(NON_VALID_DATE_MSG, date);

                        return false;
                    }
                })
                .map(mapper::map)
                .toList();
        resultLines.addAll(enrichedLines);

        return resultLines;
    }
}
