package com.knd.duantotnghiep.duantotnghiep.models;

import java.io.Serializable;

public class SignUpRequest implements Serializable {//test
    private String username;
    private String password;
    private String address;
    private String phoneNumber;

    public SignUpRequest(String username, String password, String address, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
