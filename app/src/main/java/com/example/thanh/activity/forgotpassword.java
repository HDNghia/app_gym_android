package com.example.thanh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;

public class forgotpassword extends AppCompatActivity {
    private EditText emailEditText, otpEditText, newPasswordEditText, confirmPasswordEditText;
    private Button nextButton, otpNextButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        emailEditText = findViewById(R.id.emailEditText);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.forgot_password_otp);

                otpEditText = findViewById(R.id.otpEditText);
                otpNextButton = findViewById(R.id.otpNextButton);

                otpNextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.forgot_password_reset);

                        newPasswordEditText = findViewById(R.id.newPasswordEditText);
                        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
                        resetButton = findViewById(R.id.resetButton);

                        resetButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(forgotpassword.this, login.class);
                                startActivity(intent);
                                finish(); // Kết thúc Activity hiện tại để không quay lại trang đổi mật khẩu
                            }
                        });
                    }
                });
            }
        });
    }
}
