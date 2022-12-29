package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
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
    private Spinner order_day;
    private Spinner order_time;
    private TextView order_address;
    private Spinner order_man;
    private TextView order_comment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_order_ensure);
        pay = findViewById(R.id.pay);
        back = findViewById(R.id.back);
        order_day=findViewById(R.id.order_day);
        order_time=findViewById(R.id.order_time);
        order_address=findViewById(R.id.order_address);
        order_man=findViewById(R.id.order_man);
        order_comment=findViewById(R.id.order_comment);

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
        //TODO 外卖员order_man的监听
    }

    private void commitOrder() throws JSONException {
        String orderUrl = Local.getInstance().getLocalIp() + "dish/getTotalCost";
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
        orderUrl = Local.getInstance().getLocalIp() + "indent/addIndent";
        jsonObject2.put("address",order_address.getText().toString());
        jsonObject2.put("uid",Local.getInstance().getUserLoginId());
//        jsonObject2.put("rider",rider);
        jsonObject2.put("pid",UserShoppingData.curPid);
        jsonObject2.put("comment",order_comment.getText().toString());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, orderUrl,
                jsonObject2, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    // todo 设置提交成功
                    if (state) {
                        Toast.makeText(UserActivityOrderEnsure.this, "提交订单成功", Toast.LENGTH_SHORT).show();
//                        saler_name.setText(jsonObject.getString("sellerName"));
//                        saler_income.setText(Double.toString(jsonObject.getDouble("income")));
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
