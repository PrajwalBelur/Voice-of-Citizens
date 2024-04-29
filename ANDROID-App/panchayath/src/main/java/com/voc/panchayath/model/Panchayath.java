package com.voc.panchayath.model;

import com.google.gson.annotations.SerializedName;

public class Panchayath {

    @SerializedName("pan_rid")
    private int rid;
    @SerializedName("g_name")
    private String grama;
    @SerializedName("pan_president")
    private String president;
    @SerializedName("pan_conatct")
    private String contact;
    @SerializedName("pan_tel_no")
    private String telePhone;
    @SerializedName("pan_description")
    private String description;
    @SerializedName("pan_email")
    private String email;

    public int getRid() {
        return rid;
    }

    public String getGramaName() {
        return grama;
    }

    public String getPresident() {
        return president;
    }

    public String getContact() {
        return (contact.length() == 10) ? ("+91" + contact) : contact;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }
}
