package com.example.mybhtakeawayapp.user;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

//        String orderUrl = Local.getLocalIp() + "indent/getInfo/" + orderId;
        String orderUrl = "http://192.168.1.5:8081/indent/getInfo/" + orderId;
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
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityOderDetail.this, UserActivityOrderStatus.class);
                startActivity(intent);
            }
        });

        // todo
        // 后端要删掉此订单
        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView1 = findViewById(R.id.user_order_detail);
        // 构造一些数据  todo
        mNewsList1.add(new News("麻婆豆腐", "X1"));
        mNewsList1.add(new News("鱼香肉丝", "X2"));
        mMyAdapter1 = new MyAdapter1();
        mRecyclerView1.setAdapter(mMyAdapter1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(UserActivityOderDetail.this);
        mRecyclerView1.setLayoutManager(layoutManager);

    }

    public class News {
        public String order_name; //订单号
        public String order_num;// 订单日期
        public News(String name, String num) {
            this.order_name = name;
            this.order_num = num;
        }
    }
    RecyclerView mRecyclerView1;
    MyAdapter1 mMyAdapter1 ;
    List<News> mNewsList1 = new ArrayList<>();

    class MyAdapter1 extends RecyclerView.Adapter<MyViewHoder> {
        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(UserActivityOderDetail.this, R.layout.good_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList1.get(position);
            holder.order_name.setText(news.order_name);
            holder.order_num.setText(news.order_num);
        }
        @Override
        public int getItemCount() {
            return mNewsList1.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView order_name;
        TextView order_num;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            order_name = itemView.findViewById(R.id.order_name);
            order_num = itemView.findViewById(R.id.order_statement);
        }
    }


}
