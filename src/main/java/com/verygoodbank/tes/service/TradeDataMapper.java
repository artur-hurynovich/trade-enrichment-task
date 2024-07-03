package com.verygoodbank.tes.service;

import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * An interface used to map the original header or a single trade data entry
 */
public interface TradeDataMapper {

    /**
     * The method maps the original header or a single trade data entry
     *
     * @param originalLine original header or a single trade data entry, can't be null
     * @return result {@code Optional} of header or trade data entry or {@code Optional.empty} for non-valid
     * trade data entries
     */
    Optional<String> map(@NonNull String originalLine);
}
