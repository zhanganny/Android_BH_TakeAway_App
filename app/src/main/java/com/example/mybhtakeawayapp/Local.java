package com.example.mybhtakeawayapp;

public class Local {
    static String userLoginId;

    public static String getUserLoginId() {
        return userLoginId;
    }

    public static void setUserLoginId(String userLoginId) {
        Local.userLoginId = userLoginId;
    }
}
