package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.dao.ProductNameDao;
import com.verygoodbank.tes.service.TradeDataMapper;
import com.verygoodbank.tes.validator.DateFormatValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

/**
 * Base {@code TradeDataMapper} implementation
 */
@Service
public class BaseTradeDataMapper implements TradeDataMapper {

    private static final String ORIGINAL_HEADER = "date,product_id,currency,price";
    private static final String RESULT_HEADER = "date,product_name,currency,price";

    private static final String SEPARATOR = ",";

    private static final String NON_VALID_DATE_MSG = "Non-valid format of the date: {}";

    private static final String PRODUCT_NAME_NOT_FOUND_MSG = "Product name for product with ID={} not found";

    private static final String DEFAULT_PRODUCT_NAME = "Missing Product Name";

    private final Logger logger = LoggerFactory.getLogger(BaseTradeDataMapper.class);

    private final DateFormatValidator validator;

    private final ProductNameDao dao;

    public BaseTradeDataMapper(final DateFormatValidator validator, final ProductNameDao dao) {
        this.validator = validator;
        this.dao = dao;
    }

    /**
     * See {@link TradeDataMapper#map(String)}. When mapping headers product_id is replaced with product_name.
     * When mapping trade data entries product id value is replaced with product name value using {@code ProductNameDao}.
     * For missing product id default name "Missing Product Name" is used. Trade data with a non-valid date format
     * is mapped to {@code Optional.empty}. Trade data date format is validated with {@code DateFormatValidator}
     */
    @Override
    public Optional<String> map(@NonNull final String originalLine) {
        if (originalLine.equals(ORIGINAL_HEADER)) {
            return Optional.of(RESULT_HEADER);
        } else {
            final String date = originalLine.split(SEPARATOR)[0];
            if (validator.isValidFormat(date)) {
                return Optional.of(mapTradeData(originalLine));
            } else {
                logger.warn(NON_VALID_DATE_MSG, date);

                return Optional.empty();
            }
        }
    }

    private String mapTradeData(final String tradeDataLine) {
        final String[] splitTradeDataLine = tradeDataLine.split(SEPARATOR);
        final String[] splitEnrichedTradeDataLine = Arrays.copyOf(splitTradeDataLine, splitTradeDataLine.length);
        final String productId = splitTradeDataLine[1];
        final String productName = resolveProductName(productId);
        splitEnrichedTradeDataLine[1] = productName;

        return String.join(SEPARATOR, splitEnrichedTradeDataLine);
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
