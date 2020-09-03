package com.example.basicmatchshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmatchshopping.R;
import com.example.basicmatchshopping.api.response.ShoppingCartItemResponse;
import com.example.basicmatchshopping.api.response.ShoppingCartResponse;
import com.squareup.picasso.Picasso;

public class ShoppingCartSubProductAdapter extends RecyclerView.Adapter<ShoppingCartSubProductAdapter.ShoppingCartSubProductAdapterVH> {

    ShoppingCartResponse shoppingCartResponse;
    Context context;

    public void setData(ShoppingCartResponse shoppingCartResponse) {
        this.shoppingCartResponse = shoppingCartResponse;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ShoppingCartSubProductAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ShoppingCartSubProductAdapter.ShoppingCartSubProductAdapterVH(LayoutInflater.from(context).inflate(R.layout.recycler_view_shopping_cart_sub_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartSubProductAdapterVH holder, int position) {

        ShoppingCartItemResponse shoppingCartItemResponse = shoppingCartResponse.getShoppingCartItemDTOs().get(position);

        Picasso.get().load(shoppingCartItemResponse.getSubProductDTO().getProductDTO().getImagePath()).into(holder.imageView);

        String totalAmount = (shoppingCartItemResponse.getSubProductDTO().getPrice() * shoppingCartItemResponse.getQuantity()) + "";
        if (totalAmount.length() > 4) {
            totalAmount = totalAmount.substring(0, 5);
        }

        holder.textViewName.setText(shoppingCartItemResponse.getSubProductDTO().getSource() + " - " + shoppingCartItemResponse.getSubProductDTO().getProductDTO().getName());
        holder.textViewTotalAmount.setText(totalAmount + "£");
        holder.textViewQuantity.setText(shoppingCartItemResponse.getQuantity() + "");

    }

    @Override
    public int getItemCount() {
        return shoppingCartResponse.getShoppingCartItemDTOs().size();
    }

    public class ShoppingCartSubProductAdapterVH extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName;
        TextView textViewTotalAmount;
        TextView textViewQuantity;

        public ShoppingCartSubProductAdapterVH(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewShoppingCartSubProductSource);
            textViewName = itemView.findViewById(R.id.textViewShoppingCartSubProductName);
            textViewTotalAmount = itemView.findViewById(R.id.textViewShoppingCartSubProductTotalAmount);
            textViewQuantity = itemView.findViewById(R.id.textViewShoppingCartSubProductQuantity);

        }
    }
}
