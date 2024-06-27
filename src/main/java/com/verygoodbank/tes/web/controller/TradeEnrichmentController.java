package com.verygoodbank.tes.web.controller;


import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.model.impl.NamedTradeData;
import com.verygoodbank.tes.service.TradeDataCsvFileService;
import com.verygoodbank.tes.service.TradeDataEnrichService;
import org.springframework.core.io.Resource;
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

    private final TradeDataCsvFileService csvFileService;

    private final TradeDataEnrichService enrichService;

    public TradeEnrichmentController(final TradeDataCsvFileService csvFileService,
                                     final TradeDataEnrichService enrichService) {
        this.csvFileService = csvFileService;
        this.enrichService = enrichService;
    }

    @PostMapping("/enrich")
    public ResponseEntity<Resource> enrich(@RequestParam("file") final MultipartFile file) {
        final List<IdentifiedTradeData> identifiedTradeDataList = csvFileService.parse(file);

        final List<NamedTradeData> namedTradeDataList = enrichService.enrich(identifiedTradeDataList);

        final Resource resource = csvFileService.write(namedTradeDataList);

        return ResponseEntity.ok(resource);
    }
}
