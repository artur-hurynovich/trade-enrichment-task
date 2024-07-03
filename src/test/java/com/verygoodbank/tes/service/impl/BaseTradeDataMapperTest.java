package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.dao.ProductNameDao;
import com.verygoodbank.tes.validator.DateFormatValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BaseTradeDataMapperTest {

    private static final String ORIGINAL_HEADER_LINE = "date,product_id,currency,price";
    private static final String RESULT_HEADER_LINE = "date,product_name,currency,price";

    private static final String ORIGINAL_TRADE_DATA_LINE = "20240628,15,EUR,13.18";
    private static final String ORIGINAL_TRADE_DATA_DATE = "20240628";
    private static final String RESULT_TRADE_DATA_LINE_WITH_PRODUCT_NAME = "20240628,Test Product Name,EUR,13.18";
    private static final String RESULT_TRADE_DATA_LINE_WITHOUT_PRODUCT_NAME = "20240628,Missing Product Name,EUR,13.18";

    private static final String PRODUCT_ID = "15";
    private static final String ACTUAL_PRODUCT_NAME = "Test Product Name";

    @Mock
    private DateFormatValidator validator;

    @Mock
    private ProductNameDao dao;

    @InjectMocks
    private BaseTradeDataMapper mapper;

    @Test
    void given_headerLine_when_map_then_returnEnrichedHeaderLine() {
        final Optional<String> actualResultHeaderLineOptional = mapper.map(ORIGINAL_HEADER_LINE);

        Assertions.assertTrue(actualResultHeaderLineOptional.isPresent());
        Assertions.assertEquals(RESULT_HEADER_LINE, actualResultHeaderLineOptional.get());
    }

    @Test
    void given_identifiedTradeData_when_map_then_returnNamedTradeDataWithActualName() {
        Mockito
                .when(validator.isValidFormat(ORIGINAL_TRADE_DATA_DATE))
                .thenReturn(true);
        Mockito
                .when(dao.getByProductId(PRODUCT_ID))
                .thenReturn(Optional.of(ACTUAL_PRODUCT_NAME));

        final Optional<String> actualResultTradeDataLineOptional = mapper.map(ORIGINAL_TRADE_DATA_LINE);

        Assertions.assertTrue(actualResultTradeDataLineOptional.isPresent());
        Assertions.assertEquals(RESULT_TRADE_DATA_LINE_WITH_PRODUCT_NAME, actualResultTradeDataLineOptional.get());
    }

    @Test
    void given_identifiedTradeData_when_map_then_returnNamedTradeDataWithDefaultName() {
        Mockito
                .when(validator.isValidFormat(ORIGINAL_TRADE_DATA_DATE))
                .thenReturn(true);
        Mockito
                .when(dao.getByProductId(PRODUCT_ID))
                .thenReturn(Optional.empty());

        final Optional<String> actualResultTradeDataLineOptional = mapper.map(ORIGINAL_TRADE_DATA_LINE);

        Assertions.assertTrue(actualResultTradeDataLineOptional.isPresent());
        Assertions.assertEquals(RESULT_TRADE_DATA_LINE_WITHOUT_PRODUCT_NAME, actualResultTradeDataLineOptional.get());
    }

    @Test
    void given_identifiedTradeData_when_map_then_returnEmpty() {
        Mockito
                .when(validator.isValidFormat(ORIGINAL_TRADE_DATA_DATE))
                .thenReturn(false);

        final Optional<String> actualResultTradeDataLineOptional = mapper.map(ORIGINAL_TRADE_DATA_LINE);

        Assertions.assertTrue(actualResultTradeDataLineOptional.isEmpty());
    }
}
