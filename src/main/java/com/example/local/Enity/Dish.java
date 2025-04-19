package com.example.local.Enity;

import java.util.ArrayList;

public class Dish {
    private String dishId;
    private String dishName;
    private int dishPrice;
    private ArrayList<IngredientInDish> ingredients;


    public void addIngredientDish(ArrayList<IngredientInDish> ingredients){
            if(this.ingredients == null){
                this.ingredients = ingredients;
            }

        this.ingredients.addAll(ingredients);
    }


    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public Dish(String dishId, String dishName, int dishPrice, ArrayList<IngredientInDish> ingredients) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.ingredients = ingredients;
    }

    public Dish() {
    }

    public Dish(String dishId, String dishName, int dishPrice) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
    }


    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(int dishPrice) {
        this.dishPrice = dishPrice;
    }

    public ArrayList<IngredientInDish> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientInDish> ingredients) {
        this.ingredients = ingredients;
    }



    public Integer getUnityPriceOfIngredient(){
        return this.ingredients.stream().map(i -> i.getIngredient().getUnitPrice().getPrice()).reduce(0, (a, b) -> a + b);
    }

    public Double getGrossMargin(){
        return (double) (this.getDishPrice() - this.getUnityPriceOfIngredient());
    }

    public Double getDishAvailableQuantity(){
                                                                                            // getQuantity  == 0
       return  this.getIngredients().stream().map(i -> i.getIngredient().getAvailableQuantity(null)/i.getUsedQuantity()).min(Double::compareTo).orElse(1.0);
    }





    @Override
    public String toString() {
        return "Dish{" +
                "dishId='" + dishId + '\'' +
                ", dishName='" + dishName + '\'' +
                ", dishPrice=" + dishPrice +
                ", ingredients=" + ingredients +
                '}';
    }
}
