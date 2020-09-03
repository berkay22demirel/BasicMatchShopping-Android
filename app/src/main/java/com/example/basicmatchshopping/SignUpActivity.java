package com.example.basicmatchshopping;

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
import com.example.basicmatchshopping.api.request.UserRequest;
import com.example.basicmatchshopping.api.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextName;
    EditText editTextSurname;
    EditText editTextEmail;
    Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign Up");

        editTextUsername = findViewById(R.id.editTextSignUpUsername);
        editTextPassword = findViewById(R.id.editTextSignUpPassword);
        editTextName = findViewById(R.id.editTextSignUpName);
        editTextSurname = findViewById(R.id.editTextSignUpSurname);
        editTextEmail = findViewById(R.id.editTextSignUpEmail);
        buttonSignUp = findViewById(R.id.buttonSignUpActivity);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String name = editTextName.getText().toString();
                String surname = editTextSurname.getText().toString();
                String email = editTextEmail.getText().toString();

                if (username == null || username.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Username boş ve 6 karakterden kısa olamaz", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password == null || password.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Password boş ve 6 karakterden kısa olamaz", Toast.LENGTH_LONG).show();
                    return;
                }
                if (name == null || name.length() < 3) {
                    Toast.makeText(SignUpActivity.this, "Name boş ve 3 karakterden kısa olamaz", Toast.LENGTH_LONG).show();
                    return;
                }
                if (surname == null || surname.length() < 3) {
                    Toast.makeText(SignUpActivity.this, "Surname boş ve 3 karakterden kısa olamaz", Toast.LENGTH_LONG).show();
                    return;
                }
                if (email == null || email.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Email boş ve 6 karakterden kısa olamaz", Toast.LENGTH_LONG).show();
                    return;
                }

                UserRequest userRequest = new UserRequest();
                userRequest.setUsername(username);
                userRequest.setPassword(password);
                userRequest.setName(name);
                userRequest.setSurname(surname);
                userRequest.setEmail(email);

                Call<UserResponse> userResponseCall = ApiClient.getUserApiClient().create(userRequest);
                userResponseCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                        if (response.isSuccessful()) {
                            UserResponse userResponse = response.body();

                            if (userResponse.getId() != 0) {
                                Toast.makeText(SignUpActivity.this, userResponse.getUsername() + " kullanıcısı başarıyla oluşturuldu.", Toast.LENGTH_LONG).show();
                                finish();
                                return;
                            }
                        }

                        Toast.makeText(SignUpActivity.this, "Kullanıcı oluşturulurken beklenmedik bir hata oluştu!", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("failure", t.getLocalizedMessage());
                        Toast.makeText(SignUpActivity.this, "Kullanıcı oluşturulurken beklenmedik bir hata oluştu!", Toast.LENGTH_LONG).show();
                    }
                });
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
}
