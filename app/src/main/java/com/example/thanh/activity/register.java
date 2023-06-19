package com.example.thanh.activity;

import static com.example.thanh.retrofit.RetrofitClient.getRetrofitInstance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.thanh.retrofit.ApiService;
import com.example.thanh.R;
import com.example.thanh.model.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class register  extends AppCompatActivity {
    private TextView loginTextView;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private Switch roleSwitch;
    private ApiService userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Retrofit retrofit = getRetrofitInstance();

        userApi = retrofit.create(ApiService.class);

        loginTextView = findViewById(R.id.loginTextView);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        roleSwitch = findViewById(R.id.roleSwitch);
        registerButton = findViewById(R.id.registerButton);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public void register () {
        String email = emailEditText.getText().toString();
        String phoneNumber = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Integer role = roleSwitch.isChecked() ? 1 : 2;

        User user = new User();
        user.setEmail(email);
        user.setPhonenumber(phoneNumber);
        user.setPassword(password);
        user.setRole(role);

        Call<User> call = userApi.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User User1 = response.body();
                    String jsonString = new Gson().toJson(User1);
                    Log.d("RES", jsonString);
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(register.this, "Lỗi: " +"Register fail", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(register.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
