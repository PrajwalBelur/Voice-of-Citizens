package com.voc.model;

import com.google.gson.annotations.SerializedName;

public class District {

    @SerializedName("d_rid")
    private int rid;
    @SerializedName("d_name")
    private String name;

    public District() {
    }

    public District(int rid, String name) {
        this.rid = rid;
        this.name = name;
    }

    public int getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }
}
