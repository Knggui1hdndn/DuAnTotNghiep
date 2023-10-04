package com.knd.duantotnghiep.duantotnghiep.models;

public class Category {
    private String _id;
    private String category;

    public Category() {
    }

    public Category(String _id, String category) {
        this._id = _id;
        this.category = category;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
