package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybhtakeawayapp.R;

public class UserActivityOderDetail extends Activity {
    private TextView order_id;
    private TextView order_store;
    private TextView order_address;
    private RecyclerView order_ed_list;
    private TextView order_total_money;
    private TextView order_comment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_order_detail);
        order_id = findViewById(R.id.order_id);
        order_store = findViewById(R.id.order_store);
        order_address = findViewById(R.id.order_ed_list);
        order_total_money = findViewById(R.id.order_total_money);
        order_comment = findViewById(R.id.order_comment);

        // todo
        // 前端需要从后端获取这些信息



//        pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(UserActivityShoppingCart.this, UserActivityOrderEnsure.class);
//                startActivity(intent);
//            }
//        });
    }
}
