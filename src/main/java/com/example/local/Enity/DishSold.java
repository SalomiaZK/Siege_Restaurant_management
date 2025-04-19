package com.example.local.Enity;

public class DishSold {
    private int dishIdentifier;
    private String dishName;
    private int quantitySold;
    private int price;
    private String salesPoint;

    public DishSold() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSalesPoint() {
        return salesPoint;
    }

    public void setSalesPoint(String salesPoint) {
        this.salesPoint = salesPoint;
    }

    public DishSold(int dishIdentifier, String dishName, int quantitySold, int price, String salesPoint) {
        this.dishIdentifier = dishIdentifier;
        this.dishName = dishName;
        this.quantitySold = quantitySold;
        this.price = price;
        this.salesPoint = salesPoint;
    }

    // Constructeur
    public DishSold(int dishIdentifier, String dishName, int quantitySold) {
        this.dishIdentifier = dishIdentifier;
        this.dishName = dishName;
        this.quantitySold = quantitySold;
    }

    // Getters et Setters
    public int getDishIdentifier() {
        return dishIdentifier;
    }

    public void setDishIdentifier(int dishIdentifier) {
        this.dishIdentifier = dishIdentifier;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    // toString()
    @Override
    public String toString() {
        return "DishSold{" +
                "dishIdentifier=" + dishIdentifier +
                ", dishName='" + dishName + '\'' +
                ", quantitySold=" + quantitySold +
                '}';
    }
}
