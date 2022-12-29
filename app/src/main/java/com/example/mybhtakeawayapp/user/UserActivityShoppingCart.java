package com.example.mybhtakeawayapp.user;

import static com.example.mybhtakeawayapp.Local.addTotalMoney;
import static com.example.mybhtakeawayapp.Local.shopingcar;
import static com.example.mybhtakeawayapp.Local.shopingcaritem;

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

import com.example.mybhtakeawayapp.Local;
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
        for (String name: shopingcar.keySet()) {
            UserActivityStoreIndex.News news = shopingcaritem.get(name);
            mNewsList.add(new News("a",news.good_name,news.good_composition,"￥"+news.good_price,shopingcar.get(name).toString()));
        }
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
            if (news.name == "麻婆豆腐") {
                holder.user_cart_good_image.setImageResource(R.drawable.a1);
            } else if(news.name == "糖醋里脊") {
                holder.user_cart_good_image.setImageResource(R.drawable.a2);
            } else if(news.name == "风味茄子") {
                holder.user_cart_good_image.setImageResource(R.drawable.a3);
            }
            else if(news.name == "梅菜扣肉") {
                holder.user_cart_good_image.setImageResource(R.drawable.a4);
            } else  if(news.name == "西红柿炒蛋") {
                holder.user_cart_good_image.setImageResource(R.drawable.a6);
            } else {
                holder.user_cart_good_image.setImageResource(R.drawable.a6);
            }
            holder.cart_good_name.setText(news.name);
            holder.cart_good_store.setText(news.score);
            holder.cart_good_num.setText(news.number);
            holder.cart_good_price.setText(news.cost);
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addTotalMoney(Float.valueOf(((String) holder.cart_good_price.getText()).substring(((String) holder.cart_good_price.getText()).length()-5)));
                    String name = (String) holder.cart_good_name.getText();
                    shopingcar.put(name, shopingcar.get(name) + 1);
                    holder.cart_good_num.setText(String.valueOf(Integer.valueOf((String) holder.cart_good_num.getText()) + 1));
                }
            });
            holder.sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = (String) holder.cart_good_name.getText();
                    shopingcar.put(name, shopingcar.get(name) - 1);
                    holder.cart_good_num.setText(String.valueOf(Integer.valueOf((String) holder.cart_good_num.getText()) - 1));

                }
            });
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
        Button add;
        Button sub;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            user_cart_good_image = itemView.findViewById(R.id.user_cart_good_image);
            cart_good_name = itemView.findViewById(R.id.cart_good_name);
            cart_good_store = itemView.findViewById(R.id.cart_good_store);
            cart_good_price = itemView.findViewById(R.id.cart_good_price);
            cart_good_num = itemView.findViewById(R.id.cart_good_num);
            add = itemView.findViewById(R.id.plus);
            sub = itemView.findViewById(R.id.sub);
        }
    }
}
