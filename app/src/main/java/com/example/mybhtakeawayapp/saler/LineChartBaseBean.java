package com.example.mybhtakeawayapp.saler;

public class LineChartBaseBean {
    private String key;
    private float value;
    public LineChartBaseBean(String key, float value) {
        this.key = key;
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
