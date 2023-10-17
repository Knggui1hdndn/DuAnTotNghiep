package com.knd.duantotnghiep.duantotnghiep.models;

 
import java.time.Instant;

public class PayQR {
    private String _id;
    private String idUser;
    private String idOrder;
    private String url;
    private String note;
    private long timeCreateAt;
    private long expiration;

    public PayQR() {
        // Default constructor
    }

 

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getTimeCreateAt() {
        return timeCreateAt;
    }

    public void setTimeCreateAt(long timeCreateAt) {
        this.timeCreateAt = timeCreateAt;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}

