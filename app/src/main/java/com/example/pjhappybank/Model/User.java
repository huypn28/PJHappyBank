package com.example.pjhappybank.Model;

public class User {

    private String id;
    private String name;
    private String emotion;

    public User(String id, String name, String emotion) {
        this.id = id;
        this.name = name;
        this.emotion = emotion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}
