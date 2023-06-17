package com.example.thanh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thanh.R;
import com.example.thanh.model.Invoice;

import java.util.List;

public class invoiceAdapter extends ArrayAdapter<Invoice> {

    private Context context;
    private List<Invoice> Invoices;

    public invoiceAdapter(Context context, List<Invoice> invoice) {
        super(context, 0, invoice);
        this.context = context;
        this.Invoices = invoice;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.item_invoice, parent, false);
        }

        // Lấy transaction tại vị trí hiện tại
        Invoice currentInvoice = Invoices.get(position);

        // Ánh xạ các thành phần trong item layout
        TextView textViewTranQty = listItemView.findViewById(R.id.textViewTranQty);
        TextView textViewTranDate = listItemView.findViewById(R.id.textViewTranDate);
        TextView textViewNote = listItemView.findViewById(R.id.textViewNote);
        TextView textViewTranType = listItemView.findViewById(R.id.textViewTranType);
        TextView textViewStatus = listItemView.findViewById(R.id.textViewStatus);

        Date date = new Date(currentInvoice.getTranDate());
        // Định dạng ngày tháng
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Chuyển đổi Date thành chuỗi ngày tháng
        String dateString = sdf.format(date);
        // Đặt giá trị cho các thành phần trong item layout từ transaction hiện tại
        textViewTranQty.setText("Quantity: " + currentInvoice.getTranQty());
        textViewTranDate.setText("Date: " + dateString);
        textViewNote.setText("Note: " + currentInvoice.getNote());
        textViewTranType.setText("Type: " + currentInvoice.getTranType());
        textViewStatus.setText("Status: " + currentInvoice.getStatus());

        return listItemView;
    }
}
