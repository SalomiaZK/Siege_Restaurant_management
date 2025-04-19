package com.example.local.Enity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;


public class OrderedDish {
   private String idOrderedDish;
    private Dish dish;
    private int quantity;
    private ArrayList<StatusWithDate> statusWithDate;



    public void  updateStatus(){
        if(getStatusInt(getActualStatus()) <5){
        StatusWithDate status = new StatusWithDate(getStatusInt(getActualStatus())+1,LocalDateTime.now() );
        statusWithDate.add(status);}
    }

    @Override
    public String toString() {
        return "OrderedDish{" +
                "dish=" + dish +
                ", idOrderedDish='" + idOrderedDish + '\'' +
                ", quantity=" + quantity +
                ", statusWithDate=" + statusWithDate +
                '}';
    }

    public Status getActualStatus() {
        if (statusWithDate == null || statusWithDate.isEmpty()) {
            return Status.CREATED; // ou une valeur par défaut appropriée
        }

        return statusWithDate.stream()
                .max(Comparator.comparingInt(StatusWithDate::getIntStatus))
                .map(StatusWithDate::getStatus)
                .orElse(Status.CREATED);
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getIdOrderedDish() {
        return idOrderedDish;
    }

    public void setIdOrderedDish(String idOrderedDish) {
        this.idOrderedDish = idOrderedDish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ArrayList<StatusWithDate> getStatusWithDate() {
        return statusWithDate;
    }

    public void setStatusWithDate(ArrayList<StatusWithDate> statusWithDate) {
        this.statusWithDate = statusWithDate;
    }

    public OrderedDish() {
    }

    public OrderedDish(Dish dish, String idOrderedDish, int quantity, ArrayList<StatusWithDate> statusWithDate) {
        this.dish = dish;
        this.idOrderedDish = idOrderedDish;
        this.quantity = quantity;
        this.statusWithDate = statusWithDate;
    }

    public int getStatusInt(Status status){
        switch (status){
            case CREATED:
                return 1;
            case VALIDATED:
                return 2;
            case IN_PREPARATION:
                return 3;
            case FINISHED:
                return 4;
            case SERVED:
                return  5;
            default:
                throw  new RuntimeException("not a number status");
        }
    }

}
