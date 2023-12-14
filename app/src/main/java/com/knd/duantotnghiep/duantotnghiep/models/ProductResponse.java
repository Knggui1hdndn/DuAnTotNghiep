package com.knd.duantotnghiep.duantotnghiep.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class ProductResponse {
    private String _id;
    private String name;
    private Float price;
    private Integer sold;
    private double star;
    private Float sale;
    private String description;
    private Boolean isFavourite = true;
    private ArrayList<ProductDetail> productDetails = new ArrayList<>();

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public ProductResponse() {
    }

    @Override
    public boolean equals(Object o) {
        if ((o instanceof ProductResponse that)) return that.get_id().equals(get_id());
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", sold=" + sold +
                ", star=" + star +
                ", sale=" + sale +
                ", description='" + description + '\'' +
                ", isFavourite=" + isFavourite +
                ", productDetails=" + productDetails +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public String getDescribe() {
        return description;
    }

    public void setDescribe(String describe) {
        this.description = describe;
    }

    public ArrayList<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ArrayList<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    public ProductResponse(String _id, String name, Float price, Integer sold, Float sale, String describe, ArrayList<ProductDetail> productDetails) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.sold = sold;
        this.sale = sale;
        this.description = describe;
        this.productDetails = productDetails;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Float getSale() {
        return sale;
    }

    public void setSale(Float sale) {
        this.sale = sale;
    }


}