package com.example.basicmatchshopping;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicmatchshopping.adapter.CategoryAdapter;
import com.example.basicmatchshopping.api.ApiClient;
import com.example.basicmatchshopping.api.response.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewCategories);

        recyclerView.setItemViewCacheSize(20);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        categoryAdapter = new CategoryAdapter();

        Call<List<CategoryResponse>> categoryList = ApiClient.getCategoryService().getAll();

        categoryList.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {

                List<CategoryResponse> categories = response.body();

                categoryAdapter.setData(categories);
                recyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuseach:
                Toast.makeText(this, "Search selected", Toast.LENGTH_LONG).show();
                break;
            case R.id.menushoppingcart:
                Toast.makeText(this, "Shopping Cart selected", Toast.LENGTH_LONG).show();
                break;
            case R.id.menuprofile:
                Toast.makeText(this, "Profile selected", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
