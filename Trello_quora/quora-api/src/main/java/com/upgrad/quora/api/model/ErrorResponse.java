package com.upgrad.quora.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ErrorResponse {

    @JsonProperty("code")
    private String code = null;
    @JsonProperty("message")
    private String message = null;

    public ErrorResponse code(String code){
        this.code = code;
        return this;
    }

    @ApiModelProperty(required = true,value = "Application specific standard error code")
    @NotNull


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ErrorResponse message(String message){
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
