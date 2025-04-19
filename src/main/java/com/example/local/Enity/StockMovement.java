package com.example.local.Enity;

import java.time.LocalDateTime;

public class StockMovement {
    private String TransactionId;
   private TransactionType transactionType;
    private LocalDateTime TransactionDate;
    private double usedQuantity;
    private Unity transactionUnity;
    private Ingredient ingredient;

    public StockMovement(String transactionId, TransactionType transactionType, LocalDateTime transactionDate, double usedQuantity, Unity transactionUnity, Ingredient ingredient) {
        TransactionId = transactionId;
        this.transactionType = transactionType;
        TransactionDate = transactionDate;
        this.usedQuantity = usedQuantity;
        this.transactionUnity = transactionUnity;
        this.ingredient = ingredient;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public LocalDateTime getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Unity getTransactionUnity() {
        return transactionUnity;
    }

    public void setTransactionUnity(Unity transactionUnity) {
        this.transactionUnity = transactionUnity;
    }

    public double getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(double usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

    public StockMovement(LocalDateTime transactionDate, TransactionType transactionType, Unity transactionUnity, float usedQuantity) {
        TransactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionUnity = transactionUnity;
        this.usedQuantity = usedQuantity;
    }

    public StockMovement(LocalDateTime transactionDate, String transactionId, TransactionType transactionType, Unity transactionUnity, float usedQuantity) {
        TransactionDate = transactionDate;
        TransactionId = transactionId;
        this.transactionType = transactionType;
        this.transactionUnity = transactionUnity;
        this.usedQuantity = usedQuantity;
    }


    public StockMovement() {
    }



    @Override
    public String toString() {
        return "StockMovement{" +
                "TransactionDate=" + TransactionDate +
                ", TransactionId='" + TransactionId + '\'' +
                ", transactionType=" + transactionType +
                ", usedQuantity=" + usedQuantity +
                ", transactionUnity=" + transactionUnity +
                '}';
    }
}
