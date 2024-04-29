package com.voc.model;

import com.google.gson.annotations.SerializedName;

public class Taluk {

    @SerializedName("t_rid")
    private int rid;
    @SerializedName("t_name")
    private String name;
    @SerializedName("t_district_id")
    private int districtId;

    public Taluk() {
    }

    public Taluk(int rid, String name, int districtId) {
        this.rid = rid;
        this.name = name;
        this.districtId = districtId;
    }

    public int getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    public int getDistrictId() {
        return districtId;
    }
}
