package com.example.mybhtakeawayapp.saler;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybhtakeawayapp.LoginActivity;
import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.RegisterActivity;
import com.example.mybhtakeawayapp.UserActivityHomeFragment;
import com.example.mybhtakeawayapp.rider.setDeliveryInformation;
import com.example.mybhtakeawayapp.user.UserActivityStoreIndex;

import java.util.ArrayList;
import java.util.List;

public class SellerActivityHomeFragment extends Fragment {
    private Button add_more;
    private RecyclerView order_ed_list;
    public SellerActivityHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.seller_activity_home ,container, false);
        order_ed_list = mView.findViewById(R.id.order_ed_list);
        add_more = mView.findViewById(R.id.add_more);
        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SellerFoodItemInformation.class);
                startActivity(intent);
            }
        });


        mRecyclerView1 = mView.findViewById(R.id.order_ed_list);

        // 构造一些数据  todo
        mNewsList1.add(new News("合一", "麻婆豆腐"));
        mNewsList1.add(new News("合一", "糖醋里脊"));

        mMyAdapter1 = new MyAdapter1();
        mRecyclerView1.setAdapter(mMyAdapter1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView1.setLayoutManager(layoutManager);


        return mView;
    }

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


    class MyAdapter1 extends RecyclerView.Adapter<MyViewHoder> {
        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getActivity(), R.layout.seller_food_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList1.get(position);
            holder.order_name.setText(news.order_name);
            if (position == 0) {
                holder.order_store_image.setImageResource(R.drawable.a1);
            } else if (position == 1) {
                holder.order_store_image.setImageResource(R.drawable.a2);
            }  else if (position == 2) {
                holder.order_store_image.setImageResource(R.drawable.a3);
            }
            else if (position == 3) {
                holder.order_store_image.setImageResource(R.drawable.a4);
            }
            else if (position == 4) {
                holder.order_store_image.setImageResource(R.drawable.a5);
            }
            else if (position == 5) {
                holder.order_store_image.setImageResource(R.drawable.a6);
            }


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
            order_name = itemView.findViewById(R.id.good_name);
            order_store_image = itemView.findViewById(R.id.good_image);
        }
    }

}
