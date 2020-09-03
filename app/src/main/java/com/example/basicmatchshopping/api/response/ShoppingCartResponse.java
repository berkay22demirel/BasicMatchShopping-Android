package com.example.basicmatchshopping.api.response;

import java.util.List;

public class ShoppingCartResponse {

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
