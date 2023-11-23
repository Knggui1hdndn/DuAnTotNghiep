package com.knd.duantotnghiep.duantotnghiep.models;

public class OrderRequest {
    private String name;
    private String phoneNumber;
    private String address;
    private String payments = Payments.TRANSFER.name();

    public OrderRequest(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public OrderRequest(String name, String phoneNumber, String address, Payments payments) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.payments = payments.name();
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(Payments payments) {
        this.payments = payments.name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
