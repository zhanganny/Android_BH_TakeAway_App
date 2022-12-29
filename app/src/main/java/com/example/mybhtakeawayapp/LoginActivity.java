package com.example.mybhtakeawayapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.admin.AdministratorHomeActivity;
import com.example.mybhtakeawayapp.rider.DeliveryActivityMy;
import com.example.mybhtakeawayapp.saler.SellerActivityHome;
import com.example.mybhtakeawayapp.user.UserActivityHome;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {
    private EditText lg_username;
    private EditText lg_password;
    private Button lg_login;
    private TextView lg_register;

    private CheckBox loginByAdmin;
    private CheckBox loginByRider;
    private CheckBox loginByStore;
    private CheckBox loginByUser;

    private String localIP = "http://192.168.110.79:8081/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        lg_username = findViewById(R.id.lg_username);
        lg_password = findViewById(R.id.lg_password);
        lg_login = findViewById(R.id.lg_login);
        lg_register = findViewById(R.id.lg_register);

        loginByAdmin = findViewById(R.id.loginByAdmin);
        loginByRider = findViewById(R.id.loginByRider);
        loginByStore = findViewById(R.id.loginByStore);
        loginByUser = findViewById(R.id.loginByUser);

        lg_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = lg_username.getText().toString();
                String password = lg_password.getText().toString();

                boolean isAdmin = loginByAdmin.isChecked();
                boolean isRider = loginByRider.isChecked();
                boolean isStore = loginByStore.isChecked();
                boolean isUser = loginByUser.isChecked();


                if (isAdmin) {
                    Intent intent = new Intent(LoginActivity.this, AdministratorHomeActivity.class);
                    startActivity(intent);
                    finish();
                } else if (isUser) {
                    Intent intent = new Intent(LoginActivity.this, UserActivityHome.class);
                    startActivity(intent);
                    finish();
                } else if (isStore) {
                    Intent intent = new Intent(LoginActivity.this, SellerActivityHome.class);
                    startActivity(intent);
                    finish();
                } else if (isRider) {
                    Intent intent = new Intent(LoginActivity.this, DeliveryActivityMy.class);
                    startActivity(intent);
                    finish();
                }
//                JSONObject jsonObject = new JSONObject();
//                String url = localIP+"user/login/" + username + "/" + password;
//                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        try {
//                            boolean state = jsonObject.getBoolean("state");
//                            state = true;
//                            String msg = jsonObject.getString("msg");
//                            System.out.println(state);
//                            Log.d("msg", msg);
//                            if (state) {
//                                if (isAdmin) {
//                                    Intent intent = new Intent(LoginActivity.this, UserActivityHome.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else if (isUser) {
//                                    Intent intent = new Intent(LoginActivity.this, AdministratorHomeActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else if (isStore) {
//                                    Intent intent = new Intent(LoginActivity.this, DeliveryActivityMy.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else if (isRider) {
//                                    return;
//                                }
//                            } else {
//                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Log.d("错误", volleyError.toString());
//                        Toast.makeText(LoginActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                requestQueue.add(jsonObjectRequest);
            }
        });

        lg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
