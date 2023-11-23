package com.knd.duantotnghiep.duantotnghiep.models;

import com.google.gson.annotations.SerializedName;

public class DetailOrderResponse {
    private String _id;
     private String idOrder;
    @SerializedName("idProduct")
    private ProductResponse productResponse;
    @SerializedName("idImageProductQuantity")
    private ImageQuantity imageQuantity;
    private Integer quantity;
    private Boolean isSelected=false;
    private String size;
    private Integer sale;
    private Float price;
    private Float intoMoney;

    public String getIdOrder() {
        return idOrder;
    }

    public DetailOrderResponse(String _id, String idOrder, ProductResponse productResponse, ImageQuantity imageQuantity, Integer quantity, Boolean isSelected, String size, Integer sale, Float price, Float intoMoney) {
        this._id = _id;
        this.idOrder = idOrder;
        this.productResponse = productResponse;
        this.imageQuantity = imageQuantity;
        this.quantity = quantity;
        this.isSelected = isSelected;
        this.size = size;
        this.sale = sale;
        this.price = price;
        this.intoMoney = intoMoney;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public ImageQuantity getImageQuantity() {
        return imageQuantity;
    }

    public void setImageQuantity(ImageQuantity imageQuantity) {
        this.imageQuantity = imageQuantity;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
    public ProductResponse getProductResponse() {
        return productResponse;
    }

    public void setProductResponse(ProductResponse productResponse) {
        this.productResponse = productResponse;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }



    public DetailOrderResponse() {
    }

    public DetailOrderResponse(String _id, ImageQuantity imageQuantity, Integer quantity, Integer sale, Float price, Float intoMoney) {
        this._id = _id;
        this.imageQuantity = imageQuantity;
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

    @Override
    public String toString() {
        return "DetailOrderResponse{" +
                "_id='" + _id + '\'' +
                ", productResponse=" + productResponse +
                ", imageQuantity=" + imageQuantity +
                ", quantity=" + quantity +
                ", isSelected=" + isSelected +
                ", size='" + size + '\'' +
                ", sale=" + sale +
                ", price=" + price +
                ", intoMoney=" + intoMoney +
                '}';
    }

    public void setIntoMoney(Float intoMoney) {
        this.intoMoney = intoMoney;
    }
}
