package com.example.thanh.activity;

import static com.example.thanh.activity.RandomString.randomNumber;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class forgotpassword extends AppCompatActivity {
    private EditText emailEditText, otpEditText, newPasswordEditText, confirmPasswordEditText;
    private Button nextButton, otpNextButton, resetButton;
    private TextView wrongcode;
    private String emailUser;
    private ApiService userApi;
    private String optMain;
    public void sendEmail(String recipientEmail, String subject, String body) {
        Log.d("Tuan", recipientEmail);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        Retrofit retrofit = getRetrofitInstance();
        userApi = retrofit.create(ApiService.class);

        emailEditText = findViewById(R.id.emailEditText);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tuan", "Here1");
                int otp = randomNumber(100000, 999999);
                optMain = otp + "";
                Log.d("Tuan", optMain);
                String subject = "Recovery Password";
                emailUser = String.valueOf(emailEditText.getText());
                Call<Void> call = userApi.verifyEmailOtp(emailUser, optMain);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            setContentView(R.layout.forgot_password_otp);
                            otpEditText = findViewById(R.id.otpEditText);
                            otpNextButton = findViewById(R.id.otpNextButton);
                            wrongcode = findViewById(R.id.wrongcode);
                            otpNextButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (String.valueOf(otpEditText.getText()).equals(optMain)) {
                                        setContentView(R.layout.forgot_password_reset);

                                        newPasswordEditText = findViewById(R.id.newPasswordEditText);
                                        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
                                        resetButton = findViewById(R.id.resetButton);

                                        resetButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                changePassword(emailUser, String.valueOf(newPasswordEditText.getText()));
                                                Intent intent = new Intent(forgotpassword.this, login.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    } else {
                                        wrongcode.setVisibility(View.VISIBLE);
                                    }

                                }
                            });
                            Log.d("API", "Success");
                        } else {
                            Log.d("API", "Error: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("API", "Error: " + t.getMessage());
                    }
                });
            }
        });
    }
    public void changePassword (String email, String pass) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(pass);

        Call<User> call = userApi.changepass(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(forgotpassword.this, "Success: " +"Change password successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(forgotpassword.this, login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(forgotpassword.this, "Fail: " +"Change password fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(forgotpassword.this, "Bug: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
