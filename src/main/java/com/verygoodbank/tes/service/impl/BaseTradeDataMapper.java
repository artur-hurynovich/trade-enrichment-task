package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.dao.ProductNameDao;
import com.verygoodbank.tes.service.TradeDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class BaseTradeDataMapper implements TradeDataMapper {

    private static final String PRODUCT_NAME_NOT_FOUND_MSG = "Product name for product with ID={} not found";

    private static final String DEFAULT_PRODUCT_NAME = "Missing Product Name";

    private final Logger logger = LoggerFactory.getLogger(BaseTradeDataMapper.class);

    private final ProductNameDao dao;

    public BaseTradeDataMapper(final ProductNameDao dao) {
        this.dao = dao;
    }

    @Override
    public String[] map(final String[] originalLine) {
        final String[] enrichedLine = Arrays.copyOf(originalLine, originalLine.length);
        final String productId = enrichedLine[1];
        final String productName = resolveProductName(productId);
        enrichedLine[1] = productName;

        return enrichedLine;
    }

    private String resolveProductName(final String productId) {
        final Optional<String> productNameOptional = dao.getByProductId(productId);
        if (productNameOptional.isPresent()) {
            return productNameOptional.get();
        } else {
            logger.warn(PRODUCT_NAME_NOT_FOUND_MSG, productId);

            return DEFAULT_PRODUCT_NAME;
        }
    }
}
