package com.example.thanh.activity;

import static com.example.thanh.retrofit.RetrofitClient.getRetrofitInstance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.adapter.ConversationAdapter;
import com.example.thanh.adapter.PartnerAdapter;
import com.example.thanh.model.Conversation;
import com.example.thanh.R;
import com.example.thanh.model.User;
import com.example.thanh.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddNewConversation extends AppCompatActivity implements PartnerAdapter.OnPartnerClickListener {
//    private EditText userIdEditText;
    private EditText partnerName;
    private Integer PartnerId;
//    private EditText lastActiveEditText;
    private EditText nicknameEditText;
    private Button addConversationButton;
    private List<User> PartnerList;
    private ListView listPartner;
    private PartnerAdapter adapter;

    private ApiService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_conversation);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        // Ánh xạ các thành phần UI
//        userIdEditText = findViewById(R.id.userIdEditText);
        partnerName = findViewById(R.id.partnerIdEditText);
//        isGroupConverCheckBox = findViewById(R.id.isGroupConverCheckBox);
//        lastActiveEditText = findViewById(R.id.lastActiveEditText);
        nicknameEditText = findViewById(R.id.nicknameEditText);
        addConversationButton = findViewById(R.id.addConversationButton);
        listPartner = findViewById(R.id.listPartner);
        // Khởi tạo Retrofit
        Retrofit retrofit = getRetrofitInstance();

        // Khởi tạo ApiService
        apiService = retrofit.create(ApiService.class);

        partnerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần thực hiện hành động gì trước khi văn bản thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Thực hiện hành động khi văn bản thay đổi
                if(partnerName.getText().toString().length() != 0){
//                    Log.d("bug", partnerIdEditText.getText().toString());
                    listPartner.setVisibility(View.VISIBLE);
                    fetchPartner(apiService, partnerName.getText().toString());
                }else{
                    listPartner.setVisibility(View.GONE);
//                    getConversation(apiService, conversationId);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần thực hiện hành động gì sau khi văn bản thay đổi
            }
        });

        // Xử lý sự kiện khi nhấn nút "Add Conversation"
        addConversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị từ các trường EditText và CheckBox
                Retrofit retrofit = getRetrofitInstance();
                // Tạo đối tượng FoodApiService
                ApiService apiService = retrofit.create(ApiService.class);

                // Thực hiện xử lý thêm cuộc trò chuyện
                addConversation(apiService);
            }
        });
    }
    private void fetchPartner(ApiService apiService, String name) {
        Call<List<User>> call = apiService.getUserByName(name);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    PartnerList = response.body();
                    // Xử lý dữ liệu conversation
                    // Tạo adapter và gán cho ListView
                    adapter = new PartnerAdapter(AddNewConversation.this, PartnerList);
                    adapter.setOnPartnerClickListener(AddNewConversation.this); // Đăng ký listener
                    listPartner.setAdapter(adapter);
                } else {
                    // Xử lý khi response không thành công
                    Toast.makeText(AddNewConversation.this, "Failed to get conversation", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Toast.makeText(AddNewConversation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addConversation(ApiService apiService) {
        // Gọi API hoặc thực hiện các thao tác để thêm cuộc trò chuyện vào hệ thống
        // ...
//        int userId = Integer.parseInt(userIdEditText.getText().toString());
//        int partnerId = PartnerId;
//        boolean isGroupConver = isGroupConverCheckBox.isChecked();
//        int lastActive = Integer.parseInt(lastActiveEditText.getText().toString());
        String nickname = nicknameEditText.getText().toString();

        Conversation conversation = new Conversation();
        conversation.setUserId(1);
        conversation.setPartnerId(PartnerId);
//        conversation.setGroupConver(isGroupConver);
        conversation.setLastActive(1);
        conversation.setNickname(nickname);

        // Gửi yêu cầu POST
        Call<Conversation> call = apiService.postConversation(conversation);
//        Log.d("bug", "voo");
        call.enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                if (response.isSuccessful()) {
//                    Log.d("bug", "Voo trong response");
                    // Xử lý thành công
                    Conversation postedConversation = response.body();
//                    Log.d("bug", String.valueOf(new Gson().toJson(postedFoodUser)));
                    Toast.makeText(AddNewConversation.this, "Post Conversation successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNewConversation.this, conversation_user_gets.class);
                    startActivity(intent);
//                    }
                } else {
                    // Xử lý lỗi khi gửi yêu cầu POST
                    Toast.makeText(AddNewConversation.this, "Failed to post food", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Conversation> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi trong quá trình gửi yêu cầu POST
                Toast.makeText(AddNewConversation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onPartnerClick(String Name, int partnerId) {
        // Xử lý sự kiện khi người dùng nhấp vào một cuộc trò chuyện
//       Log.d("bug", String.valueOf(partnerId));
        PartnerId = partnerId;
        Toast.makeText(AddNewConversation.this, String.valueOf(Name), Toast.LENGTH_SHORT).show();
        partnerName.setText(Name);
        listPartner.setVisibility(View.GONE);
    }
}

