package com.example.pjhappybank.Model;

public class Opinion {
    private String opinionId;
    private String opinionText;

    public Opinion(String opinionId, String opinionText) {
        this.opinionId = opinionId;
        this.opinionText = opinionText;
    }

    public String getOpinionId() {
        return opinionId;
    }

    public String getOpinionText() {
        return opinionText;
    }
}

