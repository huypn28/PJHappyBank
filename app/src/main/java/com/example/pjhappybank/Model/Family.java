package com.example.pjhappybank.Model;

public class Family {
    private String id;
    private String code;
    public Family() {

    }
    public Family(String id, String code) {
        this.id = id;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Family{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
