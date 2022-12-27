package com.example.mybhtakeawayapp.user;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.mybhtakeawayapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivityHome extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private UserHomeFragment user_homeFragment1;
    private UserOrderFragment user_orderFragment2;
    private UserInfoFragment user_infoFragment3;
    private UserActivityContactsFragment user_contactFragment4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_constrain);
        user_homeFragment1 = new UserHomeFragment();
        user_orderFragment2 = new UserOrderFragment();
        user_infoFragment3 = new UserInfoFragment();
        user_contactFragment4 = new UserActivityContactsFragment();
        frameLayout = findViewById(R.id.home_fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
        System.out.println("aaaaaaaaaaaaaaaaaa");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.user_home:
                    switchFragment(user_homeFragment1);
                    return true;
                case R.id.user_order:
                    switchFragment(user_orderFragment2);
//                    Intent intent = new Intent(UserActivityHome.this, setUserInformation.class);
//                    startActivity(intent);
//                    finish();
                    return true;
                case R.id.user_info:
                    switchFragment(user_infoFragment3);
                    return true;
                case R.id.user_contact:
                    switchFragment(user_contactFragment4);
                    return true;
            }
            return false;
        }
    };

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment, fragment).commitNow();
    }

}
