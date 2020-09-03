package com.example.basicmatchshopping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicmatchshopping.api.ApiClient;
import com.example.basicmatchshopping.api.request.LoginRequest;
import com.example.basicmatchshopping.api.response.TokenResponse;
import com.example.basicmatchshopping.api.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonSignIn;
    Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        editTextUsername = findViewById(R.id.editTextSignInUsername);
        editTextPassword = findViewById(R.id.editTextSignInPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (username == null || username.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Username boş ve 6 karakterden kısa olamaz", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password == null || password.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Password boş ve 6 karakterden kısa olamaz", Toast.LENGTH_LONG).show();
                    return;
                }

                login(username, password);
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
                finish();
                break;
            case R.id.menushoppingcart:
                Toast.makeText(this, "Shopping Cart selected", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        UserResponse existingUser = new UserResponse();

        Call<TokenResponse> tokenResponse = ApiClient.getUserApiClient().login(loginRequest);
        tokenResponse.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    existingUser.setToken("mytoken " + tokenResponse.getToken());

                    Call<UserResponse> userResponse = ApiClient.getUserApiClient().get(username, existingUser.getToken());

                    userResponse.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                            if (response.isSuccessful()) {
                                UserResponse body = response.body();
                                existingUser.setId(body.getId());
                                existingUser.setUsername(body.getUsername());
                                existingUser.setName(body.getName());
                                existingUser.setSurname(body.getSurname());
                                existingUser.setEmail(body.getEmail());

                                if (existingUser.getId() != 0) {
                                    Intent intent = new Intent();
                                    intent.putExtra("user", existingUser);
                                    setResult(RESULT_OK, intent);
                                    LoginActivity.this.finish();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Giriş başarısız!", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Log.e("failure", t.getLocalizedMessage());
                            Toast.makeText(LoginActivity.this, "Giriş başarısız!", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Giriş başarısız!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
                Toast.makeText(LoginActivity.this, "Giriş başarısız!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
