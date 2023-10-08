package com.knd.duantotnghiep.duantotnghiep.models;

public class Feeling {
    private String _id;
    private String idEvaluate ;
    private String idUser ;
    private String typeFeeling ;

    public Feeling() {
    }

    public Feeling(String _id, String idEvaluate, String idUser, TypeFeeling typeFeeling) {
        this._id = _id;
        this.idEvaluate = idEvaluate;
        this.idUser = idUser;
        this.typeFeeling = typeFeeling.name();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getType() {
        return typeFeeling;
    }

    public void setType(TypeFeeling type) {
        this.typeFeeling = type.name();
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
