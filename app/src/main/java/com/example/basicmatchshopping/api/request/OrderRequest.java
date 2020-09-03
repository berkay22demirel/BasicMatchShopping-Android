package com.example.basicmatchshopping.api.request;

public class OrderRequest {

    private UserRequest userDTO;
    private ShoppingCartRequest shoppingCartDTO;
    private String orderDate;

    public UserRequest getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserRequest userDTO) {
        this.userDTO = userDTO;
    }

    public ShoppingCartRequest getShoppingCartDTO() {
        return shoppingCartDTO;
    }

    public void setShoppingCartDTO(ShoppingCartRequest shoppingCartDTO) {
        this.shoppingCartDTO = shoppingCartDTO;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
