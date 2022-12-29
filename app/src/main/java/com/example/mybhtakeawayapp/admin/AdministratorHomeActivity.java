package com.example.mybhtakeawayapp.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybhtakeawayapp.LoginActivity;
import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.RegisterActivity;
import com.example.mybhtakeawayapp.rider.setDeliveryInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdministratorHomeActivity extends Activity {
    private String localIP = "http://10.0.2.2:8081/";
    private Button addSystemInformation;
    private TextView userNumText;
    private TextView providerNumText;
    private TextView riderNumText;

    public class News {
        // todo
        public String infoToConfirm; // 标题
        public News(String infoToConfirm) {
            this.infoToConfirm = infoToConfirm;
        }
    }

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter ;
    List<News> mNewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_home);
        userNumText = findViewById(R.id.userNum);
        providerNumText = findViewById(R.id.providerNum);
        riderNumText = findViewById(R.id.riderNum);
        JSONObject jsonObject = new JSONObject();
        String userUrl = localIP+"user/getUserNum";
        String providerUrl = localIP+"provider/getProviderNum";
        String riderUrl = localIP+"rider/getRiderNum";
        RequestQueue requestQueue = Volley.newRequestQueue(AdministratorHomeActivity.this);
        System.err.println("g");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, userUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    System.err.println(state);
                    if (state) {
                        int userNum = jsonObject.getInt("data");
                        userNumText.setText(userNum);
                    } else {
                        Toast.makeText(AdministratorHomeActivity.this, "加载买家数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(AdministratorHomeActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, providerUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        int userNum = jsonObject.getInt("data");
                        providerNumText.setText(userNum);
                    } else {
                        Toast.makeText(AdministratorHomeActivity.this, "加载商家数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(AdministratorHomeActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        //
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, riderUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    boolean state = jsonObject.getBoolean("state");
                    String msg = jsonObject.getString("msg");
                    if (state) {
                        int userNum = jsonObject.getInt("data");
                        riderNumText.setText(userNum);
                    } else {
                        Toast.makeText(AdministratorHomeActivity.this, "加载骑手数据失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("错误", volleyError.toString());
                Toast.makeText(AdministratorHomeActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
        //添加系统消息
        addSystemInformation = findViewById(R.id.addSystemInformation);
        addSystemInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 链接recyclerview todo
        mRecyclerView = findViewById(R.id.order_ed_list);
        // 构造一些数据 todo
        mNewsList.add(new News("商家认证"));
        mNewsList.add(new News("骑手认证"));
        mNewsList.add(new News("贫困生认证"));

        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AdministratorHomeActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
    }



    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @Override
        public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(AdministratorHomeActivity.this, R.layout.administrator_information, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            // todo
            holder.infoToConfirm.setText(news.infoToConfirm);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView infoToConfirm;
        Button goToConfirm;
        // todo
        public MyViewHoder(View itemView) {
            super(itemView);
            infoToConfirm = itemView.findViewById(R.id.infoToConfirm);
            goToConfirm = itemView.findViewById(R.id.goToConfirm);
        }
    }
}
