package com.example.mybhtakeawayapp.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class UserShoppingData {
    public static HashMap<String ,ArrayList<Integer>> dishes = new HashMap<>();
    public static String curPid;

    public static void addDish(String pid, int did) {
        if (!dishes.containsKey(pid)) {
            dishes.put(pid,new ArrayList<>());
        }
        Objects.requireNonNull(dishes.get(pid)).add(did);
    }

    public static void enterProvider(String pid) {
        curPid = pid;
        if (!dishes.containsKey(pid)) {
            dishes.put(pid,new ArrayList<>());
        }
    }

    public static ArrayList<Integer> getDishes() {
        return dishes.get(curPid);
    }

    public static void clear(String pid) {
        if (!dishes.containsKey(pid)) {
            dishes.put(pid,new ArrayList<>());
        }
        else {
            Objects.requireNonNull(dishes.get(pid)).clear();
        }
    }

}