package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.mybhtakeawayapp.R;

public class UserActivityOrderStatus extends Activity {
    private Button pay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_order_status);
        pay = findViewById(R.id.pay);
//        add_more = findViewById(R.id.add_more);
//        back = findViewById(R.id.back);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityOrderStatus.this, UserActivityOrderStatus.class);
                startActivity(intent);
            }
        });
    }
}
