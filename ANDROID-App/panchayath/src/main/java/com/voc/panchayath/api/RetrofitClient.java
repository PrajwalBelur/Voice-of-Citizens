package com.voc.panchayath.api;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String TAG = "HttpLog";

    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(
                message -> Log.d(TAG, message)
        );

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

    public static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(ApiConstants.READ_TIME_OUT, TimeUnit.MINUTES)
                .writeTimeout(ApiConstants.WRITE_TIME_OUT, TimeUnit.MINUTES)
                .addInterceptor(getHttpLoggingInterceptor())
                .build();
    }

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .baseUrl(ApiConstants.BASE_URL)
                .build();
    }

    public static <S> S getApiInterface(Class<S> clazz) {
        return getRetrofit().create(clazz);
    }
}
