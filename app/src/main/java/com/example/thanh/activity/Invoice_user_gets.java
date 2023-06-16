package com.example.thanh.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.adapter.invoiceAdapter;
import com.example.thanh.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class Invoice_user_gets extends AppCompatActivity {
    private ListView listViewTransactions;
    private invoiceAdapter adapter;
    private List<Invoice> invoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_list);

        // Ánh xạ ListView từ file XML
        listViewTransactions = findViewById(R.id.list_invoice);

        // Khởi tạo danh sách transactions
        invoices = new ArrayList<>();
        invoices.add(new Invoice(1, 1, 1, 1, 1, "", "nghia", 1));

        // Thêm các transaction vào danh sách (thông qua API hoặc dữ liệu khác)

        // Khởi tạo adapter và gán cho ListView
        adapter = new invoiceAdapter(this, invoices);
        listViewTransactions.setAdapter(adapter);
    }
}
