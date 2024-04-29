package com.voc.panchayath.api;

public interface ApiResponseListener<T> {
    void onSuccess(T response);
    void onError(Throwable err);
}
