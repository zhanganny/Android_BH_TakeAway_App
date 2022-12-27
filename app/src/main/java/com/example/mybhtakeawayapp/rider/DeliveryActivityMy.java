package com.example.mybhtakeawayapp.rider;

import android.annotation.SuppressLint;
import android.app.Activity;
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
    private UserHomeFragment blankFragment;
//    private BlankFragment2 blankFragment2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constrain);
        blankFragment = new UserHomeFragment();
//        blankFragment2 = new BlankFragment2();
        frameLayout = findViewById(R.id.home_fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
//        switchFragment(blankFragment2);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.user_home:
                    switchFragment(blankFragment);
                    return true;
//                case R.id.user_order:
//                    switchFragment(blankFragment2);
//                    return true;
            }
            return false;
        }
    };

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment, fragment).commitNow();
    }
}
