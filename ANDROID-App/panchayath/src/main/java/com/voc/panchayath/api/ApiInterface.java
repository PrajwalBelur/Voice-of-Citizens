package com.voc.panchayath.api;

import com.voc.panchayath.model.Complaint;
import com.voc.panchayath.model.Panchayath;
import com.voc.panchayath.model.ResponseWrapper;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("pan_api.php")
    Call<ResponseWrapper<Panchayath>> login(
            @FieldMap Map<String, Object> params
    );

    @GET("pan_api.php")
    Call<ResponseWrapper<List<Complaint>>> getComplaints(
            @QueryMap Map<String, Object> params
    );

    @FormUrlEncoded
    @POST("pan_api.php")
    Call<ResponseWrapper<String>> getPassword(
            @FieldMap Map<String, Object> params
    );

    @GET("api.php")
    Call<ResponseWrapper<Panchayath>> getPanchayathDetails(
            @QueryMap Map<String, Object> params
    );

    @Multipart
    @POST("pan_api.php")
    Call<ResponseWrapper<String>> confirm(
            @Part MultipartBody.Part[] photo,
            @PartMap Map<String, RequestBody> params
    );

    @GET("api.php")
    Call<ResponseWrapper<List<String>>> getCompletedImages(
            @QueryMap Map<String, Object> params
    );
}
