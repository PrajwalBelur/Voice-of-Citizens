package com.voc.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegPageDetails {

    @SerializedName("taluk")
    private List<Taluk> taluk;
    @SerializedName("district")
    private List<District> district;
    @SerializedName("panchayath")
    private List<Panchayath> panchayath;
    @SerializedName("grama")
    private List<Grama> grama;

    public List<Taluk> getTaluk() {
        return taluk;
    }

    public void setTaluk(List<Taluk> taluk) {
        this.taluk = taluk;
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }

    public List<Panchayath> getPanchayath() {
        return panchayath;
    }

    public void setPanchayath(List<Panchayath> panchayath) {
        this.panchayath = panchayath;
    }

    public List<Grama> getGrama() {
        return grama;
    }

    public void setGrama(List<Grama> grama) {
        this.grama = grama;
    }
}
