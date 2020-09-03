package com.example.basicmatchshopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmatchshopping.R;
import com.example.basicmatchshopping.api.ApiClient;
import com.example.basicmatchshopping.api.request.ShoppingCartItemRequest;
import com.example.basicmatchshopping.api.request.ShoppingCartRequest;
import com.example.basicmatchshopping.api.request.SubProductRequest;
import com.example.basicmatchshopping.api.response.ResultResponse;
import com.example.basicmatchshopping.api.response.ShoppingCartItemResponse;
import com.example.basicmatchshopping.api.response.ShoppingCartResponse;
import com.example.basicmatchshopping.api.response.SubProductResponse;
import com.example.basicmatchshopping.api.response.UserResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubProductAdapter extends RecyclerView.Adapter<SubProductAdapter.SubProductAdapterVH> {

    List<SubProductResponse> subProductResponses;
    UserResponse user;
    ShoppingCartResponse shoppingCart;
    Context context;

    public void setData(List<SubProductResponse> subProductResponses, UserResponse user, ShoppingCartResponse shoppingCart) {
        this.subProductResponses = subProductResponses;
        this.user = user;
        this.shoppingCart = shoppingCart;
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

        if (user == null && shoppingCart == null) {
            holder.linearLayoutAddGroup.setVisibility(View.INVISIBLE);
        } else {

            ShoppingCartItemRequest shoppingCartItemRequest = getShoppingCartItemRequest(subProductResponse);

            holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String quantity = holder.editTextQuantity.getText().toString();
                    shoppingCartItemRequest.setQuantity(shoppingCartItemRequest.getQuantity() + Integer.valueOf(quantity));

                    if (shoppingCartItemRequest.getId() == 0) {
                        create(shoppingCartItemRequest);
                    } else {
                        update(shoppingCartItemRequest);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return subProductResponses.size();
    }

    public class SubProductAdapterVH extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;
        Button buttonAdd;
        EditText editTextQuantity;
        LinearLayout linearLayoutAddGroup;

        public SubProductAdapterVH(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewSubProductSource);
            textViewName = itemView.findViewById(R.id.textViewSubProductSourceName);
            textViewPrice = itemView.findViewById(R.id.textViewSubProductPrice);
            buttonAdd = itemView.findViewById(R.id.buttonAdd);
            editTextQuantity = itemView.findViewById(R.id.editTextSubProductQuantity);
            linearLayoutAddGroup = itemView.findViewById(R.id.linearLayoutAddGroup);

        }
    }

    private ShoppingCartItemRequest getShoppingCartItemRequest(SubProductResponse subProductResponse) {

        ShoppingCartItemResponse existingShoppingCartItemResponse = null;
        List<ShoppingCartItemResponse> shoppingCartItemResponses = shoppingCart.getShoppingCartItemDTOs();
        if (shoppingCartItemResponses != null && !shoppingCartItemResponses.isEmpty()) {
            for (ShoppingCartItemResponse shoppingCartItemResponse : shoppingCartItemResponses) {
                if (shoppingCartItemResponse.getSubProductDTO().getId() == subProductResponse.getId()) {
                    existingShoppingCartItemResponse = shoppingCartItemResponse;
                    break;
                }
            }
        }

        ShoppingCartItemRequest shoppingCartItemRequest = new ShoppingCartItemRequest();
        ShoppingCartRequest shoppingCartRequest = new ShoppingCartRequest();
        shoppingCartRequest.setId(shoppingCart.getId());
        shoppingCartItemRequest.setShoppingCartDTO(shoppingCartRequest);
        SubProductRequest subProductRequest = new SubProductRequest();
        subProductRequest.setId(subProductResponse.getId());
        subProductRequest.setPrice(subProductResponse.getPrice());
        shoppingCartItemRequest.setSubProductDTO(subProductRequest);

        if (existingShoppingCartItemResponse == null) {
            shoppingCartItemRequest.setQuantity(0);
        } else {
            shoppingCartItemRequest.setId(existingShoppingCartItemResponse.getId());
            shoppingCartItemRequest.setQuantity(existingShoppingCartItemResponse.getQuantity());
        }

        return shoppingCartItemRequest;
    }

    private void create(ShoppingCartItemRequest shoppingCartItemRequest) {

        Call<ShoppingCartItemResponse> shoppingCartCall = ApiClient.getShoppingCartItemApiClient().create(shoppingCartItemRequest, user.getToken());

        shoppingCartCall.enqueue(new Callback<ShoppingCartItemResponse>() {
            @Override
            public void onResponse(Call<ShoppingCartItemResponse> call, Response<ShoppingCartItemResponse> response) {

                if (response.isSuccessful()) {
                    ShoppingCartItemResponse shoppingCartItemResponse = response.body();
                    if (shoppingCartItemResponse.getId() != 0) {
                        Toast.makeText(context, "Ürün başarıyla sepete eklendi.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Toast.makeText(context, "Ürün sepete eklenirken beklenmedik bir hata oluştu!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ShoppingCartItemResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                Toast.makeText(context, "Ürün sepete eklenirken beklenmedik bir hata oluştu!", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void update(ShoppingCartItemRequest shoppingCartItemRequest) {

        Call<ResultResponse> resultResponseCall = ApiClient.getShoppingCartItemApiClient().update(shoppingCartItemRequest, user.getToken());

        resultResponseCall.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {

                if (response.isSuccessful()) {
                    ResultResponse resultResponse = response.body();
                    if (resultResponse.getResult().equals("ShoppingCartItem is updated successsfully")) {
                        Toast.makeText(context, "Ürün başarıyla sepete eklendi.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Toast.makeText(context, "Ürün sepete eklenirken beklenmedik bir hata oluştu!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                Toast.makeText(context, "Ürün sepete eklenirken beklenmedik bir hata oluştu!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
