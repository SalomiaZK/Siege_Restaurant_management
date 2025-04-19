package com.example.local.Enity;




public class DishProcessingTimeResponse {
    private String dishId;
    private String dishName;
    private String unit;
    private String aggregation;
    private double processingTime;

    public DishProcessingTimeResponse(String dishId, String dishName, String unit, String aggregation, double processingTime) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.unit = unit;
        this.aggregation = aggregation;
        this.processingTime = processingTime;
    }

    // Getters and setters

    public String getDishId() { return dishId; }
    public void setDishId(String dishId) { this.dishId = dishId; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getAggregation() { return aggregation; }
    public void setAggregation(String aggregation) { this.aggregation = aggregation; }

    public double getProcessingTime() { return processingTime; }
    public void setProcessingTime(double processingTime) { this.processingTime = processingTime; }

    @Override
    public String toString() {
        return "DishProcessingTimeResponse{" +
                "dishId='" + dishId + '\'' +
                ", dishName='" + dishName + '\'' +
                ", unit='" + unit + '\'' +
                ", aggregation='" + aggregation + '\'' +
                ", processingTime=" + processingTime +
                '}';
    }
}
