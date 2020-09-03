package com.example.basicmatchshopping.api.response;

import java.io.Serializable;

public class ShoppingCartItemResponse implements Serializable {

    private int id;
    private SubProductResponse subProductDTO;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SubProductResponse getSubProductDTO() {
        return subProductDTO;
    }

    public void setSubProductDTO(SubProductResponse subProductDTO) {
        this.subProductDTO = subProductDTO;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShoppingCartItemResponse{" +
                "id=" + id +
                ", subProductDTO=" + subProductDTO +
                ", quantity=" + quantity +
                '}';
    }
}
