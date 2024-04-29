package com.voc.panchayath.api;

import androidx.annotation.NonNull;

import com.voc.panchayath.model.Complaint;
import com.voc.panchayath.model.Panchayath;
import com.voc.panchayath.model.ResponseWrapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ApiInteractor {

    private static ApiInterface apiInterface = RetrofitClient.getApiInterface(ApiInterface.class);

    public static void login(String loginId, String password,
                             @NonNull ApiResponseListener<ResponseWrapper<Panchayath>> callback) {

        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "login");
        params.put("loginId", loginId);
        params.put("password", password);
        Call<ResponseWrapper<Panchayath>> call = apiInterface.login(params);
        call.enqueue(new ApiCallback<>(callback));
    }

    private static RequestBody _prepareRequestBody(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
    }

    private static MultipartBody.Part _prepareMultipartBody(String fieldName, String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(fieldName, file.getName(), requestBody);
    }

    public static void getComplaintsList(int panchayathRid,
                                         @NonNull ApiResponseListener<ResponseWrapper<List<Complaint>>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "getComplaints");
        params.put("panchayathRid", panchayathRid);

        Call<ResponseWrapper<List<Complaint>>> call = apiInterface.getComplaints(params);
        call.enqueue(new ApiCallback<>(callback));
    }

    public static void markComplaintAsConfirmed(int complaintRid,
                                                List<String> photoPathList,
                                                @NonNull ApiResponseListener<ResponseWrapper<String>> callback) {

        MultipartBody.Part[] photo = new MultipartBody.Part[photoPathList.size()];

        for (int i = 0; i < photoPathList.size(); i++) {
            photo[i] = _prepareMultipartBody("photo[]", photoPathList.get(i));
        }

        Map<String, RequestBody> params = new HashMap<>();
        params.put("cmd", _prepareRequestBody("confirm"));
        params.put("complaintRid", _prepareRequestBody(String.valueOf(complaintRid)));
        Call<ResponseWrapper<String>> call = apiInterface.confirm(photo, params);
        call.enqueue(new ApiCallback<>(callback));
    }

    public static void resetPassword(String email,
                                     @NonNull ApiResponseListener<ResponseWrapper<String>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "resetPanPassword");
        params.put("email", email);

        Call<ResponseWrapper<String>> call = apiInterface.getPassword(params);
        call.enqueue(new ApiCallback<>(callback));
    }


    public static void loadPanchayathDetails(int panchayathRid,
                                             @NonNull ApiResponseListener<ResponseWrapper<Panchayath>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "getPanchayathDetails");
        params.put("panchayathRid", panchayathRid);

        Call<ResponseWrapper<Panchayath>> call = apiInterface.getPanchayathDetails(params);
        call.enqueue(new ApiCallback<>(callback));
    }

    public static void loadCompletedImages(
            int complaintRid,
            @NonNull ApiResponseListener<ResponseWrapper<List<String>>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "getCompletedImages");
        params.put("complaintRid", String.valueOf(complaintRid));
        Call<ResponseWrapper<List<String>>> call = apiInterface.getCompletedImages(params);
        call.enqueue(new ApiCallback<>(callback));
    }
}
