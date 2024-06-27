package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.model.impl.NamedTradeData;
import com.verygoodbank.tes.service.TradeDataEnrichService;
import com.verygoodbank.tes.service.TradeDataMapper;
import com.verygoodbank.tes.validator.TradeDataDateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BaseTradeDataEnrichService implements TradeDataEnrichService {

    private static final String NON_VALID_DATE_MSG = "Non-valid format of the date: {}";

    private final Logger logger = LoggerFactory.getLogger(BaseTradeDataEnrichService.class);

    private final TradeDataDateValidator validator;

    private final TradeDataMapper mapper;

    public BaseTradeDataEnrichService(final TradeDataDateValidator validator, final TradeDataMapper mapper) {
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public List<NamedTradeData> enrich(final List<IdentifiedTradeData> identifiedTradeDataList) {
        return identifiedTradeDataList
                .stream()
                .filter(identifiedTradeData -> {
                    if (validator.isValidDate(identifiedTradeData)) {
                        return true;
                    } else {
                        logger.warn(NON_VALID_DATE_MSG, identifiedTradeData.getDate());

                        return false;
                    }
                })
                .map(mapper::map)
                .toList();
    }
}
