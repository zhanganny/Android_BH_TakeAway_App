package com.example.mybhtakeawayapp.rider;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mybhtakeawayapp.R;
import com.example.mybhtakeawayapp.admin.AdministratorHomeActivity;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List mOrderList;

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.delivery_activity_order, null);
        final OrderAdapter.ViewHolder holder = new OrderAdapter.ViewHolder(view);
        holder.Orderview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
//                TakeOrder order = mOrderList.get(position);
//                Intent intent;
//                intent = new Intent(view.getContext(), RiderOrderInfo.class);
//                intent.putExtra("OrderId", order.getName());
//                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.OrderName.setText("订单");
        holder.OrderTime.setText("订单下单时间");
        holder.OrderState.setText("订单状态：已下单");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View Orderview;
        TextView OrderName;
        TextView OrderTime;
        TextView OrderState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Orderview = itemView;
            OrderName = itemView.findViewById(R.id.order_name);
            OrderTime = itemView.findViewById(R.id.order_time);
            OrderState = itemView.findViewById(R.id.order_statement);
        }
    }
}

