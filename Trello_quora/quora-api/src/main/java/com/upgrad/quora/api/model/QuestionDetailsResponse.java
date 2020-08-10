package com.upgrad.quora.api.model;

public class QuestionDetailsResponse {


    private String uuid;
    private String content;




    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QuestionDetailsResponse(String uuid, String content) {
        this.uuid = uuid;
        this.content = content;

    }

    public QuestionDetailsResponse() {
    }
}
