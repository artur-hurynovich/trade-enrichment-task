package com.verygoodbank.tes.model.impl;

import com.verygoodbank.tes.model.AbstractTradeData;

public class IdentifiedTradeData extends AbstractTradeData {

    private final String productId;

    public IdentifiedTradeData(final String date, final String currency, final String price, final String productId) {
        super(date, currency, price);
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
