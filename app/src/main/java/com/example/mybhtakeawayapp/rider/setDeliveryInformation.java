package com.example.mybhtakeawayapp.rider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.Local;
import com.example.mybhtakeawayapp.LoginActivity;
import com.example.mybhtakeawayapp.user.UserActivityHome;

import org.json.JSONException;
import org.json.JSONObject;


public class setDeliveryInformation extends Activity {
    private String localIP = Local.getInstance().getLocalIp();
    private int riderId = 19375203;
    private TextView riderUserName;
    private TextView riderContact;
    private TextView riderAccountName;
    private TextView riderPassword;
    private TextView riderRealName;
    private TextView riderStuId;
    private TextView riderSchool;
    private Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面首先获取骑手信息
        JSONObject jsonObject = new JSONObject();
        String url = localIP+"rider/getInfo/" + riderId;
        RequestQueue requestQueue = Volley.newRequestQueue(setDeliveryInformation.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        String userName = jsonObject.getString("userName");
                        String contact = jsonObject.getString("contact");
                        String accountName = jsonObject.getString("accountName");
                        String password = jsonObject.getString("password");
                        String realName = jsonObject.getString("realName");
                        String stuId = jsonObject.getString("stuId");
                        String school = jsonObject.getString("school");
                        riderUserName.setText(userName);
                        riderContact.setText(contact);
                        riderAccountName.setText(accountName);
                        riderPassword.setText(password);
                        riderRealName.setText(realName);
                        riderStuId.setText(stuId);
                        riderSchool.setText(school);
                    } else {
                        Toast.makeText(setDeliveryInformation.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(setDeliveryInformation.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改信息
                String userName = riderUserName.getText().toString();
                String contact = riderContact.getText().toString();
                String accountName = riderAccountName.getText().toString();
                String password = riderPassword.getText().toString();
                String realName = riderRealName.getText().toString();
                String stuId = riderStuId.getText().toString();
                String school = riderSchool.getText().toString();

                JSONObject jsonObject = new JSONObject();
                String url = localIP+"rider/changeInfo/" + userName + "/" + contact + "/" + accountName
                        + "/" + password + "/" + realName + "/" + stuId + "/" + school;
                RequestQueue requestQueue = Volley.newRequestQueue(setDeliveryInformation.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            boolean state = jsonObject.getBoolean("state");
                            String msg = jsonObject.getString("msg");
                            System.out.println(state);
                            Log.d("msg", msg);
                            if (state) {
                                Toast.makeText(setDeliveryInformation.this, "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(setDeliveryInformation.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("错误", volleyError.toString());
                        Toast.makeText(setDeliveryInformation.this, "网络失败", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });

    }
}
