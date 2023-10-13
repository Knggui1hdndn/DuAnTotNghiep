package com.knd.duantotnghiep.duantotnghiep.models;

enum RoleType {
    ADMIN, USER
}

enum AuthType {
    LOCAL, GOOGLE
}

public class User {
    private String _id;
    private String name;
    private String avatar;
    private String[] address;
    private String phoneNumber;
    private String password;
    private String authType = AuthType.LOCAL.name();
    private String authGoogleId = null;
    private String roleType = RoleType.USER.name();

    public User() {
    }

    public User(String _id, String name, String avatar, String[] address, String phoneNumber, String password, AuthType authType, String authGoogleId, RoleType typeRole) {
        this._id = _id;
        this.name = name;
        this.avatar = avatar;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.authType = authType.name();
        this.authGoogleId = authGoogleId;
        this.roleType = typeRole.name();
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

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType.name();
    }

    public String getAuthGoogleId() {
        return authGoogleId;
    }

    public void setAuthGoogleId(String authGoogleId) {
        this.authGoogleId = authGoogleId;
    }

    public String getRole() {
        return roleType;
    }

    public void setRole(RoleType role) {
        this.roleType = role.name();
    }
}
