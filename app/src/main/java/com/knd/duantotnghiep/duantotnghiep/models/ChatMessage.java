package com.knd.duantotnghiep.duantotnghiep.models;

import java.util.ArrayList;
import java.util.List;

public class ChatMessage {
    private String _id;
    private String message="";
    private String sender;
    private String receiver;
    private Long timeSend = System.currentTimeMillis();
    private List<String> url = new ArrayList<>();
    private Boolean isToUser = false;

    public Boolean getToUser() {
        return isToUser;
    }

    public void setToUser(Boolean toUser) {
        isToUser = toUser;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }



    public Long getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(Long timeSend) {
        this.timeSend = timeSend;
    }
}
