package com.example.local.Enity;

public class SalesElement {
    private String salesPoint;
    private String dish;
    private long quantitySold;
    private double totalAmount;

    // Constructeur
    public SalesElement(String salesPoint, String dish, long quantitySold, double totalAmount) {
        this.salesPoint = salesPoint;
        this.dish = dish;
        this.quantitySold = quantitySold;
        this.totalAmount = totalAmount;
    }

    // Getters et Setters
    public String getSalesPoint() {
        return salesPoint;
    }

    public void setSalesPoint(String salesPoint) {
        this.salesPoint = salesPoint;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public long getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(long quantitySold) {
        this.quantitySold = quantitySold;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "SalesElement{" +
                "salesPoint='" + salesPoint + '\'' +
                ", dish='" + dish + '\'' +
                ", quantitySold=" + quantitySold +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
