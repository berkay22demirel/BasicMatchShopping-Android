package com.example.basicmatchshopping;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.basicmatchshopping.api.response.ProductResponse;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageView;
    TextView textView;

    SubProductAdapter subProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            ProductResponse productResponse = (ProductResponse) intent.getSerializableExtra("product");

            recyclerView = findViewById(R.id.recyclerViewActivityProduct);
            imageView = findViewById(R.id.imageViewActivityProduct);
            textView = findViewById(R.id.textViewActivityProductName);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            subProductAdapter = new SubProductAdapter();
            subProductAdapter.setData(productResponse.getSubProductDTOs());
            recyclerView.setAdapter(subProductAdapter);

            Picasso.get().load(productResponse.getImagePath()).into(imageView);
            textView.setText(productResponse.getName());
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

}
