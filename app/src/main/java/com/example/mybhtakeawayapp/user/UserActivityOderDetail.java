package com.example.mybhtakeawayapp.user;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.Local;
import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.saler.LineChartBaseBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserActivityOderDetail extends Activity {
    private TextView order_id;
    private TextView order_store;
    private TextView order_address;
    private RecyclerView order_ed_list;
    private TextView order_total_money;
    private TextView order_comment;

    private Button cancel_order;
    private Button pay;
    private Button back;

    // todo 获取id
    private String orderId;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_order_detail);
        order_id = findViewById(R.id.order_id);
        order_store = findViewById(R.id.order_store);
        order_address = findViewById(R.id.order_address);
        order_total_money = findViewById(R.id.order_total_money);
        order_comment = findViewById(R.id.order_comment);
        order_ed_list = findViewById(R.id.user_order_detail);


        cancel_order = findViewById(R.id.cancel_order);
        pay = findViewById(R.id.pay);
        back = findViewById(R.id.back);
        mRecyclerView = findViewById(R.id.user_order_detail);

        String orderUrl = Local.getLocalIp() + "indent/getInfo/" + orderId;
//        String orderUrl = "indent/getInfo/" + orderId;
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
                        JSONArray dishes = (JSONArray) jsonObject.getJSONArray("dishes");
                        for (int i = 0; i < dishes.length(); i++) {
                            JSONObject dish = dishes.getJSONObject(i);
                            mNewsList.add(new News(dish.getString("name"),Integer.toString(dish.getInt("sum"))));
                        }
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
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityOderDetail.this, UserActivityOrderStatus.class);
                startActivity(intent);
                finish();
            }
        });

        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderUrl = "indent/cancel/" + orderId;
                RequestQueue requestQueue = Volley.newRequestQueue(UserActivityOderDetail.this);
                JSONObject jsonObject = new JSONObject();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, orderUrl, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            boolean state = jsonObject.getBoolean("state");
                            String msg = jsonObject.getString("msg");
                            if (state) {
                                Toast.makeText(UserActivityOderDetail.this, "取消成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UserActivityOderDetail.this, "取消失败", Toast.LENGTH_SHORT).show();
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
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(UserActivityOderDetail.this);
        mRecyclerView.setLayoutManager(layoutManager);
    }


    public class News {
        public String name; // 标题
        public String num; //内容
        public News(String  name, String num) {
            this.name = name;
            this.num = num;
        }
    }

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter ;
    List<News> mNewsList = new ArrayList<>();

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(UserActivityOderDetail.this, R.layout.good_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            holder.mTitleTv.setText(news.name);
            holder.mTitleContent.setText("X" + news.num);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitleTv;
        TextView mTitleContent;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.payment_good_ame);
            mTitleContent = itemView.findViewById(R.id.payment_good_number);
        }
    }
}
