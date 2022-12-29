package com.example.mybhtakeawayapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybhtakeawayapp.user.UserActivityOrderStatus;


public class FeedBackActivity extends Activity {
    private EditText feedback;
    private Button pay;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        feedback = findViewById(R.id.feedback);
        pay = findViewById(R.id.pay);
        back = findViewById(R.id.back);

        // todo
        // 传反馈信息
        String feedbackContent = feedback.getText().toString();

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FeedBackActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedBackActivity.this, UserActivityOrderStatus.class);
                startActivity(intent);
            }
        });
    }
}
