package com.verygoodbank.tes.dao;

import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * An interface for product name retrieval
 */
public interface ProductNameDao {

    /**
     * Method returns an {@code Optional} containing product name mapped to the specified product id
     *
     * @param productId product id to retrieve product name by, can't be null
     * @return {@code Optional} containing product name mapped to the specified product id or an
     * empty {@code Optional} if there's no mapping
     */
    Optional<String> getByProductId(@NonNull String productId);
}
