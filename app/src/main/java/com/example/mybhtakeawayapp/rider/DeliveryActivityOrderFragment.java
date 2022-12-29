package com.example.mybhtakeawayapp.rider;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.example.mybhtakeawayapp.admin.AdministratorHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeliveryActivityOrderFragment extends Fragment {
    // todo 获取食堂id
    private int districtId;


    public DeliveryActivityOrderFragment() {
        // Required empty public constructor
    }


    public class News {
        public String order_store_image; // 商家照片_no
        public String order_name; //订单号
        public String order_time;// 订单日期
        public String order_statement; //订单状态
        public News(String store, String name, String time, String status) {
            this.order_name = name;
            this.order_statement = status;
            this.order_time = time;
            this.order_store_image = store;
        }

    }
    RecyclerView mRecyclerView1;
    RecyclerView mRecyclerView2;
    RecyclerView mRecyclerView3;
    MyAdapter1 mMyAdapter1 ;
    MyAdapter2 mMyAdapter2 ;
    MyAdapter3 mMyAdapter3 ;
    List<News> mNewsList1 = new ArrayList<>();
    List<News> mNewsList2 = new ArrayList<>();
    List<News> mNewsList3 = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class MyAdapter1 extends RecyclerView.Adapter<MyViewHoder> {
        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.order_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList1.get(position);
            holder.order_name.setText(news.order_name);
            holder.order_status.setText(news.order_statement);
            holder.order_time.setText(news.order_time);
            // todo 处理商店照片
            //holder.order_store_image.setImageIcon();
        }

        @Override
        public int getItemCount() {
            return mNewsList1.size();
        }
    }

    class MyAdapter2 extends RecyclerView.Adapter<MyViewHoder> {
        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.order_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList1.get(position);
            holder.order_name.setText(news.order_name);
            holder.order_status.setText(news.order_statement);
            holder.order_time.setText(news.order_time);
            // todo 处理商店照片
            //holder.order_store_image.setImageIcon();
        }

        @Override
        public int getItemCount() {
            return mNewsList1.size();
        }
    }


    class MyAdapter3 extends RecyclerView.Adapter<MyViewHoder> {
        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.order_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList1.get(position);
            holder.order_name.setText(news.order_name);
            holder.order_status.setText(news.order_statement);
            holder.order_time.setText(news.order_time);
            // todo 处理商店照片
            //holder.order_store_image.setImageIcon();
        }

        @Override
        public int getItemCount() {
            return mNewsList1.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView order_name;
        TextView order_time;
        TextView order_status;
        ImageView order_store_image;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            order_name = itemView.findViewById(R.id.order_name);
            order_time = itemView.findViewById(R.id.order_time);
            order_status = itemView.findViewById(R.id.order_statement);
            order_store_image = itemView.findViewById(R.id.order_store);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.delivery_activity_order ,container, false);

        mRecyclerView1 = mView.findViewById(R.id.order_ed_list);
        mRecyclerView2 = mView.findViewById(R.id.order_ed_list2);
        mRecyclerView3 = mView.findViewById(R.id.order_ed_list3);

        // 构造一些数据  todo
        int N = 12;
        for (int i = 1;i<=N;i++) {
            mNewsList3.add(new News(Integer.toString(i%4+1), Integer.toString(i), "2022-12-30", "已完成"));
        }
        for (int i = N+1;i<=2*N;i++) {
            mNewsList2.add(new News(Integer.toString(i%4+1), Integer.toString(i), "2022-12-30", "已接单"));
        }
        for (int i = 2*N+1;i<=3*N-5;i++) {
            mNewsList1.add(new News(Integer.toString(i%4+1), Integer.toString(i), "2022-10-20", "待接单"));
        }

//        JSONObject jsonObject = new JSONObject();
//        String url = Local.getLocalIp() + "";
//        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                try {
//                    JSONArray list1 = (JSONArray) jsonObject.getJSONArray("NA");
//                    JSONArray list2 = (JSONArray) jsonObject.getJSONArray("AC");
//                    JSONArray list3 = (JSONArray) jsonObject.getJSONArray("FINISH");
//                    for (int i = 0;i< list1.length();i++) {
//                        JSONObject indent = list1.getJSONObject(i);
//                        String status = indent.getString("state");
//                        mNewsList1.add(new News(indent.getString("did"),indent.getString("oid"),indent.getString("time"), status));
//                    }
//                    for (int i = 0;i< list2.length();i++) {
//                        JSONObject indent = list2.getJSONObject(i);
//                        String status = indent.getString("state");
//                        mNewsList2.add(new News(indent.getString("did"),indent.getString("oid"),indent.getString("time"), status));
//                    }
//                    for (int i = 0;i< list3.length();i++) {
//                        JSONObject indent = list3.getJSONObject(i);
//                        String status = indent.getString("state");
//                        mNewsList3.add(new News(indent.getString("did"),indent.getString("oid"),indent.getString("time"), status));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("错误", volleyError.toString());
//                Toast.makeText(requireActivity(), "网络失败", Toast.LENGTH_SHORT).show();
//            }
//        });
//        requestQueue.add(jsonObjectRequest);

        mMyAdapter1 = new MyAdapter1();
        mRecyclerView1.setAdapter(mMyAdapter1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView1.setLayoutManager(layoutManager);

        mMyAdapter2 = new MyAdapter2();
        mRecyclerView2.setAdapter(mMyAdapter2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        mRecyclerView2.setLayoutManager(layoutManager2);

        mMyAdapter3 = new MyAdapter3();
        mRecyclerView3.setAdapter(mMyAdapter3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
        mRecyclerView3.setLayoutManager(layoutManager3);
        return mView;
    }
}