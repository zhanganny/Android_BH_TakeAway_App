package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.mybhtakeawayapp.FeedBackActivity;
import com.example.mybhtakeawayapp.R;

public class UserActivityOrderStatus extends Activity {
    private Button back;
    private Button tuikuan;
    private Button tousu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_order_status);
        back = findViewById(R.id.back);
        tuikuan = findViewById(R.id.tuikuan);
        tousu = findViewById(R.id.tousu);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityOrderStatus.this, UserActivityOderDetail.class);
                startActivity(intent);
                finish();
            }
        });

        tuikuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tousu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityOrderStatus.this, FeedBackActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
