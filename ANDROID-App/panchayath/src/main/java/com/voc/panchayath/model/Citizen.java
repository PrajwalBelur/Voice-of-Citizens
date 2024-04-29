package com.voc.panchayath.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Citizen implements Serializable {

    @SerializedName("c_rid")
    private int rid;
    @SerializedName("c_name")
    private String name;
    @SerializedName("c_contact")
    private String contact;
    @SerializedName("c_email")
    private String email;
    @SerializedName("c_address")
    private String address;
    @SerializedName("c_gender")
    private char gender;
    @SerializedName("c_profile")
    private String profilePicUrl;
    @SerializedName("c_aadhar")
    private String aadharPicUrl;
    @SerializedName("c_grama")
    private int panchayath;
    @SerializedName("g_name")
    private String gramaName;

    public int getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact.length() == 10 ? ("+91" + contact) : contact;
    }

    public String getContactWithoutCountryCode() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public char getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public String getAadharPicUrl() {
        return aadharPicUrl;
    }

    public int getPanchayath() {
        return panchayath;
    }

    public String getGramaName() {
        return gramaName;
    }
}
