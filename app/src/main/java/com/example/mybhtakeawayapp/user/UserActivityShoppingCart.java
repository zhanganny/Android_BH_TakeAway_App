package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.mybhtakeawayapp.R;

public class UserActivityShoppingCart extends Activity {
    private Button back;
    private Button pay;
    private Button add_more;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_shopping_cart);
        pay = findViewById(R.id.pay);
        add_more = findViewById(R.id.add_more);
        back = findViewById(R.id.back);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityShoppingCart.this, UserActivityOrderEnsure.class);
                startActivity(intent);
            }
        });

        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityShoppingCart.this, UserActivityStoreIndex.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityShoppingCart.this, UserActivityStoreIndex.class);
                startActivity(intent);
            }
        });
    }
}
