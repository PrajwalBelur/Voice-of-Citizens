package com.voc.api;

import androidx.annotation.NonNull;

import com.voc.model.Citizen;
import com.voc.model.Complaint;
import com.voc.model.Panchayath;
import com.voc.model.RegPageDetails;
import com.voc.model.ResponseWrapper;

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
                             @NonNull ApiResponseListener<ResponseWrapper<Citizen>> callback) {

        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "login");
        params.put("loginId", loginId);
        params.put("password", password);
        Call<ResponseWrapper<Citizen>> call = apiInterface.login(params);
        call.enqueue(new ApiCallback<>(callback));
    }

    public static void register(int grama, String name, String password, String mobileNo,
                                char gender, String email, String address,
                                String profilePath,
                                @NonNull ApiResponseListener<ResponseWrapper<String>> callback) {

        // file part preparation
        MultipartBody.Part profilePhoto = _prepareMultipartBody("profile", profilePath);

        // other body contents
        Map<String, RequestBody> params = new HashMap<>();
        params.put("cmd", _prepareRequestBody("register"));
        params.put("grama", _prepareRequestBody(String.valueOf(grama)));
        params.put("name", _prepareRequestBody(name));
        params.put("password", _prepareRequestBody(password));
        params.put("address", _prepareRequestBody(address));
        params.put("mobileNo", _prepareRequestBody(mobileNo));
        params.put("email", _prepareRequestBody(email));
        params.put("gender", _prepareRequestBody(String.valueOf(gender)));

        Call<ResponseWrapper<String>> call = apiInterface.register(profilePhoto, params);
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

    public static void submitComplaints(int citizenId, int panchayathId, Complaint.Type compType,
                                        String mobile, List<String> photoPathList, String location,
                                        String landMark, String description,
                                        @NonNull ApiResponseListener<ResponseWrapper<String>> callback) {

        MultipartBody.Part[] photo = new MultipartBody.Part[photoPathList.size()];

        for (int i = 0; i < photoPathList.size(); i++) {
            photo[i] = _prepareMultipartBody("photo[]", photoPathList.get(i));
        }
        // other body contents
        Map<String, RequestBody> params = new HashMap<>();
        params.put("cmd", _prepareRequestBody("saveComplaint"));
        params.put("citizenId", _prepareRequestBody(String.valueOf(citizenId)));
        params.put("panchayathId", _prepareRequestBody(String.valueOf(panchayathId)));
        params.put("compType", _prepareRequestBody(String.valueOf(compType)));
        params.put("mobile", _prepareRequestBody(mobile));
        params.put("location", _prepareRequestBody(location));
        params.put("landMark", _prepareRequestBody(landMark));
        params.put("description", _prepareRequestBody(description));

        Call<ResponseWrapper<String>> call = apiInterface.submitComplaints(photo, params);
        call.enqueue(new ApiCallback<>(callback));
    }

    public static void getComplaintsList(int citizenRid,
                                         @NonNull ApiResponseListener<ResponseWrapper<List<Complaint>>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "getComplaints");
        params.put("citizenId", citizenRid);

        Call<ResponseWrapper<List<Complaint>>> call = apiInterface.getComplaints(params);
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

    public static void deleteProfile(int citizenRid,
                                     @NonNull ApiResponseListener<ResponseWrapper<String>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "deleteProfile");
        params.put("citizenRid", citizenRid);

        Call<ResponseWrapper<String>> call = apiInterface.deleteProfile(params);
        call.enqueue(new ApiCallback<>(callback));
    }

    public static void resetPassword(String email,
                                     @NonNull ApiResponseListener<ResponseWrapper<String>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "resetUserPassword");
        params.put("email", email);

        Call<ResponseWrapper<String>> call = apiInterface.getPassword(params);
        call.enqueue(new ApiCallback<>(callback));
    }

    public static void updateProfile(
            int citizenRid, String name, String password, String phone,
            String email, String address,
            @NonNull ApiResponseListener<ResponseWrapper<Citizen>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "update");
        params.put("citizenRid", citizenRid);
        params.put("name", name);
        params.put("password", password);
        params.put("address", address);
        params.put("mobileNo", phone);
        params.put("email", email);

        Call<ResponseWrapper<Citizen>> call = apiInterface.updateProfile(params);
        call.enqueue(new ApiCallback<>(callback));
    }

    public static void markComplaintAsConfirmed(int complaintRid,
                                                @NonNull ApiResponseListener<ResponseWrapper<String>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "confirm");
        params.put("complaintRid", complaintRid);
        Call<ResponseWrapper<String>> call = apiInterface.confirm(params);
        call.enqueue(new ApiCallback<>(callback));
    }

    public static void loadRegPageDetails(@NonNull ApiResponseListener<ResponseWrapper<RegPageDetails>> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "getRegPageDetails");
        Call<ResponseWrapper<RegPageDetails>> call = apiInterface.getRegPageDetails(params);
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
