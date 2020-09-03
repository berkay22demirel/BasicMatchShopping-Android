package com.example.basicmatchshopping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmatchshopping.adapter.SubProductAdapter;
import com.example.basicmatchshopping.api.ApiClient;
import com.example.basicmatchshopping.api.response.ProductResponse;
import com.example.basicmatchshopping.api.response.ShoppingCartResponse;
import com.example.basicmatchshopping.api.response.UserResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerViewSubProduct;
    ImageView imageViewSubProduct;
    TextView textViewProductName;

    SubProductAdapter subProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        ProductResponse productResponse = (ProductResponse) intent.getSerializableExtra("product");
        UserResponse user = (UserResponse) intent.getSerializableExtra("user");

        recyclerViewSubProduct = findViewById(R.id.recyclerViewActivityProduct);
        imageViewSubProduct = findViewById(R.id.imageViewActivityProduct);
        textViewProductName = findViewById(R.id.textViewActivityProductName);

        Picasso.get().load(productResponse.getImagePath()).into(imageViewSubProduct);
        textViewProductName.setText(productResponse.getName());

        if (user != null) {
            Call<ShoppingCartResponse> shoppingCartCall = ApiClient.getShoppingCartApiClient().getActiveShoppingCartByUserId(user.getId() + "", user.getToken());

            shoppingCartCall.enqueue(new Callback<ShoppingCartResponse>() {
                @Override
                public void onResponse(Call<ShoppingCartResponse> call, Response<ShoppingCartResponse> response) {

                    if (response.isSuccessful()) {
                        ShoppingCartResponse shoppingCartResponse = response.body();
                        fillSubProducts(productResponse, user, shoppingCartResponse);
                    } else {
                        Toast.makeText(ProductActivity.this, "Sayfa yüklenirken beklenmedik bir hata oluştu! Lütfen tekrar deneyin.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ShoppingCartResponse> call, Throwable t) {
                    Log.e("failure", t.getLocalizedMessage());
                    Toast.makeText(ProductActivity.this, "Sayfa yüklenirken beklenmedik bir hata oluştu! Lütfen tekrar deneyin.", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            fillSubProducts(productResponse, user, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_others, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menushoppingcart:
                Toast.makeText(this, "Shopping Cart selected", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillSubProducts(ProductResponse productResponse, UserResponse user, ShoppingCartResponse shoppingCartResponse) {
        recyclerViewSubProduct.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSubProduct.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        subProductAdapter = new SubProductAdapter();
        subProductAdapter.setData(productResponse.getSubProductDTOs(), user, shoppingCartResponse);
        recyclerViewSubProduct.setAdapter(subProductAdapter);
    }

}
