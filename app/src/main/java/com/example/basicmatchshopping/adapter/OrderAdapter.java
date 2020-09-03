package com.example.basicmatchshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmatchshopping.R;
import com.example.basicmatchshopping.api.response.OrderResponse;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderAdapterVH> {

    List<OrderResponse> orderResponses;
    Context context;
    ClickedItem clickedItem;

    public OrderAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(List<OrderResponse> orderResponses) {
        this.orderResponses = orderResponses;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public OrderAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new OrderAdapter.OrderAdapterVH(LayoutInflater.from(context).inflate(R.layout.recycler_view_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapterVH holder, int position) {

        OrderResponse orderResponse = orderResponses.get(position);

        holder.textViewDate.setText(orderResponse.getOrderDate());
        holder.textViewTotalAmount.setText(orderResponse.getShoppingCartDTO().getTotalAmount() + "£");

        holder.linearLayoutOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.clickedOrder(orderResponse);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderResponses.size();
    }

    public class OrderAdapterVH extends RecyclerView.ViewHolder {

        TextView textViewDate;
        TextView textViewTotalAmount;
        LinearLayout linearLayoutOrderItem;

        public OrderAdapterVH(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.textViewOrderItemDate);
            textViewTotalAmount = itemView.findViewById(R.id.textViewOrderItemTotalAmount);
            linearLayoutOrderItem = itemView.findViewById(R.id.linearLayoutOrderItem);
        }
    }

    public interface ClickedItem {

        public void clickedOrder(OrderResponse orderResponse);
    }
}