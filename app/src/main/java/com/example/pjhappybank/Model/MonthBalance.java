package com.example.pjhappybank.Model;

public class MonthBalance {

    private String monthKey;
    private int totalQuantity;
    private int quantityChange;

    public MonthBalance(String monthKey, int totalQuantity, int quantityChange) {
        this.monthKey = monthKey;
        this.totalQuantity = totalQuantity;
        this.quantityChange = quantityChange; // Default value, can be set later
    }

    public String getMonthKey() {
        return monthKey;
    }

    public void setMonthKey(String monthKey) {
        this.monthKey = monthKey;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }
}
