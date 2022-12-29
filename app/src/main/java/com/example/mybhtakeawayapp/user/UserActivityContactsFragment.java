package com.example.mybhtakeawayapp.user;

import android.content.Intent;
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

import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.rider.DeliveryActivityOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class UserActivityContactsFragment extends Fragment {

    public UserActivityContactsFragment() {
        // Required empty public constructor
    }

    public class News {
        public String name; // 用户名
        public String phone; // 手机号

        public News(String store, String name) {
            this.phone = name;
            this.name = store;
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
            View view = View.inflate(getActivity(), R.layout.contact_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList1.get(position);
            holder.order_name.setText(news.name);
            holder.order_phone.setText(news.phone);
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
        TextView order_phone;


        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            order_name = itemView.findViewById(R.id.contact_name);
            order_phone = itemView.findViewById(R.id.contact_phone);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.user_activity_contacts ,container, false);
        mRecyclerView1 = mView.findViewById(R.id.user_contact_list);

        // 构造一些数据  todo
        mNewsList1.add(new UserActivityContactsFragment.News("用户1", "1234567"));
        mNewsList1.add(new UserActivityContactsFragment.News("用户3", "1222222"));


        mMyAdapter1 = new MyAdapter1();
        mRecyclerView1.setAdapter(mMyAdapter1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView1.setLayoutManager(layoutManager);
        return mView;
    }
}

