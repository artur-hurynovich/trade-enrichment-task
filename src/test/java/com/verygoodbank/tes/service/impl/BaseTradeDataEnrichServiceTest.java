package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.model.impl.NamedTradeData;
import com.verygoodbank.tes.service.TradeDataMapper;
import com.verygoodbank.tes.validator.TradeDataDateValidator;
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

    private static final String DATE_1 = "20240628";
    private static final String CURRENCY_1 = "20240628";
    private static final String PRICE_1 = "20240628";
    private static final String PRODUCT_ID_1 = "20240628";

    private static final String DATE_2 = "20240628";
    private static final String CURRENCY_2 = "20240628";
    private static final String PRICE_2 = "20240628";
    private static final String PRODUCT_ID_2 = "20240628";

    private static final String PRODUCT_NAME = "Test Product Name";

    @Mock
    private TradeDataDateValidator validator;

    @Mock
    private TradeDataMapper mapper;

    @InjectMocks
    private BaseTradeDataEnrichService service;

    @Test
    void given_identifiedTradeDataList_when_enrich_then_returnNamedTradeDataList() {
        final IdentifiedTradeData identifiedTradeData1 = generateIdentifiedTradeData1();
        final IdentifiedTradeData identifiedTradeData2 = generateIdentifiedTradeData2();
        final NamedTradeData namedTradeData1 = generateNamedTradeData1();

        Mockito
                .when(validator.isValidDate(identifiedTradeData1))
                .thenReturn(true);
        Mockito
                .when(validator.isValidDate(identifiedTradeData2))
                .thenReturn(false);
        Mockito
                .when(mapper.map(identifiedTradeData1))
                .thenReturn(namedTradeData1);

        final List<NamedTradeData> actualNamedTradeDataList = service.enrich(
                List.of(identifiedTradeData1, identifiedTradeData2));

        Assertions.assertEquals(1, actualNamedTradeDataList.size());
        Assertions.assertEquals(namedTradeData1, actualNamedTradeDataList.get(0));
    }

    private IdentifiedTradeData generateIdentifiedTradeData1() {
        return new IdentifiedTradeData(DATE_1, CURRENCY_1, PRICE_1, PRODUCT_ID_1);
    }

    private IdentifiedTradeData generateIdentifiedTradeData2() {
        return new IdentifiedTradeData(DATE_2, CURRENCY_2, PRICE_2, PRODUCT_ID_2);
    }

    private NamedTradeData generateNamedTradeData1() {
        return new NamedTradeData(DATE_1, CURRENCY_1, PRICE_1, PRODUCT_NAME);
    }
}
