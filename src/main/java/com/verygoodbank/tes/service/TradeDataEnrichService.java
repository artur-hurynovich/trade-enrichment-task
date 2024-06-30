package com.verygoodbank.tes.service;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * An interface used to map the original trade data entry {@code List}
 */
public interface TradeDataEnrichService {

    /**
     * The method maps the original trade data entry {@code List}
     *
     * @param originalLines an original trade data entry {@code List}
     * @return result trade data entry {@code List}
     */
    List<String[]> enrich(@NonNull List<String[]> originalLines);
}
