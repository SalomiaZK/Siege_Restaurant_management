package com.example.local.Enity;


import lombok.Data;

import java.time.LocalDateTime;



@Data
public class Price {
    private  String idPrice;
    private int price;
    private LocalDateTime datePrice;


    public Price(String idPrice,  int price, LocalDateTime datePrice ) {
        this.datePrice = datePrice;
        this.idPrice = idPrice;
        this.price = price;
    }

    public Price() {
    }

    public Price(int price, LocalDateTime dateTime) {
        this.price = price;
        this.datePrice= dateTime;
    }


    public String getIdPrice() {
        return idPrice;
    }

    public void setIdPrice(String idPrice) {
        this.idPrice = idPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getDatePrice() {
        return datePrice;
    }

    public void setDatePrice(LocalDateTime datePrice) {
        this.datePrice = datePrice;
    }


    @Override
    public String toString() {
        return "Price{" +
                "datePrice=" + datePrice +
                ", idPrice='" + idPrice + '\'' +
                ", price=" + price +
                '}';
    }
}
