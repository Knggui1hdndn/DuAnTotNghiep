package com.knd.duantotnghiep.duantotnghiep.models;

import java.util.List;

public class OrderResponse {
    private String _id;
    private String idUser;
    private Long createAt;
    private Double totalAmount;
    private String name;
    private String phoneNumber;
    private String address;
    private String payments;
    private String status;
    private String description;
    private boolean isPay;
    private List<DetailOrderResponse> detail;

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public OrderResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderResponse(String _id, String idUser, Long createAt, Double totalAmount, String phoneNumber, String address, String payments, String status, List<DetailOrderResponse> detail) {
        this._id = _id;
        this.idUser = idUser;
        this.createAt = createAt;
        this.totalAmount = totalAmount;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.payments = payments;
        this.status = status;
        this.detail = detail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<DetailOrderResponse> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailOrderResponse> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "_id='" + _id + '\'' +
                ", idUser='" + idUser + '\'' +
                ", createAt='" + createAt + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", payments='" + payments + '\'' +
                ", status='" + status + '\'' +
                ", detail=" + detail +
                '}';
    }
}

