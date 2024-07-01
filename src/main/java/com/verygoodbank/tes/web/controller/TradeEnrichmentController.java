package com.verygoodbank.tes.web.controller;


import com.verygoodbank.tes.service.TradeDataEnrichFacade;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1")
public class TradeEnrichmentController {

    private final TradeDataEnrichFacade facade;

    public TradeEnrichmentController(final TradeDataEnrichFacade facade) {
        this.facade = facade;
    }

    @PostMapping(value = "/enrich", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Async
    public CompletableFuture<ResponseEntity<byte[]>> enrich(@RequestParam("file") final MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            final byte[] result = facade.enrich(file);

            return ResponseEntity.ok(result);
        });
    }
}
