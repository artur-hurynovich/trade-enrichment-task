package com.verygoodbank.tes.service;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * An interface used to encapsulate all trade data enrichment logic including parsing the file,
 * enrichment itself and converting enriched trade data to a byte array
 */
public interface TradeDataEnrichFacade {

    /**
     * The method encapsulates all trade data enrichment logic including parsing the file,
     * enrichment itself and converting enriched trade data to a byte array
     *
     * @param file original {@code MultipartFile} to read the data from, can't be null
     * @return a byte array representation of enriched trade data
     */
    byte[] enrich(@NonNull MultipartFile file);
}
