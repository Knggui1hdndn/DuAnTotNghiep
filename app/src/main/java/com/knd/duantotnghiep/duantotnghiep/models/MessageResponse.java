package com.knd.duantotnghiep.duantotnghiep.models;

import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

public class MessageResponse {
 private String message;
    private String error;

    public MessageResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageResponse(String message) {
        this.message = message;
    }
}
