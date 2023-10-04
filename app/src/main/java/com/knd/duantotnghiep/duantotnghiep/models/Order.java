package com.knd.duantotnghiep.duantotnghiep.models;

import java.util.List;

public class Order {
    private String _id;
    private String idUser;
    private String createAt;
    private String totalAmount;
    private List<DetailOrder> detail;

    public Order() {
    }

    public Order(String _id, String idUser, String createAt, String totalAmount, List<DetailOrder> detail) {
        this._id = _id;
        this.idUser = idUser;
        this.createAt = createAt;
        this.totalAmount = totalAmount;
        this.detail = detail;
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<DetailOrder> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailOrder> detail) {
        this.detail = detail;
    }


}

