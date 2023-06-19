package com.example.thanh.activity;

import static com.example.thanh.retrofit.RetrofitClient.getRetrofitInstance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanh.R;
import com.example.thanh.adapter.FoodUserListAdapter;
import com.example.thanh.model.FoodUser;
import com.example.thanh.retrofit.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class food_user_gets extends NavActivity {
    private ListView listView;
    private FoodUserListAdapter adapter;
    private List<FoodUser> foodUsersList;
    private ApiService apiService;
    private ImageView btn_breakfast;
    private ImageView btn_lunch;
    private ImageView btn_afternoon;
    private ImageView btn_dinner;
    private TextView sessionTitle;
    private TextView sumKcalDay;
    private TextView sumKcalSession;
    private float sumKcalS = 0;
    private float sumKcalD = 0;
    private ImageView addNewFood;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.food_user;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_food;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = findViewById(R.id.list_food);
        btn_breakfast = findViewById(R.id.btn_breakfast);
        btn_lunch = findViewById(R.id.btn_lunch);
        btn_afternoon = findViewById(R.id.btn_afternoon);
        btn_dinner = findViewById(R.id.btn_dinner);
        sessionTitle = findViewById(R.id.sessionTitle);
        sumKcalDay = findViewById(R.id.sumkcal);
        sumKcalSession = findViewById(R.id.sumkcalsession);
        addNewFood = findViewById(R.id.addNewFood);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        Log.d("bug", "vô view Food");
        // Khởi tạo Retrofit
        Retrofit retrofit = getRetrofitInstance();

        // Khởi tạo ApiService
        apiService = retrofit.create(ApiService.class);

        // Lấy danh sách conversations từ API
        int foodUserId = 1; // Thay đổi giá trị ID theo ý muốn
        getFoodUser(apiService, foodUserId);
        getFoodUserBySession(apiService, foodUserId, "breakfast");

        btn_breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFoodUserBySession(apiService, foodUserId, "breakfast");
                sessionTitle.setText("Breakfast");
            }
        });
        btn_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFoodUserBySession(apiService, foodUserId, "lunch");
                sessionTitle.setText("Lunch");
            }
        });
        btn_afternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFoodUserBySession(apiService, foodUserId, "afternoon");
                sessionTitle.setText("Afternoon");
            }

        });
        btn_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFoodUserBySession(apiService, foodUserId, "dinner");
                sessionTitle.setText("dinner");
            }
        });
        addNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(food_user_gets.this, AddNewFoodUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getFoodUserBySession(ApiService apiService, int id, String Session) {
        Call<List<FoodUser>> call = apiService.getFoodUserBySession(id, Session);
        sumKcalS = 0;
//        Log.d("bug", call.request().url().toString());
        call.enqueue(new Callback<List<FoodUser>>() {
            @Override
            public void onResponse(Call<List<FoodUser>> call, Response<List<FoodUser>> response) {
                if (response.isSuccessful()) {
                    foodUsersList = response.body();
                    for(int i=0 ;i < foodUsersList.size() ; i++){
//                        Log.d("bug", String.valueOf(new Gson().toJson(foodUsersList.get(i).getFoodInfo().getKcal())));
                        sumKcalS = sumKcalS + foodUsersList.get(i).getFoodInfo().getKcal();
                    }
                    sumKcalSession.setText( sumKcalS + " Calories");

//                    Log.d("bug", String.valueOf(new Gson().toJson(foodUsersList)));
                    // Tạo adapter và gán cho ListView
                    adapter = new FoodUserListAdapter(food_user_gets.this, foodUsersList);
//                    adapter.setOnConversationClickListener(MainActivity.this); // Đăng ký listener
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(food_user_gets.this, "Failed to get foodUser", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodUser>> call, Throwable t) {
                Toast.makeText(food_user_gets.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("bug", t.getMessage());
            }
        });
    }
    private void getFoodUser(ApiService apiService, int id) {
        Call<List<FoodUser>> call = apiService.getFoodUser(id);
        sumKcalD = 0;
        call.enqueue(new Callback<List<FoodUser>>() {
            @Override
            public void onResponse(Call<List<FoodUser>> call, Response<List<FoodUser>> response) {
                if (response.isSuccessful()) {
                    foodUsersList = response.body();
                    for(int i=0 ;i < foodUsersList.size() ; i++){
                        sumKcalD = sumKcalD + foodUsersList.get(i).getFoodInfo().getKcal();
                    }
                    sumKcalDay.setText( sumKcalD + " Calories");

                } else {
                    Toast.makeText(food_user_gets.this, "Failed to get foodUser", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodUser>> call, Throwable t) {
                Toast.makeText(food_user_gets.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("bug", t.getMessage());
            }
        });
    }

}