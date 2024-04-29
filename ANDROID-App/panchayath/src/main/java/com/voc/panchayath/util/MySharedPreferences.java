package com.voc.panchayath.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.voc.panchayath.model.Panchayath;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * SharedPreference Utility class
 */
@SuppressWarnings("WeakerAccess")
public final class MySharedPreferences {

    //    public static final String PREF_NAME = BuildConfig.APPLICATION_ID;
    public static final String PREF_NAME = "com.voc.panchayath";

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

    public static void savePanchaythData(Context context, Panchayath citizen) {
        String json = gson.toJson(citizen);
        saveData(context, USER_DATA, json);
    }

    public static Panchayath getPanchayathData(Context context) {
        String json = getData(context, USER_DATA);
        return gson.fromJson(json, Panchayath.class);
    }

    public static int getPanchayathRid(Context context) {
        Panchayath panchayath = getPanchayathData(context);
        return panchayath == null ? 0 : panchayath.getRid();
    }

    public static void logout(Context context) {
        savePanchaythData(context, null);
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
