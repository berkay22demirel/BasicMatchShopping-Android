package com.example.basicmatchshopping.api.request;

public class ShoppingCartItemRequest {

    private int id;
    private ShoppingCartRequest shoppingCartDTO;
    private SubProductRequest subProductDTO;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ShoppingCartRequest getShoppingCartDTO() {
        return shoppingCartDTO;
    }

    public void setShoppingCartDTO(ShoppingCartRequest shoppingCartDTO) {
        this.shoppingCartDTO = shoppingCartDTO;
    }

    public SubProductRequest getSubProductDTO() {
        return subProductDTO;
    }

    public void setSubProductDTO(SubProductRequest subProductDTO) {
        this.subProductDTO = subProductDTO;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
