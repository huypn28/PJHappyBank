package com.example.pjhappybank.Model;

public class User {

    private String id;
    private String name;
    private String emotion;
    private String position;

    public User(String id, String name, String emotion, String position, String position1) {
        this.id = id;
        this.name = name;
        this.emotion = emotion;
        this.position = position1;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
