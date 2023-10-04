package com.knd.duantotnghiep.duantotnghiep.models;

public class Evaluate {
    private String _id;
    private String idProduct;
    private String idUser;
    private String star;
    private String comment;
    private String url[];

    public Evaluate() {
    }

    public Evaluate(String _id, String idProduct, String idUser, String star, String comment, String[] url) {
        this._id = _id;
        this.idProduct = idProduct;
        this.idUser = idUser;
        this.star = star;
        this.comment = comment;
        this.url = url;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }
}
