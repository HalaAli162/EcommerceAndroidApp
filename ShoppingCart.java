package com.example.e4trely;

import java.util.List;

public class ShoppingCart {
    private int CastID;
    private int CartID;
    private List<Product>Products;

    public int getCastID() {
        return CastID;
    }

    public int getCartID() {
        return CartID;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }

    public void setCastID(int castID) {
        CastID = castID;
    }

    public void setCartID(int cartID) {
        CartID = cartID;
    }

}
