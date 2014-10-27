package com.backend.module.QuotesAPI;

/**
 * The object model for the data we are sending through endpoints
 */
public class QuoteBean {

    private String ticker;
    private String price;
    private String change;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}