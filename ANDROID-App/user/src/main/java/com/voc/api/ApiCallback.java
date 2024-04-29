package com.voc.api;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("WeakerAccess")
public class ApiCallback<T> implements Callback<T> {

    private ApiResponseListener<T> callback;

    public ApiCallback(@NonNull ApiResponseListener<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (callback != null) {
            if (response.isSuccessful()) {
                callback.onSuccess(response.body());
            } else {
                callback.onError(new ApiError("Couldn't get response from API..."));
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (callback != null) {
            callback.onError(t);
        }
    }
}
