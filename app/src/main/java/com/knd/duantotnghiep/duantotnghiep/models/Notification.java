package com.knd.duantotnghiep.duantotnghiep.models;

public class Notification {
    private String _id;
    private String idSender;
    private String idReceve;
    private String url;
    private String title;
    private String body;
    private Long createAt;
    private boolean isSeen=false;

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
    public Notification() {
    }
    public Notification(String url, String title, String body) {
        this.url = url;
        this.title = title;
        this.body = body;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceve() {
        return idReceve;
    }

    public void setIdReceve(String idReceve) {
        this.idReceve = idReceve;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Notification(String _id, String idSender, String idReceve, String url, String title, String body, Long createAt) {
        this._id = _id;
        this.idSender = idSender;
        this.idReceve = idReceve;
        this.url = url;
        this.title = title;
        this.body = body;
        this.createAt = createAt;
    }
}
