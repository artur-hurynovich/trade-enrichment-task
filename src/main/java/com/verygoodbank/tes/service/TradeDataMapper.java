package com.verygoodbank.tes.service;

import org.springframework.lang.NonNull;

/**
 * An interface used to map the original single trade data entry
 */
public interface TradeDataMapper {

    /**
     * The method maps the original single trade data entry
     *
     * @param originalLine original trade data entry, can't be null
     * @return result trade data entry
     */
    String[] map(@NonNull String[] originalLine);
}
