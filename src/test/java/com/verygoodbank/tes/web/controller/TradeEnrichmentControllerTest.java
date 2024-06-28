package com.verygoodbank.tes.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TradeEnrichmentControllerTest {

    private static final String CSV_FILE_PATH = "src/test/resources/trade.csv";
    private static final String FILE_REQUEST_PARAM = "file";
    private static final String CSV_FILE_NAME = "trade.csv";
    private static final String CONTENT_TYPE = "text/plain";
    private static final String REQUEST_PATH = "/api/v1/enrich";

    private static final String EXPECTED_RESPONSE = """
            date,product_name,currency,price
            20160101,Treasury Bills Domestic,EUR,10.0
            20160101,Corporate Bonds Domestic,EUR,20.1
            20160101,REPO Domestic,EUR,30.34
            20160101,Missing Product Name,EUR,35.34""";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void given_scvFile_when_enrich_then_returnNewCsvFile() throws Exception {
        final MockMultipartFile file = generateOriginalFile();

        mockMvc
                .perform(multipart(REQUEST_PATH).file(file))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(content().bytes(EXPECTED_RESPONSE.getBytes()));
    }

    private MockMultipartFile generateOriginalFile() throws Exception {
        final Path path = Paths.get(CSV_FILE_PATH);
        try (final InputStream inputStream = Files.newInputStream(path)) {
            return new MockMultipartFile(FILE_REQUEST_PARAM, CSV_FILE_NAME, CONTENT_TYPE, inputStream);
        }
    }
}
