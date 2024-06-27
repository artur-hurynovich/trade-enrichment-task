package com.verygoodbank.tes.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class CsvFileProductNameDaoTest {

    private static final String VALID_PRODUCT_ID = "5";
    private static final String PRODUCT_NAME = "OTC Index Option";

    private static final String NON_VALID_PRODUCT_ID = "13";

    private final CsvFileProductNameDao dao = new CsvFileProductNameDao("src/test/resources/product.csv");

    @Test
    void given_validProductId_when_getByProductId_then_returnNonEmptyOptional() {
        final Optional<String> productNameOptional = dao.getByProductId(VALID_PRODUCT_ID);

        Assertions.assertTrue(productNameOptional.isPresent());
        Assertions.assertEquals(PRODUCT_NAME, productNameOptional.get());
    }

    @Test
    void given_nonValidProductId_when_getByProductId_then_returnEmptyOptional() {
        final Optional<String> productNameOptional = dao.getByProductId(NON_VALID_PRODUCT_ID);

        Assertions.assertTrue(productNameOptional.isEmpty());
    }
}
