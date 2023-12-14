package com.knd.duantotnghiep.duantotnghiep.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EvaluateResponse {
    private String _id;
    @SerializedName("idUser")
    private User user;
    private Integer star;
    private String comment;
    private Long createAt;
    private ArrayList<Feeling> feelings;
    private ArrayList<String> url ;



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Feeling> getFeelings() {
        return feelings;
    }

    public void setFeelings(ArrayList<Feeling> feelings) {
        this.feelings = feelings;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }



    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }
}

