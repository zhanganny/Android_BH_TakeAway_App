package com.example.mybhtakeawayapp.rider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybhtakeawayapp.Local;
import com.example.mybhtakeawayapp.Orders;
import com.example.mybhtakeawayapp.R;

import java.util.ArrayList;
import java.util.List;

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

    class MyAdapter2 extends RecyclerView.Adapter<MyViewHoder2> {
        @NonNull
        @Override
        public MyViewHoder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.order_item, null);
            MyViewHoder2 myViewHoder2 = new MyViewHoder2(view);
            return myViewHoder2;
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder2 holder, int position) {
            News news = mNewsList2.get(position);
            holder.order_name.setText(news.order_name);
            holder.order_status.setText(news.order_statement);
            holder.order_time.setText(news.order_time);
            // todo 处理商店照片
            //holder.order_store_image.setImageIcon();
        }

        @Override
        public int getItemCount() {
            return mNewsList2.size();
        }
    }


    class MyAdapter3 extends RecyclerView.Adapter<MyViewHoder3> {
        @NonNull
        @Override
        public MyViewHoder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.order_item, null);
            MyViewHoder3 myViewHoder3 = new MyViewHoder3(view);
            return myViewHoder3;
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder3 holder, int position) {
            News news = mNewsList3.get(position);
            holder.order_name.setText(news.order_name);
            holder.order_status.setText(news.order_statement);
            holder.order_time.setText(news.order_time);
            // todo 处理商店照片
            //holder.order_store_image.setImageIcon();
        }

        @Override
        public int getItemCount() {
            return mNewsList2.size();
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

    class MyViewHoder2 extends RecyclerView.ViewHolder {
        TextView order_name;
        TextView order_time;
        TextView order_status;
        ImageView order_store_image;

        public MyViewHoder2(@NonNull View itemView) {
            super(itemView);
            order_name = itemView.findViewById(R.id.order_name);
            order_time = itemView.findViewById(R.id.order_time);
            order_status = itemView.findViewById(R.id.order_statement);
            order_store_image = itemView.findViewById(R.id.order_store);
        }
    }

    class MyViewHoder3 extends RecyclerView.ViewHolder {
        TextView order_name;
        TextView order_time;
        TextView order_status;
        ImageView order_store_image;

        public MyViewHoder3(@NonNull View itemView) {
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
        View mView = inflater.inflate(R.layout.seller_activity_order ,container, false);

        mRecyclerView1 = mView.findViewById(R.id.order_ed_list);
        mRecyclerView2 = mView.findViewById(R.id.order_ed_list2);
        mRecyclerView3 = mView.findViewById(R.id.order_ed_list3);

//        // 构造一些数据  todo
//        mNewsList1.add(new News("合一", "合一", "2022-10-20", "待接单"));
//        mNewsList1.add(new News("合一", "合一", "2022-10-20", "待接单"));
//
//        mNewsList2.add(new News("合一", "合一", "2022-10-20", "已接单"));
//
//        mNewsList3.add(new News("合一", "合一", "2022-10-20", "已完成"));
        // 构造一些数据  todo
        ArrayList<Orders> tmp = Local.orders.get(Orders.OrderState.ACCEPTED);
        for (Orders order:tmp) {
            mNewsList1.add(new News(Integer.toString(order.d_id),Integer.toString(order.o_id),order.o_time.toString(),"待接单"));
        }
        tmp = Local.orders.get(Orders.OrderState.DELIVERING);
        for (Orders order:tmp) {
            mNewsList2.add(new News(Integer.toString(order.d_id),Integer.toString(order.o_id),order.o_time.toString(),"已接单"));
        }
        tmp = Local.orders.get(Orders.OrderState.FINISHED);
        for (Orders order:tmp) {
            mNewsList3.add(new News(Integer.toString(order.d_id),Integer.toString(order.o_id),order.o_time.toString(),"已接单"));
        }


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