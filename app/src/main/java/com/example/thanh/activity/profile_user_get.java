package com.example.thanh.activity;
import static com.example.thanh.retrofit.RetrofitClient.getRetrofitInstance;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.model.User;
import com.example.thanh.retrofit.ApiService;
import com.squareup.picasso.Picasso;

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

    private ImageView btnEditFirstName;
    private EditText firstNameEditText;
    private ImageView avatarImageView;
    private TextView nameTextView;
    private TextView bankNameTextView;
    private TextView bankNumberTextView;
    private TextView workingLevelTextView;
    private TextView walletTextView;
    private TextView firstNameTextView;
    private TextView genderTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView addressTextView;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.profile_user_get);


        btnEditFirstName = findViewById(R.id.btnEditFirstName);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        avatarImageView = findViewById(R.id.imageViewAva);
        nameTextView = findViewById(R.id.nameTextView);
        bankNameTextView = findViewById(R.id.bankName);
        bankNumberTextView = findViewById(R.id.BankNumber);
        workingLevelTextView = findViewById(R.id.workingLevel);
        walletTextView = findViewById(R.id.walletTextView);
        firstNameTextView = findViewById(R.id.firstNameTextView);
        genderTextView = findViewById(R.id.GenderTextView);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        addressTextView = findViewById(R.id.addressTextViewt);

//        avatarImageView = findViewById(R.id.foodImageView);
//        ImageView imageView = view.findViewById(R.id.userImageView);
//        String imageUrl = foodItem.getAttachment();
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

        btnEditFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstNameEditText.setVisibility(View.VISIBLE);
                firstNameTextView.setVisibility(View.GONE);
            }
        });
    }

    // Phương thức giả lập dữ liệu đối tượng ProfileUserGet (thay thế bằng dữ liệu thật)
    private void fetchApi() {

//        User profile = new User();

        // Gọi API để lấy thông tin người dùng
        Call<User> call = apiService.getUser(1);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Dữ liệu người dùng nhận được từ API
                    User profile = response.body();

                    // Truy cập các thuộc tính
                    nameTextView.setText(profile.getFirstname() + " " + profile.getLastname());
                    bankNameTextView.setText(profile.getBankName());
                    bankNumberTextView.setText(profile.getBankAccount());
                    workingLevelTextView.setText(String.valueOf(profile.getWorkingLevel()));
                    walletTextView.setText(String.valueOf(profile.getWallet()));
                    firstNameTextView.setText(profile.getFirstname());
                    genderTextView.setText(profile.getGender());
                    emailTextView.setText(profile.getEmail());
                    phoneTextView.setText(profile.getPhonenumber());
                    addressTextView.setText(profile.getAddress());

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
}
