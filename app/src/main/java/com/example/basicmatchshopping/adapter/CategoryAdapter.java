package com.example.basicmatchshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmatchshopping.R;
import com.example.basicmatchshopping.api.response.CategoryResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterVH> {

    List<CategoryResponse> categoryResponses;
    Context context;

    public CategoryAdapter() {
    }

    public void setData(List<CategoryResponse> categoryResponses) {
        this.categoryResponses = categoryResponses;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CategoryAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CategoryAdapter.CategoryAdapterVH(LayoutInflater.from(context).inflate(R.layout.recycler_view_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterVH holder, int position) {

        CategoryResponse categoryResponse = categoryResponses.get(position);

        Picasso.get().load(categoryResponse.getImagePath()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return categoryResponses.size();
    }

    public class CategoryAdapterVH extends RecyclerView.ViewHolder {

        ImageView imageView;

        public CategoryAdapterVH(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewCategory);
        }
    }
}
