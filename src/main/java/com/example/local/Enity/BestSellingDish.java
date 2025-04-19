
package com.example.local.Enity;

public class BestSellingDish {
    private String dishName;
    private int quantitySold;
    private double totalAmount;

    public BestSellingDish(String dishName, int quantitySold, double totalAmount) {
        this.dishName = dishName;
        this.quantitySold = quantitySold;
        this.totalAmount = totalAmount;
    }

    public String getDishName() {
        return dishName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "BestSellingDish{" +
                "dishName='" + dishName + '\'' +
                ", quantitySold=" + quantitySold +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
