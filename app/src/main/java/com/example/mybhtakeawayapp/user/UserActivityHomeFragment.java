package com.example.mybhtakeawayapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.user.UserActivityStoreIndex;

import java.util.ArrayList;
import java.util.List;

public class UserActivityHomeFragment extends Fragment {
    private ImageView sys_img;

    private RecyclerView user_home_store_list;

    public class News {
        public String order_store_image; // 商家照片_no
        public String order_name; //订单号
        public News(String store, String name) {
            this.order_name = name;
            this.order_store_image = store;
        }

    }
    RecyclerView mRecyclerView1;
    MyAdapter1 mMyAdapter1 ;
    List<News> mNewsList1 = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class MyAdapter1 extends RecyclerView.Adapter<MyViewHoder> {
        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.user_home_store_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList1.get(position);
            holder.order_name.setText(news.order_name);
            // todo 处理商店照片
            //holder.order_store_image.setImageIcon();



            holder.go_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), UserActivityStoreIndex.class);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mNewsList1.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView order_name;
        ImageView order_store_image;
        Button go_in;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            order_name = itemView.findViewById(R.id.store_name);
            order_store_image = itemView.findViewById(R.id.store_image);
            go_in = itemView.findViewById(R.id.go_in);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.user_activity_home ,container, false);
        sys_img = mView.findViewById(R.id.sys_img);

        Drawable drawable = getResources().getDrawable(R.drawable.app_icon_your_company);
        sys_img.setImageDrawable(drawable);

        mRecyclerView1 = mView.findViewById(R.id.user_home_store_list);



        // 构造一些数据  todo
        mNewsList1.add(new News("合一", "合一"));
        mNewsList1.add(new News("合一", "合一"));

        mMyAdapter1 = new MyAdapter1();
        mRecyclerView1.setAdapter(mMyAdapter1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView1.setLayoutManager(layoutManager);

        return mView;
    }




}
