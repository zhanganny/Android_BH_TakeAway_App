package com.example.mybhtakeawayapp;

import android.os.Build;

import com.example.mybhtakeawayapp.user.UserActivityStoreIndex;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Local {

    static ArrayList<UserActivityStoreIndex.News> dishes;
    private static Local instance;
    String userLoginId;
    String localIp;
    public static HashMap<String,Integer> shopingcar = new HashMap<>();
    public static HashMap<String, UserActivityStoreIndex.News> shopingcaritem = new HashMap<>();
    public static float totalMoney = 0;
    public static void addTotalMoney(float totalMoney) {
        Local.totalMoney = Local.totalMoney+totalMoney;
    }
    public static int countnum=0;
    public static float getTotalMoney() {
        return totalMoney;
    }
    public HashMap<Orders.OrderState,ArrayList<Orders>> orders;
    private Local() {
        userLoginId = "0";
        localIp = "http://192.168.1.1:8081/";
        totalMoney=0;
        countnum=0;
        dishes = new ArrayList<>();
        orders = new HashMap<>();
        orders = new HashMap<>();
        int N = 6;
        ArrayList<Orders> tmp = new ArrayList<>();
        for (int i = 1;i<N;i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tmp.add(new Orders(i, LocalDateTime.of(2022,12,30,8,11+i),
                        LocalDateTime.of(2022,12,30,9,30+i),100+i*3, Orders.OrderState.PAID,"学生7公寓303",Integer.toString(i%4+1),Integer.toString(i%3+2),Integer.toString(i%3+1),"",i%3+1));
            }
        }
        orders.put(Orders.OrderState.PAID,tmp);
        tmp = new ArrayList<>();
        for (int i = N;i<2*N;i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tmp.add(new Orders(i, LocalDateTime.of(2022,12,30,8,11+i),
                        LocalDateTime.of(2022,12,30,8,30+i),100+i*3, Orders.OrderState.NOT_ACCEPT,"学生7公寓303",Integer.toString(i%4+1),Integer.toString(i%3+2),Integer.toString(i%3+1),"",i%3+1));
            }
        }
        tmp = new ArrayList<>();
        for (int i = 2*N;i<3*N;i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tmp.add(new Orders(i, LocalDateTime.of(2022,12,30,8,11+i),
                        LocalDateTime.of(2022,12,30,8,30+i),100+i*3, Orders.OrderState.ACCEPTED,"学生7公寓303",Integer.toString(i%4+1),Integer.toString(i%3+2),Integer.toString(i%3+1),"",i%3+1));
            }
        }
        orders.put(Orders.OrderState.ACCEPTED,tmp);
        tmp = new ArrayList<>();
        for (int i = 3*N;i<4*N;i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tmp.add(new Orders(i, LocalDateTime.of(2022,12,30,8,11+i),
                        LocalDateTime.of(2022,12,30,8,30+i),100+i*3, Orders.OrderState.DELIVERING,"学生7公寓303",Integer.toString(i%4+1),Integer.toString(i%3+2),Integer.toString(i%3+1),"",i%3+1));
            }
        }
        orders.put(Orders.OrderState.DELIVERING,tmp);
        tmp = new ArrayList<>();
        for (int i = 4*N;i<5*N;i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tmp.add(new Orders(i, LocalDateTime.of(2022,12,30,8,11+i),
                        LocalDateTime.of(2022,12,30,8,30+i),100+i*3, Orders.OrderState.FINISHED,"学生7公寓303",Integer.toString(i%4+1),Integer.toString(i%3+2),Integer.toString(i%3+1),"",i%3+1));
            }
        }
        orders.put(Orders.OrderState.FINISHED,tmp);
    }

    public static Local getInstance() {
        if (instance == null)
            instance = new Local();
        return instance;
    }


    public   void addTotalMoney(int totalMoney) {
        Local.getInstance().totalMoney = Local.getInstance().totalMoney+totalMoney;
    }

    public   void addOneCountnum() {
        Local.getInstance().countnum = Local.getInstance().countnum+1;
    }

    public   String getUserLoginId() {
        return userLoginId;
    }

    public   String getLocalIp() {
        return localIp;
    }

    public   int getCountnum() {
        return countnum;
    }

    public   void setUserLoginId(String userLoginId) {
        Local.getInstance().userLoginId = userLoginId;
    }
}
