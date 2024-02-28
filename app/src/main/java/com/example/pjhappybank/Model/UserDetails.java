package com.example.pjhappybank.Model;

public class UserDetails {
    private String name;
    private String position;
    private String emotion;
    private String quantity;

    public UserDetails(String name, String position, String emotion, String quantity) {
        this.name = name;
        this.position = position;
        this.emotion = emotion;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
