package com.example.thanh.activity;

import static com.example.thanh.retrofit.RetrofitClient.getRetrofitInstance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.model.User;
import com.example.thanh.retrofit.ApiService;
import com.google.gson.Gson;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private TextView forgotPasswordTextView;
    private ApiService userApi;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        Retrofit retrofit = getRetrofitInstance();
        databaseHelper = new DatabaseHelper(this);
        userApi = retrofit.create(ApiService.class);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
//                Intent intent = new Intent(login.this, post_user_gets.class);
//                startActivity(intent);
//                finish();
            }
        });
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
//                List<User> ul = databaseHelper.getAllUser();
//                Toast.makeText(login.this, String.valueOf(ul.get(0).get_id()), Toast.LENGTH_SHORT).show();
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, forgotpassword.class);
                startActivity(intent);
            }
        });
    }

    public void login () {

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        Call<User> call = userApi.logIn(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User User1 = response.body();
                    String jsonString = new Gson().toJson(User1);
                    Log.d("RES", jsonString);
                    saveUser(User1);
                    Intent intent = new Intent(login.this, post_user_gets.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(login.this, "Lỗi: " +"Register fail", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(login.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this, login.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void saveUser(User u) {
        User user = new User();
        user.set_id(u.get_id());
        user.setEmail(u.getEmail());
        long result;
        databaseHelper.deleteAll();
        result = databaseHelper.addUser(user);
        if (result != -1) {
            Toast.makeText(this, "Thêm user thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Thêm user thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}