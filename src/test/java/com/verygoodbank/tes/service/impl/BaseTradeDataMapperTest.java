package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.model.impl.NamedTradeData;
import com.verygoodbank.tes.service.TradeDataMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BaseTradeDataMapperTest {

    private static final String DATE = "20240628";
    private static final String CURRENCY = "EUR";
    private static final String PRICE = "13.18";
    private static final String PRODUCT_ID = "15";
    private static final String PRODUCT_NAME = "Test Product Name";

    private final TradeDataMapper mapper = new BaseTradeDataMapper();

    @Test
    void given_identifiedTradeData_when_map_then_returnNamedTradeData() {
        final IdentifiedTradeData identifiedTradeData = new IdentifiedTradeData(DATE, CURRENCY, PRICE, PRODUCT_ID);
        final NamedTradeData expectedNamedTradeData = new NamedTradeData(DATE, CURRENCY, PRICE, PRODUCT_NAME);

        final NamedTradeData actualNamedTradeData = mapper.map(identifiedTradeData, PRODUCT_NAME);

        Assertions
                .assertThat(actualNamedTradeData)
                .usingRecursiveComparison()
                .isEqualTo(expectedNamedTradeData);
    }
}
