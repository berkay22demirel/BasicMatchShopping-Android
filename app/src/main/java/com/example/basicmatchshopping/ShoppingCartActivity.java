package com.example.basicmatchshopping;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmatchshopping.adapter.ShoppingCartSubProductAdapter;
import com.example.basicmatchshopping.api.ApiClient;
import com.example.basicmatchshopping.api.request.OrderRequest;
import com.example.basicmatchshopping.api.request.ShoppingCartRequest;
import com.example.basicmatchshopping.api.request.UserRequest;
import com.example.basicmatchshopping.api.response.OrderResponse;
import com.example.basicmatchshopping.api.response.ShoppingCartItemResponse;
import com.example.basicmatchshopping.api.response.ShoppingCartResponse;
import com.example.basicmatchshopping.api.response.UserResponse;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends AppCompatActivity {

    RecyclerView recyclerViewSubProducts;
    LinearLayout linearLayoutAmazon;
    ImageView imageViewAmazon;
    TextView textViewAmazon;
    LinearLayout linearLayoutMorrisons;
    ImageView imageViewMorrisons;
    TextView textViewMorrisons;
    Button buttonBuy;

    ShoppingCartSubProductAdapter shoppingCartSubProductAdapter;

    UserResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shopping Cart");

        user = (UserResponse) getIntent().getSerializableExtra("user");

        if (user != null) {
            Call<ShoppingCartResponse> shoppingCartCall = ApiClient.getShoppingCartApiClient().getActiveShoppingCartByUserId(user.getId() + "", user.getToken());

            shoppingCartCall.enqueue(new Callback<ShoppingCartResponse>() {
                @Override
                public void onResponse(Call<ShoppingCartResponse> call, Response<ShoppingCartResponse> response) {

                    if (response.isSuccessful()) {
                        ShoppingCartResponse shoppingCartResponse = response.body();

                        recyclerViewSubProducts = findViewById(R.id.recyclerViewShoppingCartSubProducts);
                        linearLayoutAmazon = findViewById(R.id.linearLayoutShoppingCartAmazonTotalAmount);
                        imageViewAmazon = findViewById(R.id.imageViewShoppingCartAmazonTotalAmount);
                        textViewAmazon = findViewById(R.id.textViewShoppingCartAmazonTotalAmount);
                        linearLayoutMorrisons = findViewById(R.id.linearLayoutShoppingCartMorrisonsTotalAmount);
                        imageViewMorrisons = findViewById(R.id.imageViewShoppingCartMorrisonsTotalAmount);
                        textViewMorrisons = findViewById(R.id.textViewShoppingCartMorrisonsTotalAmount);
                        buttonBuy = findViewById(R.id.buttonShoppingCartBuy);

                        recyclerViewSubProducts.setLayoutManager(new LinearLayoutManager(ShoppingCartActivity.this));
                        recyclerViewSubProducts.addItemDecoration(new DividerItemDecoration(ShoppingCartActivity.this, DividerItemDecoration.VERTICAL));

                        shoppingCartSubProductAdapter = new ShoppingCartSubProductAdapter();
                        shoppingCartSubProductAdapter.setData(shoppingCartResponse);
                        recyclerViewSubProducts.setAdapter(shoppingCartSubProductAdapter);

                        double amazonTotalAmount = 0.0;
                        double morrisonsTotalAmount = 0.0;
                        for (ShoppingCartItemResponse shoppingCartItemResponse : shoppingCartResponse.getShoppingCartItemDTOs()) {
                            if (shoppingCartItemResponse.getSubProductDTO().getSource().equals("AMAZON")) {
                                amazonTotalAmount += (shoppingCartItemResponse.getSubProductDTO().getPrice() * shoppingCartItemResponse.getQuantity());
                            } else if (shoppingCartItemResponse.getSubProductDTO().getSource().equals("MORRISONS")) {
                                morrisonsTotalAmount += (shoppingCartItemResponse.getSubProductDTO().getPrice() * shoppingCartItemResponse.getQuantity());
                            }
                        }

                        DecimalFormat df = new DecimalFormat("#.##");
                        if (amazonTotalAmount != 0.0) {
                            textViewAmazon.setText(textViewAmazon.getText().toString() + df.format(amazonTotalAmount) + "£");
                            Picasso.get().load("https://thumbor.forbes.com/thumbor/fit-in/416x416/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5d825aa26de3150009a4616c%2F0x0.jpg%3Fbackground%3D000000%26cropX1%3D0%26cropX2%3D416%26cropY1%3D0%26cropY2%3D416").into(imageViewAmazon);

                        } else {
                            linearLayoutAmazon.setVisibility(View.INVISIBLE);
                        }

                        if (morrisonsTotalAmount != 0.0) {
                            textViewMorrisons.setText(textViewMorrisons.getText().toString() + df.format(morrisonsTotalAmount) + "£");
                            Picasso.get().load("https://pbs.twimg.com/profile_images/1278233172153634817/7ziRUygO_400x400.png").into(imageViewMorrisons);

                        } else {
                            linearLayoutMorrisons.setVisibility(View.INVISIBLE);
                        }


                        buttonBuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                ShoppingCartRequest shoppingCartRequest = new ShoppingCartRequest();
                                shoppingCartRequest.setId(shoppingCartResponse.getId());

                                UserRequest userRequest = new UserRequest();
                                userRequest.setId(user.getId());

                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String date = formatter.format(new Date());

                                OrderRequest orderRequest = new OrderRequest();
                                orderRequest.setShoppingCartDTO(shoppingCartRequest);
                                orderRequest.setUserDTO(userRequest);
                                orderRequest.setOrderDate(date);

                                Call<OrderResponse> orderResponseCall = ApiClient.getOrderApiClient().buy(orderRequest, user.getToken());

                                orderResponseCall.enqueue(new Callback<OrderResponse>() {
                                    @Override
                                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {

                                        if (response.isSuccessful()) {

                                            OrderResponse orderResponse = response.body();
                                            if (orderResponse.getId() != 0) {
                                                Toast.makeText(ShoppingCartActivity.this, "Sipariş tamamlandı.", Toast.LENGTH_LONG).show();
                                                ShoppingCartActivity.this.finish();
                                            } else {
                                                Toast.makeText(ShoppingCartActivity.this, "Sipariş tamamlanamadı. Lütfen tekrar deneyin!", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(ShoppingCartActivity.this, "Sipariş tamamlanamadı. Lütfen tekrar deneyin!", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                                        Log.e("failure", t.getLocalizedMessage());
                                        Toast.makeText(ShoppingCartActivity.this, "Sipariş tamamlanamadı. Lütfen tekrar deneyin!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });

                    } else {
                        Toast.makeText(ShoppingCartActivity.this, "Sayfa yüklenirken beklenmedik bir hata oluştu! Lütfen tekrar deneyin.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ShoppingCartResponse> call, Throwable t) {
                    Log.e("failure", t.getLocalizedMessage());
                    Toast.makeText(ShoppingCartActivity.this, "Sayfa yüklenirken beklenmedik bir hata oluştu! Lütfen tekrar deneyin.", Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
