package com.voc.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.voc.BuildConfig;
import com.voc.model.Citizen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * SharedPreference Utility class
 */
@SuppressWarnings("WeakerAccess")
public final class MySharedPreferences {

    public static final String PREF_NAME = BuildConfig.APPLICATION_ID;

    private static final String USER_DATA = "user_data";
    private static final String IMAGES_LIST_KEY = "images_list_key";

    private static SharedPreferences sharedPreferences = null;
    private static Gson gson = new Gson();

    private static SharedPreferences getSharedPref(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }

        return sharedPreferences;
    }

    @SuppressLint("ApplySharedPref")
    private static void saveData(Context context, String key, String value) {
        getSharedPref(context).edit().putString(key, value).commit();
    }

    private static void saveData(Context context, String key, int value) {
        saveData(context, key, String.valueOf(value));
    }

    private static void saveData(Context context, String key, boolean value) {
        saveData(context, key, String.valueOf(value));
    }

    private static String getData(Context context, String key) {
        return getSharedPref(context).getString(key, "");
    }

    public static void saveCitizenData(Context context, Citizen citizen) {
        String json = gson.toJson(citizen);
        saveData(context, USER_DATA, json);
    }

    public static Citizen getCitizenData(Context context) {
        String json = getData(context, USER_DATA);
        return gson.fromJson(json, Citizen.class);
    }

    public static int getCitizenRid(Context context) {
        Citizen citizen = getCitizenData(context);
        return citizen == null ? 0 : citizen.getRid();
    }

    public static void logout(Context context) {
        saveCitizenData(context, null);
        saveImages(context, Collections.emptyList());
    }

    public static <T> void saveData(Context context, T body, String key) {
        Gson gson = new Gson();
        String data = gson.toJson(body);
        saveData(context, key, data);
    }

    public static void saveImages(Context context, List<String> picturesPathList) {
        saveData(context, picturesPathList, IMAGES_LIST_KEY);
    }

    public static List<String> getImagesList(Context context) {
        String data = getData(context, IMAGES_LIST_KEY);
        if (TextUtils.isEmpty(data)) {
            return Collections.emptyList();
        } else {
            String[] imagesArray = new Gson().fromJson(data, String[].class);
            return Arrays.asList(imagesArray);
        }
    }
}
