package com.example.basicmatchshopping;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.basicmatchshopping.api.response.ShoppingCartResponse;
import com.example.basicmatchshopping.api.response.UserResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends AppCompatActivity {

    RecyclerView recyclerViewSubProducts;
    TextView textViewTotalAmount;
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
                        textViewTotalAmount = findViewById(R.id.textViewShoppingCartTotalAmount);
                        buttonBuy = findViewById(R.id.buttonShoppingCartBuy);

                        recyclerViewSubProducts.setLayoutManager(new LinearLayoutManager(ShoppingCartActivity.this));
                        recyclerViewSubProducts.addItemDecoration(new DividerItemDecoration(ShoppingCartActivity.this, DividerItemDecoration.VERTICAL));

                        shoppingCartSubProductAdapter = new ShoppingCartSubProductAdapter();
                        shoppingCartSubProductAdapter.setData(shoppingCartResponse);
                        recyclerViewSubProducts.setAdapter(shoppingCartSubProductAdapter);

                        String totalAmount = "" + shoppingCartResponse.getTotalAmount();
                        if (totalAmount.length() > 4) {
                            totalAmount = totalAmount.substring(0, 5);
                        }

                        textViewTotalAmount.setText(textViewTotalAmount.getText().toString() + totalAmount + "£");

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
        getMenuInflater().inflate(R.menu.menu_others, menu);
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
