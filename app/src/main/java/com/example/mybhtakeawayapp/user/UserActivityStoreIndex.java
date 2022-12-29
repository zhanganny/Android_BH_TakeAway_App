package com.example.mybhtakeawayapp.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybhtakeawayapp.LoginActivity;
import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.RegisterActivity;
import com.example.mybhtakeawayapp.admin.AdministratorHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class UserActivityStoreIndex extends Activity {
    private Button pay;
    private Button back;

    public class News {
        // todo
        public String good_name; // 标题
        public String good_composition;
        public String good_price;
        public String process;

        public News(String good_name, String good_composition, String good_price, String process) {
            this.good_name = good_name;
            this.good_composition = good_composition;
            this.good_price = good_price;
            this.process = process;
        }
    }

    RecyclerView store_ListView;
    MyAdapter mMyAdapter ;
    List<News> mNewsList = new ArrayList<>();

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @Override
        public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(UserActivityStoreIndex.this, R.layout.user_store_item, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            // todo
            holder.good_name.setText(news.good_name);
            holder.good_composition.setText(news.good_name);
            holder.good_price.setText(news.good_name);
            holder.recommend.setText(news.process);
            holder.process.setProgress(Integer.parseInt(news.process));
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView good_name;
        TextView good_composition;
        TextView good_price;
        TextView recommend;
        ProgressBar process;
        // todo
        public MyViewHoder(View itemView) {
            super(itemView);
            good_name = itemView.findViewById(R.id.good_name);
            good_composition = itemView.findViewById(R.id.good_composition);
            good_price = itemView.findViewById(R.id.good_price);
            recommend = itemView.findViewById(R.id.recommend);
            process = itemView.findViewById(R.id.process);

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_store_index);
        pay = findViewById(R.id.pay);
        back = findViewById(R.id.back);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityStoreIndex.this, UserActivityShoppingCart.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivityStoreIndex.this, UserActivityHome.class);
                startActivity(intent);
                finish();
            }
        });

        // 链接recyclerview todo
        store_ListView = findViewById(R.id.store_ListView);
        // 构造一些数据 todo
        mNewsList.add(new News("学二食堂", "大豆","￥12.00","80"));
        mNewsList.add(new News("学食堂", "大豆","￥12.00","80"));
        mMyAdapter = new MyAdapter();
        store_ListView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(UserActivityStoreIndex.this);
        store_ListView.setLayoutManager(layoutManager);
    }
}
