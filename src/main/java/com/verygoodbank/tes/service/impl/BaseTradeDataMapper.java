package com.verygoodbank.tes.service.impl;

import com.verygoodbank.tes.model.impl.IdentifiedTradeData;
import com.verygoodbank.tes.model.impl.NamedTradeData;
import com.verygoodbank.tes.service.TradeDataMapper;

public class BaseTradeDataMapper implements TradeDataMapper {

    @Override
    public NamedTradeData map(final IdentifiedTradeData identifiedTradeData, final String productName) {
        final String date = identifiedTradeData.getDate();
        final String currency = identifiedTradeData.getCurrency();
        final String price = identifiedTradeData.getPrice();

        return new NamedTradeData(date, currency, price, productName);
    }
}
