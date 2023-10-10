package com.knd.duantotnghiep.duantotnghiep.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SearchLocal")
public class SearchLocal {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String _idProduct;
    private String name;
    private String uri;

    public SearchLocal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get_idProduct() {
        return _idProduct;
    }

    public void set_idProduct(String _idProduct) {
        this._idProduct = _idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public SearchLocal( String _idProduct, String name, String uri) {
         this._idProduct = _idProduct;
        this.name = name;
        this.uri = uri;
    }
}
