package com.knd.duantotnghiep.duantotnghiep.models;

enum TypeRole{
ADMIN,USER
}
enum AuthType{
    LOCAL,GOOGLE
}
public class User {

    private String _id;
    private String name;
    private String avatar;
    private String [] address;
    private String phoneNumber;
    private String password;
    private AuthType authType = AuthType.LOCAL;
    private String authGoogleId = null;
    private TypeRole role =TypeRole.USER;

    public User() {
    }

    public User(String _id, String name, String avatar, String[] address, String phoneNumber, String password, AuthType authType, String authGoogleId, TypeRole role) {
        this._id = _id;
        this.name = name;
        this.avatar = avatar;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.authType = authType;
        this.authGoogleId = authGoogleId;
        this.role = role;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public String getAuthGoogleId() {
        return authGoogleId;
    }

    public void setAuthGoogleId(String authGoogleId) {
        this.authGoogleId = authGoogleId;
    }

    public TypeRole getRole() {
        return role;
    }

    public void setRole(TypeRole role) {
        this.role = role;
    }
}
