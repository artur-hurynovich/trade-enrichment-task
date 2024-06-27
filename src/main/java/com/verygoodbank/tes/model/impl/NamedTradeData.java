package com.verygoodbank.tes.model.impl;

import com.verygoodbank.tes.model.AbstractTradeData;

public class NamedTradeData extends AbstractTradeData {

    private final String productName;

    public NamedTradeData(final String date, final String currency, final String price, final String productName) {
        super(date, currency, price);
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}
