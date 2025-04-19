package com.example.local.Enity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;


public class Order {
    private String idOrder;
    private ArrayList<OrderedDish> orderedDish; //table hafa
    private LocalDateTime orderDate;
    // table  hafa


    public Order() {
    }

    public Order(String idOrder, LocalDateTime orderDate, ArrayList<OrderedDish> orderedDish) {
        this.idOrder = idOrder;
        this.orderDate = orderDate;
        this.orderedDish = orderedDish;
    }

    // Dans votre classe OrderedDish
    public Status getActualStatus() {
        // 1. Vérification null/empty
        if (orderedDish == null || orderedDish.isEmpty()) {
            return Status.CREATED;
        }

        // 2. Trouver le statut minimum SANS Optional.get()
        return orderedDish.stream()
                .filter(dish -> dish.getActualStatus() != null) // Filtrer les null
                .map(OrderedDish::getActualStatus)
                .min(Comparator.comparingInt(this::getStatusInt))
                .orElse(Status.CREATED); // Jamais null
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public ArrayList<OrderedDish> getOrderedDish() {
        return orderedDish;
    }

    public void setOrderedDish(ArrayList<OrderedDish> orderedDish) {
        this.orderedDish = orderedDish;
    }


//
//    public StatusWithDate getActualStatus(){
//        if(status.size() == 0){
//            return new StatusWithDate(
//                    Status.CREATED,
//                    LocalDateTime.now()
//            );
//        }
//        return this.status.getLast();
//    }



    public double getTotalAmount(){
        return  this.orderedDish.stream().map(o -> o.getDish().getDishPrice() * o.getQuantity()).reduce(0,(a,b) -> a+b);
    }


    public Status statusTranslator(int statusCode){
        switch (statusCode){
            case 1:
                return Status.CREATED;
            case 2 :
                return Status.VALIDATED;
            case 3:
                return Status.IN_PREPARATION;
            case 4:
                return Status.FINISHED;
            case 5:
                return Status.SERVED;
            default:
                throw  new RuntimeException("not a number status");
        }

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
