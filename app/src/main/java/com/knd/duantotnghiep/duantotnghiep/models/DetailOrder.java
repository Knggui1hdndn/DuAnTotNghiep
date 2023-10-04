package com.knd.duantotnghiep.duantotnghiep.models;

public class DetailOrder{
    private String _id;
    private String idProduct;
    private Integer quantity;
    private Integer sale;
    private Float price;
    private Float intoMoney;

    public DetailOrder() {
    }

    public DetailOrder(String _id, String idProduct, Integer quantity, Integer sale, Float price, Float intoMoney) {
        this._id = _id;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.sale = sale;
        this.price = price;
        this.intoMoney = intoMoney;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getIntoMoney() {
        return intoMoney;
    }

    public void setIntoMoney(Float intoMoney) {
        this.intoMoney = intoMoney;
    }
}
