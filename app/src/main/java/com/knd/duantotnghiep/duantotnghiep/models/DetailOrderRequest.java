package com.knd.duantotnghiep.duantotnghiep.models;

public class DetailOrderRequest {
    private String _id;
    private String idProduct;
    private String name;
    private String idImageProductQuantity;
    private Integer quantity;
    private String size;
    private Float sale;
    private Float price;
    private Float intoMoney;
    private boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetailOrderRequest(String _id, Integer quantity, boolean isSelected) {
        this._id = _id;
        this.quantity = quantity;
        this.isSelected = isSelected;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public DetailOrderRequest() {
    }

    public DetailOrderRequest(  String idProduct, String idImageProductQuantity, Integer quantity, String size, Float sale, Float price, Float intoMoney, boolean isSelected,String name) {
         this.idProduct = idProduct;
        this.idImageProductQuantity = idImageProductQuantity;
        this.quantity = quantity;
        this.size = size;
        this.sale = sale;
        this.price = price;
        this.intoMoney = intoMoney;
        this.isSelected = isSelected;
        this.name = name;
    }

    public String getIdImageProductQuantity() {
        return idImageProductQuantity;
    }

    public void setIdImageProductQuantity(String idImageProductQuantity) {
        this.idImageProductQuantity = idImageProductQuantity;
    }

    public String getSize() {
        return size;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public DetailOrderRequest(String _id, String idProduct, String idImageProductQuantity, Integer quantity, String size, Float sale, Float price, Float intoMoney) {
        this._id = _id;
        this.idImageProductQuantity = idImageProductQuantity;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.size = size;
        this.sale = sale;
        this.price = price;
        this.intoMoney = intoMoney;
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

    public Float getSale() {
        return sale;
    }

    public void setSale(Float sale) {
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
