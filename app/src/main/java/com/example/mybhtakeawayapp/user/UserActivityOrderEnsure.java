package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.mybhtakeawayapp.R;

public class UserActivityOrderEnsure extends Activity {
    private Button back;
    private Button pay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_order_ensure);
        pay = findViewById(R.id.pay);
        back = findViewById(R.id.back);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityOrderEnsure.this, UserActivityHome.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityOrderEnsure.this, UserActivityStoreIndex.class);
                startActivity(intent);
            }
        });
    }
}
