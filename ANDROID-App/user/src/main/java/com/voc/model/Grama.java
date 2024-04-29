package com.voc.model;

import com.google.gson.annotations.SerializedName;

public class Grama {

    @SerializedName("g_rid")
    private int rid;
    @SerializedName("g_name")
    private String name;
    @SerializedName("g_taluk_id")
    private int talukId;

    public int getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    public int getTalukId() {
        return talukId;
    }
}
