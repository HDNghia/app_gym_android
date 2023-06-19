package com.example.thanh.activity;

import static com.example.thanh.retrofit.RetrofitClient.getRetrofitInstance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.model.Invoice;
import com.example.thanh.model.User;
import com.example.thanh.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class invoicePost extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText tranQtyEditText;
    private EditText tranContentEditText;
    private Button postButton;
    private ApiService apiService;
    private List<User> ul;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_invoice);

        // Liên kết các thành phần giao diện với mã Java
        databaseHelper = new DatabaseHelper(this);
        tranQtyEditText = findViewById(R.id.tranQtyEditText);
        tranContentEditText = findViewById(R.id.tranContentEditText);
        postButton = findViewById(R.id.postButton);

        // Khởi tạo Retrofit
        Retrofit retrofit = getRetrofitInstance();
        ul = databaseHelper.getAllUser();


        // Khởi tạo ApiService
        apiService = retrofit.create(ApiService.class);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thực hiện fetch API POST
                createInvoice();
            }
        });
        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

    }

    private void createInvoice() {
        Invoice invoice = new Invoice();
        invoice.set_id(1);
        invoice.setUserId(ul.get(0).get_id());
        invoice.setTranQty( Integer.parseInt(String.valueOf(tranQtyEditText.getText())));
        invoice.setCourseId(0);
        invoice.setTranDate(1683103370);
        invoice.setStatus(0);
        invoice.setTranContent(String.valueOf(tranContentEditText.getText()));
        invoice.setNote("");
        if (ul.get(0).getRole() == 1){
            invoice.setTranType(1);
        } else
        {
            invoice.setTranType(0);
        }

        Call<Invoice> call = apiService.createInvoice(invoice);
        call.enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if (response.isSuccessful()) {
                    Invoice createdInvoice = response.body();
                    Toast.makeText(invoicePost.this, "create invoice successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(invoicePost.this, profile_user_get.class);
                    startActivity(intent);
                    // Xử lý kết quả trả về nếu cần thiết
                } else {
                    Toast.makeText(invoicePost.this, "Failed to create invoice", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                Toast.makeText(invoicePost.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}

