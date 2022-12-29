package com.example.mybhtakeawayapp;

import android.os.Build;

import com.example.mybhtakeawayapp.user.UserActivityStoreIndex;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Local {
    static String userLoginId;
    static int totalMoney=0;
    static int countnum=0;
    static ArrayList<UserActivityStoreIndex.News> dishes;
    static HashMap<Orders.OrderState,ArrayList<Orders>> orders = new HashMap<Orders.OrderState, ArrayList<Orders>>(){
        {
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
    };

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

    public static int getCountnum() {
        return countnum;
    }

    public static void setUserLoginId(String userLoginId) {
        Local.userLoginId = userLoginId;
    }
}
