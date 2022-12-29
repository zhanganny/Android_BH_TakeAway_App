package com.example.mybhtakeawayapp.saler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybhtakeawayapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SellerActivityHome extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private SellerActivityHomeFragment homeFragment1;
    private SellerOrderItemFragment orderFragment2;
    private SalerInfo infoFragment3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.saler_constrain);

        homeFragment1 = new SellerActivityHomeFragment();
        orderFragment2 = new SellerOrderItemFragment();
        infoFragment3 = new SalerInfo();
        frameLayout = findViewById(R.id.saler_home_fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.saler_home:
                    switchFragment(homeFragment1);
                    return true;
                case R.id.saler_order:
                    switchFragment(orderFragment2);
                    return true;
                case R.id.saler_info:
                    switchFragment(infoFragment3);
                    return true;
            }
            return false;
        }
    };

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.saler_home_fragment, fragment).commitNow();
    }
}
