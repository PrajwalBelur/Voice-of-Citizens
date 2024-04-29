package com.voc.panchayath.model;

import com.google.gson.annotations.SerializedName;

public class ResponseWrapper<T> {

    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("error")
    private String error;
    @SerializedName("info")
    private T response;

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getError() {
        return error;
    }

    public T getResponse() {
        return response;
    }
}
