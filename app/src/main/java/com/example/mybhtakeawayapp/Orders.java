package com.example.mybhtakeawayapp;

import java.time.LocalDateTime;

public class Orders {
    public enum OrderState {
        NOT_PAY,
        PAID,
        NOT_ACCEPT,
        ACCEPTED,
        DELIVERING,
        FINISHED,
        CANCELED
    }

    public int o_id;
    public LocalDateTime o_time;//下单时间
    public LocalDateTime send_time;//配送时间
    public double cost;
    public OrderState state=OrderState.PAID;
    public String address;//送货地址
    public String u_id;
    public String rider;
    public String p_id;
    public String o_comment;//备注
    public int d_id;

    public Orders(int o_id, LocalDateTime o_time, LocalDateTime send_time, double cost, OrderState state, String address, String u_id, String rider, String p_id, String o_comment, int d_id) {
        this.o_id = o_id;
        this.o_time = o_time;
        this.send_time = send_time;
        this.cost = cost;
        this.state = state;
        this.address = address;
        this.u_id = u_id;
        this.rider = rider;
        this.p_id = p_id;
        this.o_comment = o_comment;
        this.d_id = d_id;
    }
}
