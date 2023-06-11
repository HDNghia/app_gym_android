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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.adapter.FoodListAdapter;
import com.example.thanh.model.FoodItem;
import com.example.thanh.model.FoodUser;
import com.example.thanh.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddNewFoodUserActivity extends AppCompatActivity {

    private FoodListAdapter adapter;
    private EditText foodIdEditText;
    private EditText userIdEditText;
    private EditText useDatetimeEditText;
    private Spinner  sessionsSpinner;
    private Button AddNewFoodUser;
    private ListView listFood;
    private Button AddNewFood;
    private ApiService apiService;

    private List<FoodItem> foodItemList;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_fooduser);

        // Ánh xạ các view từ layout
        foodIdEditText = findViewById(R.id.foodIdEditText);
        userIdEditText = findViewById(R.id.userIdEditText);
        useDatetimeEditText = findViewById(R.id.useDatetimeEditText);
        sessionsSpinner = findViewById(R.id.sessionsSpinner);
        AddNewFoodUser = findViewById(R.id.AddNewFoodUser);
        listFood = findViewById(R.id.listFood);
        AddNewFood = findViewById(R.id.AddFood);

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
        getFood(apiService);

//        String selectedValue = sessionsSpinner.getSelectedItem().toString();
        AddNewFoodUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postMessageOfUser(apiService);
            }
        });

        AddNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewFoodUserActivity.this, AddNewFood.class);
                startActivity(intent);
            }
        });

    }

    private void getFood(ApiService apiService) {
        Call<List<FoodItem>> call = apiService.getFoodList();
        Log.d("bug", call.request().url().toString());
        call.enqueue(new Callback<List<FoodItem>>() {
            @Override
            public void onResponse(Call<List<FoodItem>> call, Response<List<FoodItem>> response) {
                if (response.isSuccessful()) {
                    foodItemList = response.body();
//                    Log.d("bug", String.valueOf(new Gson().toJson(foodItemList)));
                    adapter = new FoodListAdapter(AddNewFoodUserActivity.this, foodItemList);
//                    adapter.setOnConversationClickListener(MainActivity.this); // Đăng ký listener
                    listFood.setAdapter(adapter);
                } else {
                    Toast.makeText(AddNewFoodUserActivity.this, "Failed to get foodUser", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodItem>> call, Throwable t) {
                Toast.makeText(AddNewFoodUserActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("bug", t.getMessage());
            }
        });
    }

    private void postMessageOfUser(ApiService apiService) {

        // Tạo object Message

        FoodUser foodUser = new FoodUser();
        foodUser.setFoodId(Integer.parseInt(foodIdEditText.getText().toString()));
        foodUser.setUserId(Integer.parseInt(userIdEditText.getText().toString()));
        foodUser.setUseDatetime(Integer.parseInt(useDatetimeEditText.getText().toString()));
        foodUser.setSession(sessionsSpinner.getSelectedItem().toString());

        // Gửi yêu cầu POST
        Call<FoodUser> call = apiService.postFoodUser(foodUser);
        Log.d("bug", "voo");
        call.enqueue(new Callback<FoodUser>() {
            @Override
            public void onResponse(Call<FoodUser> call, Response<FoodUser> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    FoodUser postedFoodUser = response.body();
//                    Log.d("bug", String.valueOf(new Gson().toJson(postedFoodUser)));
                    Toast.makeText(AddNewFoodUserActivity.this, "Post foodUser successfully", Toast.LENGTH_SHORT).show();

//                    }
                } else {
                    // Xử lý lỗi khi gửi yêu cầu POST
                    Toast.makeText(AddNewFoodUserActivity.this, "Failed to post foodUser", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodUser> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi trong quá trình gửi yêu cầu POST
                Toast.makeText(AddNewFoodUserActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}