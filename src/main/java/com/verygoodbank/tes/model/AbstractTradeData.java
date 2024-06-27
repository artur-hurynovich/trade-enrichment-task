package com.verygoodbank.tes.model;

public abstract class AbstractTradeData {

    private final String date;

    private final String currency;

    private final String price;

    protected AbstractTradeData(final String date, final String currency, final String price) {
        this.date = date;
        this.currency = currency;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrice() {
        return price;
    }
}
