package com.example.mybhtakeawayapp;

public class Local {
    static String userLoginId;
    static String localIp = "http://192.168.1.1:8081/";

    public static String getUserLoginId() {
        return userLoginId;
    }

    public static void setUserLoginId(String userLoginId) {
        Local.userLoginId = userLoginId;
    }

    public static String getLocalIp() {
        return localIp;
    }

    public static void setLocalIp(String localIp) {
        Local.localIp = localIp;
    }
}
