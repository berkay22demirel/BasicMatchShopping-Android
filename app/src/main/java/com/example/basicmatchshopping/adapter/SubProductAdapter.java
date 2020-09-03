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
import com.example.basicmatchshopping.api.response.SubProductResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubProductAdapter extends RecyclerView.Adapter<SubProductAdapter.SubProductAdapterVH> {

    List<SubProductResponse> subProductResponses;
    Context context;

    public void setData(List<SubProductResponse> subProductResponses) {
        this.subProductResponses = subProductResponses;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public SubProductAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SubProductAdapter.SubProductAdapterVH(LayoutInflater.from(context).inflate(R.layout.recycler_view_sub_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubProductAdapterVH holder, int position) {

        SubProductResponse subProductResponse = subProductResponses.get(position);

        if (subProductResponse.getSource().equals("AMAZON")) {
            Picasso.get().load("https://thumbor.forbes.com/thumbor/fit-in/416x416/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5d825aa26de3150009a4616c%2F0x0.jpg%3Fbackground%3D000000%26cropX1%3D0%26cropX2%3D416%26cropY1%3D0%26cropY2%3D416").into(holder.imageView);
        }

        holder.textViewName.setText(subProductResponse.getSource());
        holder.textViewPrice.setText(subProductResponse.getAmount() + " " + subProductResponse.getAmountType() + " / " + subProductResponse.getPrice() + "£");

    }

    @Override
    public int getItemCount() {
        return subProductResponses.size();
    }

    public class SubProductAdapterVH extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;

        public SubProductAdapterVH(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewSubProductSource);
            textViewName = itemView.findViewById(R.id.textViewSubProductSourceName);
            textViewPrice = itemView.findViewById(R.id.textViewSubProductPrice);

        }
    }
}
