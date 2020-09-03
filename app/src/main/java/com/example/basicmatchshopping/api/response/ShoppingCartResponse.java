package com.example.basicmatchshopping.api.response;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartResponse implements Serializable {

    private int id;
    private UserResponse userDTO;
    private List<ShoppingCartItemResponse> shoppingCartItemDTOs;
    private double totalAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserResponse getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserResponse userDTO) {
        this.userDTO = userDTO;
    }

    public List<ShoppingCartItemResponse> getShoppingCartItemDTOs() {
        return shoppingCartItemDTOs;
    }

    public void setShoppingCartItemDTOs(List<ShoppingCartItemResponse> shoppingCartItemDTOs) {
        this.shoppingCartItemDTOs = shoppingCartItemDTOs;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFixTotalAmount() {
        String totalAmount = "" + this.totalAmount;
        if (totalAmount.length() > 4) {
            totalAmount = totalAmount.substring(0, 5);
        }
        return totalAmount;
    }

    @Override
    public String toString() {
        return "ShoppingCartResponse{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", shoppingCartItemDTOs=" + shoppingCartItemDTOs +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
