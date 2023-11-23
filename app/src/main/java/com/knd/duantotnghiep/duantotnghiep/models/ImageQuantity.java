package com.knd.duantotnghiep.duantotnghiep.models;

import androidx.annotation.NonNull;

public class ImageQuantity {

    private String _id;
    private String idProductDetail;
    private ImageProduct imageProduct;
    private int quantity;
    private Boolean isClick = false;

    public String getIdProductDetail() {
        return idProductDetail;
    }

    public void setIdProductDetail(String idProductDetail) {
        this.idProductDetail = idProductDetail;
    }

    @NonNull
    @Override
    public String toString() {
        return "ImageQuantity{" +
                "_id='" + _id + '\'' +
                ", idProductDetail='" + idProductDetail + '\'' +
                ", imageProduct=" + imageProduct +
                ", quantity=" + quantity +
                ", isClick=" + isClick +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Boolean getClick() {
        return isClick;
    }

    public void setClick(Boolean click) {
        isClick = click;
    }

    public ImageProduct getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(ImageProduct imageProduct) {
        this.imageProduct = imageProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
