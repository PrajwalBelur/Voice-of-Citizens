package com.voc.api;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final int READ_TIME_OUT = 2;
    public static final int WRITE_TIME_OUT = 2;

    private static final String IP_ADDRESS = "192.168.43.29";
    public static final String BASE_URL = "http://" + IP_ADDRESS + "/voc/api/";
    public static final String IMAGE_URL = "http://" + IP_ADDRESS + "/voc/uploads/";

}
