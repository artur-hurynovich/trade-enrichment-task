package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.service.TradeDataMapper;
import com.verygoodbank.tes.validator.DateFormatValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BaseTradeDataEnrichServiceTest {

    private static final String[] ORIGINAL_HEADER = {"date", "product_id", "currency", "price"};
    private static final String[] RESULT_HEADER = {"date", "product_name", "currency", "price"};

    private static final String DATE_1 = "20240628";
    private static final String PRODUCT_ID_1 = "16";
    private static final String CURRENCY_1 = "EUR";
    private static final String PRICE_1 = "10.10";

    private static final String DATE_2 = "2024-06-28";
    private static final String PRODUCT_ID_2 = "36";
    private static final String CURRENCY_2 = "USD";
    private static final String PRICE_2 = "20.20";

    private static final String PRODUCT_NAME = "Test Product Name";

    @Mock
    private DateFormatValidator validator;

    @Mock
    private TradeDataMapper mapper;

    @InjectMocks
    private BaseTradeDataEnrichService service;

    @Test
    void given_identifiedTradeDataList_when_enrich_then_returnNamedTradeDataList() {
        final String[] originalLine1 = {DATE_1, PRODUCT_ID_1, CURRENCY_1, PRICE_1};
        final String[] originalLine2 = {DATE_2, PRODUCT_ID_2, CURRENCY_2, PRICE_2};
        final String[] enrichedLine1 = {DATE_1, PRODUCT_NAME, CURRENCY_1, PRICE_1};

        Mockito
                .when(validator.isValidFormat(DATE_1))
                .thenReturn(true);
        Mockito
                .when(validator.isValidFormat(DATE_2))
                .thenReturn(false);
        Mockito
                .when(mapper.map(originalLine1))
                .thenReturn(enrichedLine1);

        final List<String[]> actualEnrichedLines = service.enrich(
                List.of(ORIGINAL_HEADER, originalLine1, originalLine2));

        Assertions.assertEquals(2, actualEnrichedLines.size());
        Assertions.assertArrayEquals(RESULT_HEADER, actualEnrichedLines.get(0));
        Assertions.assertArrayEquals(enrichedLine1, actualEnrichedLines.get(1));
    }
}
