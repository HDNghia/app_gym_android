package com.example.thanh.activity;
import static com.example.thanh.retrofit.RetrofitClient.getRetrofitInstance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.model.User;
import com.example.thanh.retrofit.ApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class profile_user_get  extends NavActivity{
    @Override
    protected int getLayoutResourceId() {
        return R.layout.profile_user_get;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_home;
    }

    private DatabaseHelper databaseHelper;
    private EditText firstNameEditText;
    private EditText LastNameEditText;
    private ImageView avatarImageView;
    private TextView bankNameTextView;
    private TextView workingLevelTextView;
    private TextView walletTextView;
    private EditText genderEditText;
    private TextView emailTextView;
    private EditText phoneEditText;
    private EditText addressEditText;
    private ApiService apiService;
    private Button btnUpdate;
    private Button reCharGer;
    private Button withDraw;
    private List<User> ul;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.profile_user_get);

        databaseHelper = new DatabaseHelper(this);

        btnUpdate = findViewById(R.id.btnUpdate);
        avatarImageView = findViewById(R.id.imageViewAva);
        bankNameTextView = findViewById(R.id.bankName);
        workingLevelTextView = findViewById(R.id.workingLevel);
        walletTextView = findViewById(R.id.walletTextView);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        genderEditText = findViewById(R.id.genderEditText);
        emailTextView = findViewById(R.id.emailTextView);
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);
        LastNameEditText = findViewById(R.id.LastNameEditText);
        reCharGer = findViewById(R.id.reCharGer);
        withDraw = findViewById(R.id.withDraw);

        ul = databaseHelper.getAllUser();
        if(ul.get(0).getRole() == 1){
            reCharGer.setVisibility(View.VISIBLE);

        }
        if(ul.get(0).getRole() == 2){
            withDraw.setVisibility(View.VISIBLE);
        }

        String imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8fA%3D%3D&w=1000&q=80";

        Picasso.get()
                .load(imageUrl)
//                .resize(567, 499)
                .resize(52, 37)
                .into(avatarImageView);


        // Khởi tạo Retrofit
        Retrofit retrofit = getRetrofitInstance();

        // Khởi tạo ApiService
        apiService = retrofit.create(ApiService.class);

        fetchApi();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateApi();
            }
        });
        reCharGer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_user_get.this, Invoice_user_gets.class);
                startActivity(intent);
            }
        });
    }

    // Phương thức giả lập dữ liệu đối tượng ProfileUserGet (thay thế bằng dữ liệu thật)
    private void fetchApi() {
        Log.d("bug", "voo");

//        User profile = new User();

        // Gọi API để lấy thông tin người dùng
        Call<User> call = apiService.getUser(ul.get(0).get_id());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Dữ liệu người dùng nhận được từ API
                    User profile = response.body();
                    Log.d("bug", "voo1");
                    // Truy cập các thuộc tính
                    bankNameTextView.setText(profile.getBankName());
                    workingLevelTextView.setText(String.valueOf(profile.getWorkingLevel()));
                    walletTextView.setText(String.valueOf(profile.getWallet()));
                    firstNameEditText.setText(profile.getFirstname());
                    genderEditText.setText(profile.getGender());
                    emailTextView.setText(profile.getEmail());
                    phoneEditText.setText(profile.getPhonenumber());
                    addressEditText.setText(profile.getAddress());
                    LastNameEditText.setText(profile.getLastname());

                } else {
                    System.out.println("Failed to get user data. Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("Failed to get user data. Error: " + t.getMessage());
            }
        });

    }
    private void updateApi(){
        // Tạo đối tượng User với các thuộc tính được chỉ định
        User user = new User();
        user.setAge(ul.get(0).getAge());
        user.setHeight(ul.get(0).getHeight());
        user.setWeight(ul.get(0).getWeight());
        user.setWorkingLevel(ul.get(0).getWorkingLevel());
        user.setAvt(ul.get(0).getAvt());
        user.setCoverId(ul.get(0).getCoverId());
        user.setWallet(ul.get(0).getWallet());
//        user.setIsBan(false);
        user.setStatus(ul.get(0).getStatus());
        user.setLastActive(ul.get(0).getLastActive());
        user.setBankAccount(ul.get(0).getBankAccount());
        user.setBankName(ul.get(0).getBankName());
        user.setDescription1(ul.get(0).getDescription1());
        user.setDescription2(ul.get(0).getDescription2());
        user.setSpecialize(ul.get(0).getSpecialize());
        user.set_id(ul.get(0).get_id());
        user.setFirstname(String.valueOf(firstNameEditText.getText()));
        user.setLastname(String.valueOf(LastNameEditText.getText()));
        user.setGender(String.valueOf(genderEditText.getText()));
        user.setEmail(ul.get(0).getEmail());
        user.setPhonenumber(String.valueOf(phoneEditText.getText()));
        user.setAddress(String.valueOf(addressEditText.getText()));
        user.set__v(0);

        // Gửi yêu cầu PUT API
        Call<Void> call = apiService.updateUser(ul.get(0).get_id(), user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(profile_user_get.this, "Update SuccessFully", Toast.LENGTH_SHORT).show();
                    // Xử lý thành công
                } else {
                    // Xử lý lỗi
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }

}
