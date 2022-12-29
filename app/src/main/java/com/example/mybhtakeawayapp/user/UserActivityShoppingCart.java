package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybhtakeawayapp.R;

import java.util.ArrayList;
import java.util.List;

public class UserActivityShoppingCart extends Activity {
    private Button back;
    private Button pay;
    private Button add_more;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_shopping_cart);
        pay = findViewById(R.id.pay);
        add_more = findViewById(R.id.add_more);
        back = findViewById(R.id.back);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityShoppingCart.this, UserActivityOrderEnsure.class);
                startActivity(intent);
            }
        });

        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityShoppingCart.this, UserActivityStoreIndex.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityShoppingCart.this, UserActivityStoreIndex.class);
                startActivity(intent);
            }
        });


        mRecyclerView = findViewById(R.id.cart_store_ListView);
        // 构造一些数据 todo
        mNewsList.add(new News("a","鱼香肉丝","80","￥20.00","2"));
        mNewsList.add(new News("a","麻婆豆腐","80","￥20.00","2"));
        mNewsList.add(new News("a","风味茄子","80","￥20.00","2"));

        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(UserActivityShoppingCart.this);
        mRecyclerView.setLayoutManager(layoutManager);
    }


    public class News {
        public String picture; // 标题
        public String name; //内容
        public String score; // 评分 百分制
        public String cost; // 花费
        public String number; // 购物车内该商品的数量
        public News(String picture, String name, String store, String cost, String number) {
            this.picture = picture;
            this.cost = cost;
            this.name = name;
            this.score = store;
            this.number = number;
        }
    }

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter ;
    List<News> mNewsList = new ArrayList<>();

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(UserActivityShoppingCart.this, R.layout.user_shopping_cart_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            // todo 图片还没弄
            //holder.user_cart_good_image.setImageIcon();
            holder.cart_good_name.setText(news.name);
            holder.cart_good_store.setText(news.score);
            holder.cart_good_num.setText(news.number);
            holder.cart_good_price.setText(news.cost);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        ImageView user_cart_good_image;
        TextView cart_good_name;
        TextView cart_good_store;
        TextView cart_good_price;
        TextView cart_good_num;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            user_cart_good_image = itemView.findViewById(R.id.user_cart_good_image);
            cart_good_name = itemView.findViewById(R.id.cart_good_name);
            cart_good_store = itemView.findViewById(R.id.cart_good_store);
            cart_good_price = itemView.findViewById(R.id.cart_good_price);
            cart_good_num = itemView.findViewById(R.id.cart_good_num);
        }
    }
}
