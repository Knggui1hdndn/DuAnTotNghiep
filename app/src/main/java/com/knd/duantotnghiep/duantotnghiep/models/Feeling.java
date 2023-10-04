package com.knd.duantotnghiep.duantotnghiep.models;

enum TypeFeeling{
    HAPPY,//vui
    SAD,//buồn
    LOVE,//yêu
    ANGRY,//giận dữ
}
public class Feeling {
    private String _id;
    private String idEvaluate ;
    private String idUser ;
    private TypeFeeling type ;

    public Feeling() {
    }

    public Feeling(String _id, String idEvaluate, String idUser, TypeFeeling type) {
        this._id = _id;
        this.idEvaluate = idEvaluate;
        this.idUser = idUser;
        this.type = type;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public TypeFeeling getType() {
        return type;
    }

    public void setType(TypeFeeling type) {
        this.type = type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdEvaluate() {
        return idEvaluate;
    }

    public void setIdEvaluate(String idEvaluate) {
        this.idEvaluate = idEvaluate;
    }
}
