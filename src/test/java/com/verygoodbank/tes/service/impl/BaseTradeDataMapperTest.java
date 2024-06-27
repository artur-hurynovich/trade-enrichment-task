package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.dao.ProductNameDao;
import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.model.impl.NamedTradeData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BaseTradeDataMapperTest {

    private static final String DATE = "20240628";
    private static final String CURRENCY = "EUR";
    private static final String PRICE = "13.18";
    private static final String PRODUCT_ID = "15";
    private static final String ACTUAL_PRODUCT_NAME = "Test Product Name";
    private static final String DEFAULT_PRODUCT_NAME = "Missing Product Name";

    @Mock
    private ProductNameDao dao;

    @InjectMocks
    private BaseTradeDataMapper mapper;

    @Test
    void given_identifiedTradeData_when_map_then_returnNamedTradeDataWithActualName() {
        Mockito
                .when(dao.getByProductId(PRODUCT_ID))
                .thenReturn(Optional.of(ACTUAL_PRODUCT_NAME));

        final IdentifiedTradeData identifiedTradeData = new IdentifiedTradeData(DATE, CURRENCY, PRICE, PRODUCT_ID);
        final NamedTradeData expectedNamedTradeData = new NamedTradeData(DATE, CURRENCY, PRICE, ACTUAL_PRODUCT_NAME);

        final NamedTradeData actualNamedTradeData = mapper.map(identifiedTradeData);

        Assertions
                .assertThat(actualNamedTradeData)
                .usingRecursiveComparison()
                .isEqualTo(expectedNamedTradeData);
    }

    @Test
    void given_identifiedTradeData_when_map_then_returnNamedTradeDataWithDefaultName() {
        Mockito
                .when(dao.getByProductId(PRODUCT_ID))
                .thenReturn(Optional.empty());

        final IdentifiedTradeData identifiedTradeData = new IdentifiedTradeData(DATE, CURRENCY, PRICE, PRODUCT_ID);
        final NamedTradeData expectedNamedTradeData = new NamedTradeData(DATE, CURRENCY, PRICE, DEFAULT_PRODUCT_NAME);

        final NamedTradeData actualNamedTradeData = mapper.map(identifiedTradeData);

        Assertions
                .assertThat(actualNamedTradeData)
                .usingRecursiveComparison()
                .isEqualTo(expectedNamedTradeData);
    }
}
