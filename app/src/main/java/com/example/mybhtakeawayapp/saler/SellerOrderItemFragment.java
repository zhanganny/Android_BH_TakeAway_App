package com.example.mybhtakeawayapp.saler;

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
import com.example.mybhtakeawayapp.Orders;
import com.example.mybhtakeawayapp.rider.DeliveryActivityOrderFragment;
import com.example.mybhtakeawayapp.saler.SellerOrderItemFragment;
import com.example.mybhtakeawayapp.R;

import java.util.ArrayList;
import java.util.List;

public class SellerOrderItemFragment extends Fragment {
    public SellerOrderItemFragment() {
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
    SellerOrderItemFragment.MyAdapter1 mMyAdapter1 ;
    SellerOrderItemFragment.MyAdapter2 mMyAdapter2 ;
    SellerOrderItemFragment.MyAdapter3 mMyAdapter3 ;
    List<SellerOrderItemFragment.News> mNewsList1 = new ArrayList<>();
    List<SellerOrderItemFragment.News> mNewsList2 = new ArrayList<>();
    List<SellerOrderItemFragment.News> mNewsList3 = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class MyAdapter1 extends RecyclerView.Adapter<SellerOrderItemFragment.MyViewHoder> {
        @NonNull
        @Override
        public SellerOrderItemFragment.MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.order_item, null);
            SellerOrderItemFragment.MyViewHoder myViewHoder = new SellerOrderItemFragment.MyViewHoder(view);
            return myViewHoder;
        }
        @Override
        public void onBindViewHolder(@NonNull SellerOrderItemFragment.MyViewHoder holder, int position) {
            SellerOrderItemFragment.News news = mNewsList1.get(position);
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

    class MyAdapter2 extends RecyclerView.Adapter<SellerOrderItemFragment.MyViewHoder2> {
        @NonNull
        @Override
        public SellerOrderItemFragment.MyViewHoder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.order_item, null);
            SellerOrderItemFragment.MyViewHoder2 myViewHoder2 = new SellerOrderItemFragment.MyViewHoder2(view);
            return myViewHoder2;
        }
        @Override
        public void onBindViewHolder(@NonNull SellerOrderItemFragment.MyViewHoder2 holder, int position) {
            SellerOrderItemFragment.News news = mNewsList2.get(position);
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


    class MyAdapter3 extends RecyclerView.Adapter<SellerOrderItemFragment.MyViewHoder3> {
        @NonNull
        @Override
        public SellerOrderItemFragment.MyViewHoder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.order_item, null);
            SellerOrderItemFragment.MyViewHoder3 myViewHoder3 = new SellerOrderItemFragment.MyViewHoder3(view);
            return myViewHoder3;
        }
        @Override
        public void onBindViewHolder(@NonNull SellerOrderItemFragment.MyViewHoder3 holder, int position) {
            SellerOrderItemFragment.News news = mNewsList3.get(position);
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


        ArrayList<Orders> tmp = Local.getInstance().orders.get(Orders.OrderState.PAID);
        for (Orders order:tmp) {
            mNewsList1.add(new SellerOrderItemFragment.News(Integer.toString(order.d_id),Integer.toString(order.o_id),order.o_time.toString(),"待接单"));
        }
        tmp = Local.getInstance().orders.get(Orders.OrderState.NOT_ACCEPT);
        for (Orders order:tmp) {
            mNewsList2.add(new SellerOrderItemFragment.News(Integer.toString(order.d_id),Integer.toString(order.o_id),order.o_time.toString(),"已接单"));
        }
        tmp = Local.getInstance().orders.get(Orders.OrderState.FINISHED);
        for (Orders order:tmp) {
            mNewsList3.add(new SellerOrderItemFragment.News(Integer.toString(order.d_id),Integer.toString(order.o_id),order.o_time.toString(),"已接单"));
        }


        mMyAdapter1 = new SellerOrderItemFragment.MyAdapter1();
        mRecyclerView1.setAdapter(mMyAdapter1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView1.setLayoutManager(layoutManager);

        mMyAdapter2 = new SellerOrderItemFragment.MyAdapter2();
        mRecyclerView2.setAdapter(mMyAdapter2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        mRecyclerView2.setLayoutManager(layoutManager2);

        mMyAdapter3 = new SellerOrderItemFragment.MyAdapter3();
        mRecyclerView3.setAdapter(mMyAdapter3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
        mRecyclerView3.setLayoutManager(layoutManager3);
        return mView;
    }
}
