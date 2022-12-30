package com.example.mybhtakeawayapp.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybhtakeawayapp.Local;
import com.example.mybhtakeawayapp.R;

import java.util.ArrayList;
import java.util.List;

public class UserActivityContactsFragment extends Fragment {


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
    MyAdapter1 mMyAdapter1 ;
    List<News> mNewsList1 = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsList1.clear();
        mNewsList1.add(new News("合一", "张三", " ", "182-0000-0000"));
        mNewsList1.add(new News("合一", "李四", " ", "138-3457-3456"));
        mNewsList1.add(new News("合一", "王五", " ", "138-3457-3456"));
        for (int i = 0;i < Local.contacts;i++) {
            mNewsList1.add(new News("合一", "阿七", " ", "136-3557-3126"));
        }
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
            // todo 处理用户照片
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
        View mView = inflater.inflate(R.layout.user_activity_contacts ,container, false);

        mRecyclerView1 = mView.findViewById(R.id.user_contact_list);

        // 构造一些数据  todo

        mMyAdapter1 = new MyAdapter1();
        mRecyclerView1.setAdapter(mMyAdapter1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView1.setLayoutManager(layoutManager);

        return mView;
    }
}