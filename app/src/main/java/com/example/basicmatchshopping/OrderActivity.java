package com.example.basicmatchshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmatchshopping.adapter.ShoppingCartSubProductAdapter;
import com.example.basicmatchshopping.api.response.OrderResponse;
import com.example.basicmatchshopping.api.response.UserResponse;

public class OrderActivity extends AppCompatActivity {

    TextView textViewOrderDate;
    TextView textViewTotalAmount;
    RecyclerView recyclerViewProducts;

    UserResponse user;
    OrderResponse order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order");

        user = (UserResponse) getIntent().getSerializableExtra("user");
        order = (OrderResponse) getIntent().getSerializableExtra("order");

        textViewOrderDate = findViewById(R.id.textViewOrderDate);
        textViewTotalAmount = findViewById(R.id.textViewOrderTotalAmount);
        recyclerViewProducts = findViewById(R.id.recyclerViewOrderProducts);

        if (order != null) {

            recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewProducts.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            ShoppingCartSubProductAdapter shoppingCartSubProductAdapter = new ShoppingCartSubProductAdapter();
            shoppingCartSubProductAdapter.setData(order.getShoppingCartDTO());

            recyclerViewProducts.setAdapter(shoppingCartSubProductAdapter);

            textViewOrderDate.setText(order.getOrderDate().substring(0, 11));
            textViewTotalAmount.setText(order.getShoppingCartDTO().getFixTotalAmount() + "£");
        } else {
            Toast.makeText(this, "Sayfa yüklenirken beklenmedik bir hata oluştu!", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent();
                intent.putExtra("user", user);
                setResult(RESULT_OK, intent);
                OrderActivity.this.finish();
                break;
            case R.id.menushoppingcart:
                Toast.makeText(this, "Shopping Cart selected", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
