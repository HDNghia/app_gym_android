package com.example.thanh.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.activity.AddNewFoodUserActivity;
import com.example.thanh.model.FoodItem;
import com.example.thanh.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodListAdapter extends ArrayAdapter<FoodItem> {
    private Context context;
    private List<FoodItem> FoodList;

    public FoodListAdapter(Context context, List<FoodItem> foodItems) {
        super(context, 0, foodItems);
        this.context = context;
        this.FoodList = foodItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        FoodItem foodItem = FoodList.get(position);

        // Kiểm tra nếu view chưa được tái sử dụng, inflate layout
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.food_item, parent, false);
        }

        // Ánh xạ các phần tử trong list_item_food.xml\
        ImageView imageView = view.findViewById(R.id.foodImageView);
//        ImageView imageView = view.findViewById(R.id.userImageView);
//        String imageUrl = foodItem.getAttachment();
        String imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8fA%3D%3D&w=1000&q=80";

        Picasso.get()
                .load(imageUrl)
//                .resize(567, 499)
                .resize(52, 37)
                .into(imageView);

        TextView foodIdTextView = view.findViewById(R.id.text_id);
        TextView foodNameTextView = view.findViewById(R.id.text_name);

//        TextView foodNutritionTextView = view.findViewById(R.id.text_nutrition);
        TextView foodKcalTextView = view.findViewById(R.id.text_kcal);

        // Đặt dữ liệu vào các phần tử
        foodIdTextView.setText(foodItem.get_id() + "");

        foodNameTextView.setText(foodItem.getName());

//        foodNutritionTextView.setText(foodItem.getNutrition());

        foodKcalTextView.setText(foodItem.getKcal() + " Calories");
        String foodName = foodItem.getName();
        Integer foodId = foodItem.get_id();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onFoodClick(foodName, foodId);
                }
            }
        });
        return view;
    }

    public interface OnFoodClickListener {
        void onFoodClick(String foodName, int foodId);
    }
    private OnFoodClickListener clickListener;

    public void setOnFoodClickListener(OnFoodClickListener listener) {
        this.clickListener = listener;
    }
}
