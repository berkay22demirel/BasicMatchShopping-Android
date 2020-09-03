package com.example.basicmatchshopping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmatchshopping.adapter.ProductAdapter;
import com.example.basicmatchshopping.api.ApiClient;
import com.example.basicmatchshopping.api.response.ProductResponse;
import com.example.basicmatchshopping.api.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity implements ProductAdapter.ClickedItem {

    RecyclerView recyclerView;

    ProductAdapter productAdapter;
    UserResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Products");

        int categoryId = getIntent().getIntExtra("categoryId", 0);
        user = (UserResponse) getIntent().getSerializableExtra("user");

        recyclerView = findViewById(R.id.recyclerViewProducts);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        productAdapter = new ProductAdapter(this::clickedProduct);

        Call<List<ProductResponse>> productList = ApiClient.getProductApiClient().getAllByCategoryId(categoryId + "");

        productList.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {

                List<ProductResponse> products = response.body();

                productAdapter.setData(products);
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products, menu);
        MenuItem item = menu.findItem(R.id.menuseach);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menuseach:
                Toast.makeText(this, "Search selected", Toast.LENGTH_LONG).show();
                break;
            case R.id.menushoppingcart:
                Toast.makeText(this, "Shopping Cart selected", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickedProduct(ProductResponse productResponse) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("product", productResponse);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
