package com.knd.duantotnghiep.duantotnghiep.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDetail implements Serializable {
    private String _id;
    private String size;
    private ArrayList<ImageQuantity> imageProductQuantity;

    private Boolean isClick = false;

    public Boolean getClick() {
        return isClick;
    }

    public void setClick(Boolean click) {
        isClick = click;
    }


    public ProductDetail(String size, ArrayList<ImageQuantity> imageProductQuantity) {
        this.size = size;
        this.imageProductQuantity = imageProductQuantity;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "_id='" + _id + '\'' +
                ", size='" + size + '\'' +
                ", imageProductQuantity=" + imageProductQuantity +
                ", isClick=" + isClick +
                '}';
    }

    public ProductDetail() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ArrayList<ImageQuantity> getImageProducts() {
        return imageProductQuantity;
    }

    public void setImageProducts(ArrayList<ImageQuantity> imageProducts) {
        this.imageProductQuantity = imageProducts;
    }
}
