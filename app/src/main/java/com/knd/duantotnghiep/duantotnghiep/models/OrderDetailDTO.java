package com.knd.duantotnghiep.duantotnghiep.models;

public class OrderDetailDTO {
    private String size;
    private int quantity;
    private int sale;
    private double price;
    private double intoMoney;
    private boolean isSelected;
    private int __v;
    private String name;
    private String color;
    private String image;
private Boolean isEvaluate;
    public OrderDetailDTO(String size, int quantity, int sale, double price, double intoMoney, boolean isSelected, int __v, String name, String color, String image) {
        this.size = size;
        this.quantity = quantity;
        this.sale = sale;
        this.price = price;
        this.intoMoney = intoMoney;
        this.isSelected = isSelected;
        this.__v = __v;
        this.name = name;
        this.color = color;
        this.image = image;
    }

    public Boolean getEvaluate() {
        return isEvaluate;
    }

    public void setEvaluate(Boolean evaluate) {
        isEvaluate = evaluate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getIntoMoney() {
        return intoMoney;
    }

    public void setIntoMoney(double intoMoney) {
        this.intoMoney = intoMoney;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
// getters and setters
}
