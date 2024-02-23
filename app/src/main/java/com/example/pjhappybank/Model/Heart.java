package com.example.pjhappybank.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Heart {

    private String quantity;
    private String date;

    public Heart() {
        this.quantity = "0";
        this.date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(new Date());
    }

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
