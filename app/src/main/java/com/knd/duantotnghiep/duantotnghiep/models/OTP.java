package com.knd.duantotnghiep.duantotnghiep.models;

public class OTP {
    private String type;
    private String email;
    private String phoneNumber;
    private String otp;

    public OTP() {
    }

    public OTP(String type, String email, String phoneNumber, String otp) {
        this.type = type;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.otp = otp;
    }

    public OTP(String email, String phoneNumber, String otp) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
