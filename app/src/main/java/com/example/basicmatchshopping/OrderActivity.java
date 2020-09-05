package com.example.basicmatchshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.example.basicmatchshopping.api.response.OrderResponse;
import com.example.basicmatchshopping.api.response.ShoppingCartItemResponse;
import com.example.basicmatchshopping.api.response.UserResponse;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class OrderActivity extends AppCompatActivity {

    TextView textViewOrderDate;
    LinearLayout linearLayoutAmazon;
    ImageView imageViewAmazon;
    TextView textViewAmazon;
    LinearLayout linearLayoutMorrisons;
    ImageView imageViewMorrisons;
    TextView textViewMorrisons;
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
        linearLayoutAmazon = findViewById(R.id.linearLayoutOrderAmazonTotalAmount);
        imageViewAmazon = findViewById(R.id.imageViewOrderAmazonTotalAmount);
        textViewAmazon = findViewById(R.id.textViewOrderAmazonTotalAmount);
        linearLayoutMorrisons = findViewById(R.id.linearLayoutOrderMorrisonsTotalAmount);
        imageViewMorrisons = findViewById(R.id.imageViewOrderMorrisonsTotalAmount);
        textViewMorrisons = findViewById(R.id.textViewOrderMorrisonsTotalAmount);
        textViewTotalAmount = findViewById(R.id.textViewOrderTotalAmount);
        recyclerViewProducts = findViewById(R.id.recyclerViewOrderProducts);

        if (order != null) {

            recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewProducts.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            ShoppingCartSubProductAdapter shoppingCartSubProductAdapter = new ShoppingCartSubProductAdapter();
            shoppingCartSubProductAdapter.setData(order.getShoppingCartDTO());

            recyclerViewProducts.setAdapter(shoppingCartSubProductAdapter);

            textViewOrderDate.setText(order.getOrderDate().substring(0, 11));

            double amazonTotalAmount = 0.0;
            double morrisonsTotalAmount = 0.0;
            for (ShoppingCartItemResponse shoppingCartItemResponse : order.getShoppingCartDTO().getShoppingCartItemDTOs()) {
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

            textViewTotalAmount.setText(textViewTotalAmount.getText().toString() + df.format(order.getShoppingCartDTO().getTotalAmount()) + "£");

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
                if (user == null) {
                    Intent intentLogin = new Intent(this, LoginActivity.class);
                    startActivityForResult(intentLogin, 1);
                } else {
                    Intent intentShoppingCart = new Intent(this, ShoppingCartActivity.class);
                    intentShoppingCart.putExtra("user", user);
                    startActivity(intentShoppingCart);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
