package com.example.thanh.activity;

import static com.example.thanh.retrofit.RetrofitClient.getRetrofitInstance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.adapter.invoiceAdapter;
import com.example.thanh.model.Invoice;
import com.example.thanh.model.User;
import com.example.thanh.retrofit.ApiService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Invoice_user_gets extends AppCompatActivity {
    private ListView listViewTransactions;
    private invoiceAdapter adapter;
    private List<Invoice> invoices;
    private ApiService apiService;
    private DatabaseHelper databaseHelper;
    private List<User> ul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_list);

        databaseHelper = new DatabaseHelper(this);
        ul = databaseHelper.getAllUser();

        // Khởi tạo Retrofit
        Retrofit retrofit = getRetrofitInstance();

        // Khởi tạo ApiService
        apiService = retrofit.create(ApiService.class);

        // Ánh xạ ListView từ file XML
        listViewTransactions = findViewById(R.id.list_invoice);

        // Khởi tạo danh sách transactions
//        invoices = new ArrayList<>();
//        invoices.add(new Invoice(1, 1, 1, 1, 1, "", "nghia", 1));

        // Thêm các transaction vào danh sách (thông qua API hoặc dữ liệu khác)
        getAllInvoice(apiService);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

    }
    private void getAllInvoice(ApiService apiService) {
        Call<List<Invoice>> call = apiService.getInvoice(ul.get(0).get_id());
        call.enqueue(new Callback<List<Invoice>>() {
            @Override
            public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
                if (response.isSuccessful()) {
                    invoices = response.body();
                    Log.d("bug", String.valueOf(new Gson().toJson(invoices)));
                    // Tạo adapter và gán cho ListView
                    adapter = new invoiceAdapter(Invoice_user_gets.this, invoices);
                    listViewTransactions.setAdapter(adapter);
                } else {
                    Toast.makeText(Invoice_user_gets.this, "Failed to get invoices", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Invoice>> call, Throwable t) {
                Toast.makeText(Invoice_user_gets.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

}
