package com.example.basicmatchshopping;

import android.content.Intent;
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
import com.example.basicmatchshopping.api.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.ClickedItem {

    RecyclerView recyclerView;

    CategoryAdapter categoryAdapter;

    UserResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewCategories);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        categoryAdapter = new CategoryAdapter(this::clickedCategory);

        Call<List<CategoryResponse>> categoryList = ApiClient.getCategoryApiClient().getAll();

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
            case R.id.menushoppingcart:
                Toast.makeText(this, "Shopping Cart selected", Toast.LENGTH_LONG).show();
                break;
            case R.id.menuprofile:
                if (user == null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    Intent intent = new Intent(this, ProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivityForResult(intent, 1);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickedCategory(CategoryResponse categoryResponse) {
        Intent intent = new Intent(this, ProductsActivity.class);
        intent.putExtra("categoryId", categoryResponse.getId());
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                user = (UserResponse) data.getSerializableExtra("user");
            }
        }
    }
}
