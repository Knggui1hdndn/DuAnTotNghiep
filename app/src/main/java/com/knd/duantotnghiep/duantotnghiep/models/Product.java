package com.knd.duantotnghiep.duantotnghiep.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

 public class Product {

     private String _id;
    private String name;
    private String image;
    private String price;
    private String sold;
    private String sale;
    private String describe;
    private String idCata;

    public Product() {
    }

     public String getDescribe() {
         return describe;
     }

     public void setDescribe(String describe) {
         this.describe = describe;
     }

     public Product(String _id, String name, String image, String price, String sold, String sale, String describe, String idCata) {
        this._id = _id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.sold = sold;
        this.sale = sale;
        this.describe = describe;
        this.idCata = idCata;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getIdCata() {
        return idCata;
    }

    public void setIdCata(String idCata) {
        this.idCata = idCata;
    }
}