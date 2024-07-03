package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.service.TradeDataMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BaseTradeDataEnrichFacadeTest {

    private static final String CSV_FILE_PATH = "src/test/resources/trade.csv";
    private static final String FILE_REQUEST_PARAM = "file";
    private static final String CSV_FILE_NAME = "trade.csv";
    private static final String CONTENT_TYPE = "text/plain";

    private static final String ORIGINAL_HEADER = "date,product_id,currency,price";
    private static final String ORIGINAL_TRADE_DATA_1 = "20160101,1,EUR,10.0";
    private static final String ORIGINAL_TRADE_DATA_2 = "20160101,2,EUR,20.1";
    private static final String ORIGINAL_TRADE_DATA_3 = "20160101,3,EUR,30.34";
    private static final String ORIGINAL_TRADE_DATA_4 = "20160101,11,EUR,35.34";

    private static final String RESULT_HEADER = "date,product_name,currency,price";
    private static final String RESULT_TRADE_DATA_1 = "20160101,Test Product Name 1,EUR,10.0";
    private static final String RESULT_TRADE_DATA_2 = "20160101,Test Product Name 2,EUR,20.1";
    private static final String RESULT_TRADE_DATA_3 = "20160101,Test Product Name 3,EUR,30.34";
    private static final String RESULT_TRADE_DATA_4 = "20160101,Missing Product Name,EUR,35.34";

    private static final byte[] ENRICHED_TRADE_DATA_BYTES = """
            date,product_name,currency,price
            20160101,Test Product Name 1,EUR,10.0
            20160101,Test Product Name 2,EUR,20.1
            20160101,Test Product Name 3,EUR,30.34
            20160101,Missing Product Name,EUR,35.34
            """.getBytes();

    @Mock
    private TradeDataMapper mapper;

    @InjectMocks
    private BaseTradeDataEnrichFacade facade;

    @Test
    void given_file_when_enrich_returnByteArray() throws Exception {
        Mockito
                .when(mapper.map(ORIGINAL_HEADER))
                .thenReturn(Optional.of(RESULT_HEADER));
        Mockito
                .when(mapper.map(ORIGINAL_TRADE_DATA_1))
                .thenReturn(Optional.of(RESULT_TRADE_DATA_1));
        Mockito
                .when(mapper.map(ORIGINAL_TRADE_DATA_2))
                .thenReturn(Optional.of(RESULT_TRADE_DATA_2));
        Mockito
                .when(mapper.map(ORIGINAL_TRADE_DATA_3))
                .thenReturn(Optional.of(RESULT_TRADE_DATA_3));
        Mockito
                .when(mapper.map(ORIGINAL_TRADE_DATA_4))
                .thenReturn(Optional.of(RESULT_TRADE_DATA_4));

        final MockMultipartFile file = generateOriginalFile();
        final byte[] actualEnrichedTradeDataBytes = facade.enrich(file);

        Assertions.assertArrayEquals(ENRICHED_TRADE_DATA_BYTES, actualEnrichedTradeDataBytes);
    }

    private MockMultipartFile generateOriginalFile() throws Exception {
        final Path path = Paths.get(CSV_FILE_PATH);
        try (final InputStream inputStream = Files.newInputStream(path)) {
            return new MockMultipartFile(FILE_REQUEST_PARAM, CSV_FILE_NAME, CONTENT_TYPE, inputStream);
        }
    }
}
