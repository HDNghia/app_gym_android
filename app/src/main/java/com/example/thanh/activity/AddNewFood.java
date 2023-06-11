package com.example.thanh.activity;
import static com.example.thanh.retrofit.RetrofitClient.getRetrofitInstance;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.model.FoodItem;
import com.example.thanh.R;
import com.example.thanh.retrofit.ApiService;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddNewFood extends AppCompatActivity {

    private EditText ingredientsEditText;
    private EditText nameEditText;
    private EditText nutritionEditText;
    private EditText recipeEditText;
    private EditText kcalEditText;
    private Button addNewFood;
    private Button btnSelectImage;
    private ImageView selectImage;
    private ApiService apiService;

    private Uri imageUri;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_food);

        ingredientsEditText = findViewById(R.id.ingredientsEditText);
        nameEditText = findViewById(R.id.nameEditText);
        nutritionEditText = findViewById(R.id.nutritionEditText);
        recipeEditText = findViewById(R.id.recipeEditText);
        kcalEditText = findViewById(R.id.kcalEditText);
        addNewFood = findViewById(R.id.addNewFood);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        selectImage = findViewById(R.id.selectImage);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });

        Retrofit retrofit = getRetrofitInstance();

        // Tạo đối tượng FoodApiService
        apiService = retrofit.create(ApiService.class);

        addNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();

            }
        });
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
    }

    private void uploadImage(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        selectImage.setImageURI(null);
                        Toast.makeText(AddNewFood.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        selectImage.setVisibility(View.GONE);
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(AddNewFood.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
                    }
                });
        final StorageReference ref = storageReference.child("images/mountains.jpg");
        UploadTask uploadTask = ref.putFile(imageUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                Log.d("bug",  String.valueOf(ref.getDownloadUrl()) + " 1");
                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.d("bug", String.valueOf(downloadUri));
                    postFood(apiService, downloadUri);
//                    post(apiService, postEditText.getText().toString(), downloadUri);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("bug", "vo cái selected0");
        if (requestCode == 100 && data != null && data.getData() != null){
            Log.d("bug", "vo cái selected");
            imageUri = data.getData();
            selectImage.setVisibility(View.VISIBLE);
            selectImage.setImageURI(imageUri);
        }
    }

    private void postFood(ApiService apiService, Uri downloadUri) {

        // Tạo object Message

        FoodItem foodItem = new FoodItem();
        foodItem.setAttachment(downloadUri.toString());
        foodItem.setIngredients(ingredientsEditText.getText().toString());
        foodItem.setName(nameEditText.getText().toString());
        foodItem.setNutrition(nutritionEditText.getText().toString());
        foodItem.setRecipe(recipeEditText.getText().toString());
        foodItem.setKcal(Integer.parseInt(kcalEditText.getText().toString()));

        // Gửi yêu cầu POST
        Call<FoodItem> call = apiService.addNewFood(foodItem);
        Log.d("bug", "voo");
        call.enqueue(new Callback<FoodItem>() {
            @Override
            public void onResponse(Call<FoodItem> call, Response<FoodItem> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    FoodItem postedFoodUser = response.body();
//                    Log.d("bug", String.valueOf(new Gson().toJson(postedFoodUser)));
                    Toast.makeText(AddNewFood.this, "Post food successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNewFood.this, AddNewFoodUserActivity.class);
                    startActivity(intent);

//                    }
                } else {
                    // Xử lý lỗi khi gửi yêu cầu POST
                    Toast.makeText(AddNewFood.this, "Failed to post food", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodItem> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi trong quá trình gửi yêu cầu POST
                Toast.makeText(AddNewFood.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
