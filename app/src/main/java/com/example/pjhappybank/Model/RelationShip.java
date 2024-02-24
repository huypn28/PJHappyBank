package com.example.pjhappybank.Model;

public class RelationShip {
    private String user;
    private String code;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RelationShip(String user, String code) {

        this.user = user;
        this.code = code;
    }

    @Override
    public String toString() {
        return "RelationShip{" +
                "user='" + user + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
