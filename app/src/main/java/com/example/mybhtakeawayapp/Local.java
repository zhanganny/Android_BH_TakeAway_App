package com.example.mybhtakeawayapp;

import com.example.mybhtakeawayapp.user.UserActivityStoreIndex;

import java.util.ArrayList;

public class Local {
    static String userLoginId;
    static String localIp = "http://192.168.1.1:8081/";
    static int totalMoney=0;
    static int countnum=0;
    static ArrayList<UserActivityStoreIndex.News> dishes;

    public static void addTotalMoney(int totalMoney) {
        Local.totalMoney = Local.totalMoney+totalMoney;
    }

    public static void addOneCountnum() {
        Local.countnum = Local.countnum+1;
    }

    public static String getUserLoginId() {
        return userLoginId;
    }

    public static int getTotalMoney() {
        return totalMoney;
    }

    public static String getLocalIp() {
        return localIp;
    }

    public static int getCountnum() {
        return countnum;
    }

    public static void setUserLoginId(String userLoginId) {
        Local.userLoginId = userLoginId;
    }
}
