package com.knd.duantotnghiep.duantotnghiep.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SearchLocal")
public class SearchLocal {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String _id ;
    private String name;
    private String image;

    public SearchLocal(  String _id, String name, String image) {
         this._id = _id;
        this.name = name;
        this.image = image;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
