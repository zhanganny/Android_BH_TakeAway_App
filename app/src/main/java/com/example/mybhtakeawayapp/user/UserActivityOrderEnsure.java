package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.Local;
import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.saler.SalerInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserActivityOrderEnsure extends Activity {
    private Button back;
    private Button pay;
    private TextView order_day;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_order_ensure);
        pay = findViewById(R.id.pay);
        back = findViewById(R.id.back);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    commitOrder();
                } catch (JSONException e) {
                    Toast.makeText(UserActivityOrderEnsure.this, "提交订单失败", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(UserActivityOrderEnsure.this, UserActivityHome.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityOrderEnsure.this, UserActivityStoreIndex.class);
                startActivity(intent);
            }
        });
    }

    private void commitOrder() throws JSONException {
        String orderUrl = Local.getLocalIp() + "dish/getTotalCost";
        RequestQueue requestQueue = Volley.newRequestQueue(UserActivityOrderEnsure.this);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        ArrayList<Integer> dishes = UserShoppingData.getDishes();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, orderUrl,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        jsonObject2.put("cost",jsonObject.getDouble("data"));
                    } else {
                        Toast.makeText(UserActivityOrderEnsure.this, "加载价格数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(UserActivityOrderEnsure.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        // todo 获取地址、骑手id、备注
        orderUrl = Local.getLocalIp() + "indent/addIndent";
        jsonObject2.put("address",address);
        jsonObject2.put("uid",Local.getUserLoginId());
        jsonObject2.put("rider",rider);
        jsonObject2.put("pid",UserShoppingData.curPid);
        jsonObject2.put("comment",comment);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, orderUrl,
                jsonObject2, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    // todo 设置提交成功
                    if (state) {
                        saler_name.setText(jsonObject.getString("sellerName"));
                        saler_income.setText(Double.toString(jsonObject.getDouble("income")));
                    } else {
                        Toast.makeText(UserActivityOrderEnsure.this, "提交订单失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(UserActivityOrderEnsure.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        UserShoppingData.clear(UserShoppingData.curPid);
    }
}
