package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.service.TradeDataCsvReader;
import com.verygoodbank.tes.service.TradeDataCsvWriter;
import com.verygoodbank.tes.service.TradeDataEnrichService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BaseTradeDataEnrichFacadeTest {

    private static final List<String[]> ORIGINAL_TRADE_DATA = List.of(
            new String[]{"date", "product_id", "currency", "price"},
            new String[]{"20160101", "1", "EUR", "10.0"});

    private static final List<String[]> ENRICHED_TRADE_DATA = List.of(
            new String[]{"date", "product_name", "currency", "price"},
            new String[]{"20160101", "Test Product Name", "EUR", "10.0"});

    private static final byte[] ENRICHED_TRADE_DATA_BYTES = """
            date,product_name,currency,price
            20160101,Test Product Name,EUR,10.0
            """.getBytes();

    @Mock
    private MultipartFile file;

    @Mock
    private TradeDataCsvReader reader;

    @Mock
    private TradeDataEnrichService enrichService;

    @Mock
    private TradeDataCsvWriter writer;

    @InjectMocks
    private BaseTradeDataEnrichFacade facade;

    @Test
    void given_file_when_enrich_returnByteArray() {
        Mockito
                .when(reader.read(file))
                .thenReturn(ORIGINAL_TRADE_DATA);
        Mockito
                .when(enrichService.enrich(ORIGINAL_TRADE_DATA))
                .thenReturn(ENRICHED_TRADE_DATA);
        Mockito
                .when(writer.write(ENRICHED_TRADE_DATA))
                .thenReturn(ENRICHED_TRADE_DATA_BYTES);

        final byte[] actualEnrichedTradeDataBytes = facade.enrich(file);

        Assertions.assertArrayEquals(ENRICHED_TRADE_DATA_BYTES, actualEnrichedTradeDataBytes);
    }
}
