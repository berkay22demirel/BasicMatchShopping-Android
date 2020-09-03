package com.example.basicmatchshopping.api.response;

import java.io.Serializable;

public class OrderResponse implements Serializable {

    private int id;
    private UserResponse userDTO;
    private ShoppingCartResponse shoppingCartDTO;
    private String orderDate;

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

    public ShoppingCartResponse getShoppingCartDTO() {
        return shoppingCartDTO;
    }

    public void setShoppingCartDTO(ShoppingCartResponse shoppingCartDTO) {
        this.shoppingCartDTO = shoppingCartDTO;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", shoppingCartDTO=" + shoppingCartDTO +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
