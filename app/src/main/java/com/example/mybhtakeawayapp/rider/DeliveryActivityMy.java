package com.example.mybhtakeawayapp.rider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.user.UserHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DeliveryActivityMy extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private DeliveryActivityMyHomeFragment homeFragment1;
    private DeliveryActivityOrderFragment orderFragment2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_constrain);
        homeFragment1 = new DeliveryActivityMyHomeFragment();
        orderFragment2 = new DeliveryActivityOrderFragment();
        frameLayout = findViewById(R.id.rider_home_fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.rider_home:
                    switchFragment(homeFragment1);
                    return true;
                case R.id.rider_order:
                    switchFragment(orderFragment2);
                    return true;
            }
            return false;
        }
    };

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.rider_home_fragment, fragment).commitNow();
    }
}
