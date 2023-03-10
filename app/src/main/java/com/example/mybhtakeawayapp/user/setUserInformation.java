package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.Local;
import com.example.mybhtakeawayapp.R;

import org.json.JSONException;

public class setUserInformation extends Activity {
    private TextView userName;
    private TextView userContact;
    private TextView userDefaultAddress;
    private TextView userEmail;
    private TextView userCount;
    private TextView userPassword;
    private Button pay;
    private String userId = Local.getUserLoginId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_setting);
        userName = findViewById(R.id.userName);
        userContact = findViewById(R.id.userContact);
        userDefaultAddress = findViewById(R.id.userDefaultAddress);
        userEmail = findViewById(R.id.userEmail);
        userCount = findViewById(R.id.userCount);
        userPassword = findViewById(R.id.userPassword);
        pay = findViewById(R.id.pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String contact = userContact.getText().toString();
                String defaultAddress= userDefaultAddress.getText().toString();
                String email = userEmail.getText().toString();
                String count = userCount.getText().toString();
                String password = userPassword.getText().toString();
                if(name.equals("")||contact.equals("")||defaultAddress.equals("")||
                        email.equals("")||count.equals("")||password.equals("")){
                    Toast.makeText(setUserInformation.this, "???????????????", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        String name1 = URLEncoder.encode(name, "utf-8");
                        String contact1 = URLEncoder.encode(contact, "utf-8");
                        String defaultAddress1 = URLEncoder.encode(defaultAddress, "utf-8");
                        String email1 = URLEncoder.encode(email, "utf-8");
                        String count1 = URLEncoder.encode(count, "utf-8");
                        String password1 = URLEncoder.encode(password, "utf-8");
                        set(name1,contact1,defaultAddress1,email1,count1,password1);
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void set(String name, String contact, String defaultAddress, String email, String count, String password) throws JSONException {
        JSONObject jsonObject=new JSONObject();
        String url= Local.getLocalIp() + "updateInfo/" + userId;
        jsonObject.put("name",name);
        jsonObject.put("contact",contact);
        jsonObject.put("address",defaultAddress);
        jsonObject.put("email",email);
        jsonObject.put("password",password);
        RequestQueue requestQueue= Volley.newRequestQueue(setUserInformation.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    if(state){
                        Toast.makeText(setUserInformation.this, "????????????", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(setUserInformation.this, "????????????", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("??????", volleyError.toString());
                Toast.makeText(setUserInformation.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
