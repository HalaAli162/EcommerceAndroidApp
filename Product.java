package com.example.e4trely;

public class Product {
    private int ProdID;
    private String ProName;
    private int Price;
    private int Quantity;
    private int CatID;

    public int getProdID() {
        return ProdID;
    }

    public String getProName() {
        return ProName;
    }

    public float getPrice() {
        return Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getCatID() {
        return CatID;
    }

    public void setProdID(int prodID) {
        ProdID = prodID;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void setCatID(int catID) {
        CatID = catID;
    }
}
