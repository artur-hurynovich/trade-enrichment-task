package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.service.TradeDataEnrichService;
import com.verygoodbank.tes.service.TradeDataMapper;
import com.verygoodbank.tes.validator.DateFormatValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base {@code TradeDataEnrichService} implementation
 */
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

    /**
     * See {@link TradeDataEnrichService#enrich(List)}. Additionally, the header is expected as the first element
     * of the {@code List}. Header value "product_id" is replaced with "product_name". Entries with dates which don't
     * match the pattern "yyyyMMdd" are ignored. To validate the pattern {@code DateFormatValidator} is used.
     * To map every single entry {@code TradeDataMapper} is used
     */
    @Override
    public List<String[]> enrich(@NonNull final List<String[]> originalLines) {
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

        return Collections.unmodifiableList(resultLines);
    }
}
