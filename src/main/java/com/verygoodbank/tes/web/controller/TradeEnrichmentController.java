package com.verygoodbank.tes.web.controller;


import com.verygoodbank.tes.service.TradeDataCsvReader;
import com.verygoodbank.tes.service.TradeDataCsvWriter;
import com.verygoodbank.tes.service.TradeDataEnrichService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TradeEnrichmentController {

    private final TradeDataCsvReader reader;

    private final TradeDataEnrichService enrichService;

    private final TradeDataCsvWriter writer;

    public TradeEnrichmentController(final TradeDataCsvReader reader, final TradeDataEnrichService enrichService,
                                     final TradeDataCsvWriter writer) {
        this.reader = reader;
        this.enrichService = enrichService;
        this.writer = writer;
    }

    @PostMapping(value = "/enrich", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> enrich(@RequestParam("file") final MultipartFile file) {
        final List<String[]> originalLines = reader.read(file);

        final List<String[]> enrichedLines = enrichService.enrich(originalLines);

        final byte[] result = writer.write(enrichedLines);

        return ResponseEntity.ok(result);
    }
}
