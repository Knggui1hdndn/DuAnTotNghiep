package com.knd.duantotnghiep.duantotnghiep.models;

public class OTP {
    private String type;
    private String account;
    private String otp;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public OTP() {
    }

    public OTP(String type, String account,  String otp) {
        this.type = type;
         this.account = account;
        this.otp = otp;
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
