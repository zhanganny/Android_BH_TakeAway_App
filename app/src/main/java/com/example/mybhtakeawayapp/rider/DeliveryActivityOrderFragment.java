package com.example.mybhtakeawayapp.rider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.admin.AdministratorHomeActivity;
import com.example.mybhtakeawayapp.user.UserActivityContactsFragment;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivityOrderFragment extends Fragment {

    public DeliveryActivityOrderFragment() {
        // Required empty public constructor
    }

    RecyclerView order_ed_list;
    List<DeliveryActivityOrderFragment.News> mNewsList = new ArrayList<>();
    public class News {
        // todo
        public String infoToConfirm; // 标题
        public News(String infoToConfirm) {
            this.infoToConfirm = infoToConfirm;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.delivery_activity_order ,container, false);

        OrderAdapter adapter = new OrderAdapter();

        order_ed_list = mView.findViewById(R.id.order_ed_list);
        order_ed_list.setAdapter(adapter);
        return mView;
    }
}
