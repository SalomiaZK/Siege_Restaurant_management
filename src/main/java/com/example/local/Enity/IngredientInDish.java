package com.example.local.Enity;

public class IngredientInDish {
    private Ingredient ingredient;
    private int usedQuantity;

    public IngredientInDish(Ingredient ingredient, int usedQuantity) {
        this.ingredient = ingredient;
        this.usedQuantity = usedQuantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(int usedQuantity) {
        this.usedQuantity = usedQuantity;
    }


    @Override
    public String toString() {
        return "IngredientInDish{" +
                "ingredient=" + ingredient +
                ", usedQuantity=" + usedQuantity +
                '}';
    }
}
