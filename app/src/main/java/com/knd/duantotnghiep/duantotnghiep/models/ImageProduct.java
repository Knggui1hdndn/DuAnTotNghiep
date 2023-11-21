package com.knd.duantotnghiep.duantotnghiep.models;

public class ImageProduct {
    private String _id;
    private String idProduct;
     private String color;
    private String image;

    @Override
    public String toString() {
        return "ImageProduct{" +
                "_id='" + _id + '\'' +
                ", idProduct='" + idProduct + '\'' +
                ", color='" + color + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public ImageProduct() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




 }
