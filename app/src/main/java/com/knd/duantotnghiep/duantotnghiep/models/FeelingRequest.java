package com.knd.duantotnghiep.duantotnghiep.models;

public class FeelingRequest {
    private String typeFeeling ;

    public String getTypeFeeling() {
        return typeFeeling;
    }

    public FeelingRequest(TypeFeeling typeFeeling) {
        this.typeFeeling = typeFeeling.name();
    }

    public void setTypeFeeling(TypeFeeling typeFeeling) {
        this.typeFeeling = typeFeeling.name();
    }
}
