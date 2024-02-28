package com.example.pjhappybank.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Heart {

    private String quantity;
    private String date;
    private String month; // New variable

    public Heart() {
        this.quantity = "0";
        this.date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(new Date());
        this.month = new SimpleDateFormat("MM", Locale.ENGLISH).format(new Date()); // Set month in constructor
    }
    public Heart(String quantity, String date) {
        this.quantity = quantity;
        this.date = date;
    }
    public Heart(String quantity, String date, String month) {
        this.quantity = quantity;
        this.date = date;
        this.month = month;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
