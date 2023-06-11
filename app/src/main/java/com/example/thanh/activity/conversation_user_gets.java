package com.example.thanh.activity;

import static com.example.thanh.retrofit.RetrofitClient.getRetrofitInstance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thanh.R;
import com.example.thanh.adapter.ConversationAdapter;
import com.example.thanh.model.Conversation;
import com.example.thanh.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class conversation_user_gets extends NavActivity implements ConversationAdapter.OnConversationClickListener {
    private ListView listView;
    private List<Conversation> conversationList;
    private ConversationAdapter adapter;
    private EditText searchEditText;
    private ApiService apiService;
    private ImageView addNewPartner;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.conversation_user_gets;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_mess;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = findViewById(R.id.list_view);
        searchEditText = findViewById(R.id.searchEditText);
        addNewPartner = findViewById(R.id.addNewPartner);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        // Khởi tạo Retrofit
        Retrofit retrofit = getRetrofitInstance();

        // Khởi tạo ApiService
        apiService = retrofit.create(ApiService.class);

        // Lấy danh sách conversations từ API
        int conversationId = 1; // Thay đổi giá trị ID theo ý muốn
        getConversation(apiService, conversationId);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần thực hiện hành động gì trước khi văn bản thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Thực hiện hành động khi văn bản thay đổi
                if(searchEditText.getText().toString().length() != 0){
                    Log.d("bug", searchEditText.getText().toString());
                    fetchConversation(apiService, conversationId, searchEditText.getText().toString());
                }else{
                    getConversation(apiService, conversationId);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần thực hiện hành động gì sau khi văn bản thay đổi
            }
        });

        addNewPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(conversation_user_gets.this, "Vo ne", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(conversation_user_gets.this, AddNewConversation.class);
                startActivity(intent);
            }
        });

    }
    private void fetchConversation(ApiService apiService,int conversationId, String name) {
        Call<List<Conversation>> call = apiService.searchConversation(conversationId, name);
        call.enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                if (response.isSuccessful()) {
                    conversationList = response.body();
                    // Xử lý dữ liệu conversation
                    // Tạo adapter và gán cho ListView
                    adapter = new ConversationAdapter(conversation_user_gets.this, conversationList);
                    adapter.setOnConversationClickListener(conversation_user_gets.this); // Đăng ký listener
                    listView.setAdapter(adapter);
                } else {
                    // Xử lý khi response không thành công
                    Toast.makeText(conversation_user_gets.this, "Failed to get conversation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Toast.makeText(conversation_user_gets.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getConversation(ApiService apiService, int id) {
        Log.d("bug", "vô");
        Call<List<Conversation>> call = apiService.getConversation(id);
        call.enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                Log.d("bug", "vô");
                if (response.isSuccessful()) {
                    conversationList = response.body();

                    // Tạo adapter và gán cho ListView
                    adapter = new ConversationAdapter(conversation_user_gets.this, conversationList);
                    adapter.setOnConversationClickListener(conversation_user_gets.this); // Đăng ký listener
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(conversation_user_gets.this, "Failed to get conversation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
                Toast.makeText(conversation_user_gets.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public void onConversationClick(int conversationId, int userId) {
        // Xử lý sự kiện khi người dùng nhấp vào một cuộc trò chuyện
        Intent intent = new Intent(conversation_user_gets.this, message_user_get.class);
//         Khởi chạy Intent
        intent.putExtra("conversation_id", conversationId);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }
}
