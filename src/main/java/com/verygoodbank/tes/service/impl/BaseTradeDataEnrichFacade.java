package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.service.TradeDataCsvReader;
import com.verygoodbank.tes.service.TradeDataCsvWriter;
import com.verygoodbank.tes.service.TradeDataEnrichFacade;
import com.verygoodbank.tes.service.TradeDataEnrichService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Base {@code TradeDataEnrichFacade} implementation
 */
@Service
public class BaseTradeDataEnrichFacade implements TradeDataEnrichFacade {

    private final TradeDataCsvReader reader;

    private final TradeDataEnrichService enrichService;

    private final TradeDataCsvWriter writer;

    public BaseTradeDataEnrichFacade(final TradeDataCsvReader reader, final TradeDataEnrichService enrichService,
                                     final TradeDataCsvWriter writer) {
        this.reader = reader;
        this.enrichService = enrichService;
        this.writer = writer;
    }

    /**
     * See {@link TradeDataEnrichFacade#enrich(MultipartFile)}. {@code TradeDataCsvReader} is used to read the original
     * {@code MultipartFile}. {@code TradeDataEnrichService} is used to perform enrichment. {@code TradeDataCsvWriter}
     * is used to convert enriched trade data to a byte array
     */
    @Override
    public byte[] enrich(@NonNull final MultipartFile file) {
        final List<String[]> originalLines = reader.read(file);

        final List<String[]> enrichedLines = enrichService.enrich(originalLines);

        return writer.write(enrichedLines);
    }
}
