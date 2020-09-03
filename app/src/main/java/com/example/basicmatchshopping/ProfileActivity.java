package com.example.basicmatchshopping;

import android.content.Intent;
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

import com.example.basicmatchshopping.adapter.OrderAdapter;
import com.example.basicmatchshopping.api.ApiClient;
import com.example.basicmatchshopping.api.response.OrderResponse;
import com.example.basicmatchshopping.api.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements OrderAdapter.ClickedItem {

    RecyclerView recyclerViewOrders;
    TextView textViewNameSurname;
    Button buttonSignOut;

    OrderAdapter orderAdapter;

    UserResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        recyclerViewOrders = findViewById(R.id.recyclerViewProfileOrders);
        textViewNameSurname = findViewById(R.id.textViewProfileNameSurname);
        buttonSignOut = findViewById(R.id.buttonSignOut);

        user = (UserResponse) getIntent().getSerializableExtra("user");

        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrders.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        orderAdapter = new OrderAdapter(this::clickedOrder);

        Call<List<OrderResponse>> orderList = ApiClient.getOrderApiClient().getAllByUserId(user.getId() + "", user.getToken());

        orderList.enqueue(new Callback<List<OrderResponse>>() {
            @Override
            public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {

                if (response.isSuccessful()) {
                    List<OrderResponse> orders = response.body();

                    orderAdapter.setData(orders);
                    recyclerViewOrders.setAdapter(orderAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });

        textViewNameSurname.setText(user.getName() + " " + user.getSurname());

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                user = null;
                intent.putExtra("user", user);
                setResult(RESULT_OK, intent);
                ProfileActivity.this.finish();
            }
        });
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
                Intent intent = new Intent();
                intent.putExtra("user", user);
                setResult(RESULT_OK, intent);
                ProfileActivity.this.finish();
                break;
            case R.id.menushoppingcart:
                Toast.makeText(this, "Shopping Cart selected", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickedOrder(OrderResponse orderResponse) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("order", orderResponse);
        startActivityForResult(intent, 1);
    }
}
