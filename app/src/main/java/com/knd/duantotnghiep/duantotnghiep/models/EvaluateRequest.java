package com.knd.duantotnghiep.duantotnghiep.models;

import java.util.ArrayList;

public class EvaluateRequest {
    private String star;
    private String comment;

    public EvaluateRequest() {
    }

    public EvaluateRequest(String star, String comment) {
        this.star = star;
        this.comment = comment;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
