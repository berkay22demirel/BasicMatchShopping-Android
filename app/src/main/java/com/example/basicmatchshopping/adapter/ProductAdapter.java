package com.example.basicmatchshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmatchshopping.R;
import com.example.basicmatchshopping.api.response.ProductResponse;
import com.example.basicmatchshopping.api.response.SubProductResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterVH> implements Filterable {

    List<ProductResponse> productResponses;
    List<ProductResponse> productResponseAll;
    Context context;
    ClickedItem clickedItem;

    public ProductAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(List<ProductResponse> productResponses) {
        this.productResponses = productResponses;
        this.productResponseAll = new ArrayList<>(productResponses);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ProductAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ProductAdapter.ProductAdapterVH(LayoutInflater.from(context).inflate(R.layout.recycler_view_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterVH holder, int position) {

        ProductResponse productResponse = productResponses.get(position);

        Picasso.get().load(productResponse.getImagePath()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.clickedProduct(productResponse);
            }
        });
        holder.textViewName.setText(productResponse.getName());
        SubProductResponse subProductResponse = productResponse.getSubProductDTOs().get(0);
        holder.textViewPrice.setText(subProductResponse.getAmount() + " " + subProductResponse.getAmountType() + " / " + subProductResponse.getPrice() + "£");

    }

    @Override
    public int getItemCount() {
        return productResponses.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<ProductResponse> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(productResponseAll);
            } else {
                for (ProductResponse productResponse : productResponseAll) {
                    if (productResponse.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(productResponse);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            productResponses.clear();
            productResponses.addAll((Collection<? extends ProductResponse>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ProductAdapterVH extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;

        public ProductAdapterVH(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewProduct);
            textViewName = itemView.findViewById(R.id.textViewProductName);
            textViewPrice = itemView.findViewById(R.id.textViewProductPrice);

        }
    }

    public interface ClickedItem {

        public void clickedProduct(ProductResponse productResponse);
    }
}
