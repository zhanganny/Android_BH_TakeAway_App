package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.Local;
import com.example.mybhtakeawayapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class UserActivityOderDetail extends Activity {
    private TextView order_id;
    private TextView order_store;
    private TextView order_address;
    private RecyclerView order_ed_list;
    private TextView order_total_money;
    private TextView order_comment;
    // todo 获取id
    private String orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_order_detail);
        order_id = findViewById(R.id.order_id);
        order_store = findViewById(R.id.order_store);
        order_address = findViewById(R.id.order_address);
        order_total_money = findViewById(R.id.order_total_money);
        order_comment = findViewById(R.id.order_comment);
        order_ed_list = findViewById(R.id.order_ed_list);

        String orderUrl = Local.getLocalIp() + "indent/getInfo/" + orderId;
        RequestQueue requestQueue = Volley.newRequestQueue(UserActivityOderDetail.this);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, orderUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        order_id.setText(orderId);
                        order_store.setText(jsonObject.getString("store"));
                        order_address.setText(jsonObject.getString("address"));
                        order_total_money.setText(jsonObject.getString("cost"));
                        order_comment.setText(jsonObject.getString("o_comment"));
                    } else {
                        Toast.makeText(UserActivityOderDetail.this, "加载买家数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(UserActivityOderDetail.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        // todo
        // 前端需要从后端获取这些信息



//        pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(UserActivityShoppingCart.this, UserActivityOrderEnsure.class);
//                startActivity(intent);
//            }
//        });
    }
}
