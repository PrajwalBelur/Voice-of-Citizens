package com.voc.api;

import com.voc.model.Citizen;
import com.voc.model.Complaint;
import com.voc.model.Panchayath;
import com.voc.model.RegPageDetails;
import com.voc.model.ResponseWrapper;

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
    @POST("api.php")
    Call<ResponseWrapper<Citizen>> login(
            @FieldMap Map<String, Object> params
    );

    @Multipart
    @POST("api.php")
    Call<ResponseWrapper<String>> register(
            @Part MultipartBody.Part profile,
            @PartMap Map<String, RequestBody> params
    );

    @Multipart
    @POST("api.php")
    Call<ResponseWrapper<String>> submitComplaints(
            @Part MultipartBody.Part[] photo,
            @PartMap Map<String, RequestBody> params
    );

    @GET("api.php")
    Call<ResponseWrapper<List<Complaint>>> getComplaints(
            @QueryMap Map<String, Object> params
    );

    @GET("api.php")
    Call<ResponseWrapper<Panchayath>> getPanchayathDetails(
            @QueryMap Map<String, Object> params
    );

    @FormUrlEncoded
    @POST("api.php")
    Call<ResponseWrapper<String>> deleteProfile(
            @FieldMap Map<String, Object> params
    );

    @FormUrlEncoded
    @POST("api.php")
    Call<ResponseWrapper<String>> getPassword(
            @FieldMap Map<String, Object> params
    );

    @FormUrlEncoded
    @POST("api.php")
    Call<ResponseWrapper<Citizen>> updateProfile(
            @FieldMap Map<String, Object> params
    );

    @FormUrlEncoded
    @POST("api.php")
    Call<ResponseWrapper<String>> confirm(
            @FieldMap Map<String, Object> params
    );


    @GET("api.php")
    Call<ResponseWrapper<RegPageDetails>> getRegPageDetails(
            @QueryMap Map<String, Object> params
    );

    @GET("api.php")
    Call<ResponseWrapper<List<String>>> getCompletedImages(
            @QueryMap Map<String, Object> params
    );
}
