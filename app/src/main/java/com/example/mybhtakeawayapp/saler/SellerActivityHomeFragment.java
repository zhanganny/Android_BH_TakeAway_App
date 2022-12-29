package com.example.mybhtakeawayapp.saler;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybhtakeawayapp.LoginActivity;
import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.RegisterActivity;
import com.example.mybhtakeawayapp.rider.setDeliveryInformation;

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

        return mView;
    }
}
