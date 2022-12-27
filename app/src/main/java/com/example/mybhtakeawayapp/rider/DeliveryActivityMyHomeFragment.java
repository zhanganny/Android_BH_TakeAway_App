package com.example.mybhtakeawayapp.rider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.user.setUserInformation;

public class DeliveryActivityMyHomeFragment extends Fragment {
    private Button riderInfoSet;
    public DeliveryActivityMyHomeFragment() {
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
        View mView = inflater.inflate(R.layout.delivery_activity_my ,container, false);
        riderInfoSet = mView.findViewById(R.id.riderInfoSet);
        riderInfoSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), setDeliveryInformation.class);
                startActivity(intent);
            }
        });
        return mView;
    }
}
