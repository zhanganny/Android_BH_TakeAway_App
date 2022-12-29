package com.example.mybhtakeawayapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.user.UserActivityHome;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity {
    private String localIP = "http://192.168.110.79:8081/";
    private EditText re_username;
    private EditText re_password;
    private EditText re_affirm;
    private CheckBox registerByRider;
    private CheckBox registerByStore;
    private CheckBox registerByUser;
    private Button re_register;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);
        re_username = findViewById(R.id.re_username);
        re_password = findViewById(R.id.re_password);
        re_affirm = findViewById(R.id.re_affirm);
        registerByRider = findViewById(R.id.registerByRider);
        registerByStore = findViewById(R.id.registerByStore);
        registerByUser = findViewById(R.id.registerByUser);
        re_register = findViewById(R.id.re_register);

        re_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = re_username.getText().toString();
                String password = re_password.getText().toString();
                String affirm = re_affirm.getText().toString();
                boolean isRider = registerByRider.isChecked();
                boolean isStore = registerByStore.isChecked();
                boolean isUser = registerByUser.isChecked();
                if (username.equals("") || password.equals("") || re_affirm.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请完整填写信息", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(re_affirm)) {
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    String url;
                    if (isRider) {
                        url = localIP + "rider/register/" + username + "/" + password;//TODO 增加id参数
                    } else if (isStore) {
                        url = localIP + "provider/register/" + username + "/" + password;
                    } else {
                        url = localIP + "user/register/" + username + "/" + password;
                    }
                    RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                boolean state = jsonObject.getBoolean("state");
                                String msg = jsonObject.getString("msg");
                                System.out.println(state);

                                if (state) {
                                    if (isUser) {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (isRider) {
                                        return;
                                    } else {
                                        return;
                                    }
                                } else {
                                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("错误", volleyError.toString());
                            Toast.makeText(RegisterActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }
            }
        });
    }
}
