package com.example.local.Enity;


import java.time.LocalDateTime;
import java.util.ArrayList;



public class Ingredient {
    private String ingredientId;
    private String ingredientName;
    private LocalDateTime modificationDate;
    private ArrayList<Price> prices;
    private Unity unity;
    private  long quantity;
    private ArrayList<StockMovement> transactions;

public void  addPrice(ArrayList<Price> prices) {
       if(this.prices ==null){
           this.prices = prices;
       }else {
           this.prices.addAll(prices);
       }
}


public void addStockMovement(ArrayList<StockMovement> stockMovements){
    this.transactions.addAll(stockMovements);
}
    public Ingredient() {
    }

    public Ingredient(String ingredientId, String ingredientName, LocalDateTime modificationDate, ArrayList<Price> prices, long quantity, ArrayList<StockMovement> transactions, Unity unity) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.modificationDate = modificationDate;
        this.prices = prices;
        this.quantity = quantity;
        this.transactions = transactions;
        this.unity = unity;
    }

    public Ingredient(String ingredientId, String ingredientName, LocalDateTime modificationDate, ArrayList<Price> prices, ArrayList<StockMovement> transactions, Unity unity) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.modificationDate = modificationDate;
        this.prices = prices;
        this.transactions = transactions;
        this.unity = unity;
    }

    public Ingredient(int ingredientId, String name, int quantity, LocalDateTime modificationDate) {
        this.ingredientId = String.valueOf(ingredientId);
        this.ingredientName = name;
        this.quantity = quantity;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public ArrayList<Price> getPrices() {
        if(prices == null) {
          return  new ArrayList<>();
        }
        return prices;
    }

    public void setPrices(ArrayList<Price> prices) {
        this.prices = prices;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public ArrayList<StockMovement> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<StockMovement> transactions) {
        this.transactions = transactions;
    }

    public Unity getUnity() {
        return unity;
    }

    public void setUnity(Unity unity) {
        this.unity = unity;
    }

    public float getQuantity() {
        return quantity;
    }




    public int getTotalPrice(){
        return (int) (getUnitPrice().getPrice()*quantity);
    }

    public Price getUnitPrice(){
        if(prices == null){
            return new Price();
        }
        return  prices.get(prices.size()-1);
    }

    public Price getPrice(LocalDateTime datePrice){
        return (Price) prices.stream().filter(price -> price.getDatePrice().equals(datePrice));
    }






    public double getAvailableQuantity(LocalDateTime date){
        if(date == null){
            return  this.getTransactions().stream().filter(t -> t.getTransactionDate().isBefore(LocalDateTime.now())).map(t -> t.getUsedQuantity()).reduce((double) 0,(a, b) -> a + b);

        }else{
            return  this.getTransactions().stream().filter(t -> t.getTransactionDate().isBefore(date)).map(t -> t.getUsedQuantity()).reduce((double) 0,(a, b) -> a + b);
        }
    }

    public Ingredient(String ingredientId, LocalDateTime modificationDate, long quantity) {
        this.ingredientId = ingredientId;
        this.modificationDate = modificationDate;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId='" + ingredientId + '\'' +
                ", ingredientName='" + ingredientName + '\'' +
                ", modificationDate=" + modificationDate +
                ", prices=" + prices +
                ", unity=" + unity +
                ", quantity=" + quantity +
                ", transactions=" + transactions +
                '}';
    }



}

// afaka atao hoe msum quantity am le transaction am le date de modif atram le date ao am le truc