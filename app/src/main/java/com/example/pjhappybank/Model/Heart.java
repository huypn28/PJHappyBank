package com.example.pjhappybank.Model;

public class Heart {

    private String quantity;
    private String date;

    public Heart(String quantity, String date) {
        this.quantity = quantity;
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
