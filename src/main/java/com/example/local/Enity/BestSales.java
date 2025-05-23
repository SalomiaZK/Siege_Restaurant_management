package com.example.local.Enity;

import java.time.LocalDateTime;
import java.util.List;

public class BestSales {
    private  String id;
    private LocalDateTime updatedAt;
    private List<SalesElement> sales;

    // Constructeur
    public BestSales(LocalDateTime updatedAt, List<SalesElement> sales) {
        this.updatedAt = updatedAt;
        this.sales = sales;
    }

    // Getters et Setters
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<SalesElement> getSales() {
        return sales;
    }

    public void setSales(List<SalesElement> sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "BestSales{" +
                "updatedAt=" + updatedAt +
                ", sales=" + sales +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BestSales(String id, LocalDateTime updatedAt, List<SalesElement> sales) {
        this.id = id;
        this.updatedAt = updatedAt;
        this.sales = sales;
    }
}
