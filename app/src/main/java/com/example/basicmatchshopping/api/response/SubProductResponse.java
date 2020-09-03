package com.example.basicmatchshopping.api.response;

import java.io.Serializable;

public class SubProductResponse implements Serializable {

    private int id;
    private String source;
    private double price;
    private String amountType;
    private double amount;
    private String sourceLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    @Override
    public String toString() {
        return "SubProductResponse{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", price=" + price +
                ", amountType='" + amountType + '\'' +
                ", amount=" + amount +
                ", sourceLink='" + sourceLink + '\'' +
                '}';
    }
}
