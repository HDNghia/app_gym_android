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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.R;
import com.example.thanh.model.User;
import com.example.thanh.retrofit.ApiService;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private ImageView selectImage;
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
    private Button btnHistory;
    private List<User> ul;
    private Uri imageUri;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    private ImageView btnSelectImage;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.profile_user_get);

        databaseHelper = new DatabaseHelper(this);
        btnUpdate = findViewById(R.id.btnUpdate);
        selectImage = findViewById(R.id.imageViewAva);
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
        btnHistory = findViewById(R.id.btnHistory);
        btnSelectImage = findViewById(R.id.btnEditImage);

        ul = databaseHelper.getAllUser();
        Log.d("Role", String.valueOf(ul.get(0).getRole()));
        if(ul.get(0).getRole() == 1){
            reCharGer.setVisibility(View.VISIBLE);
        }
        Log.d("Tuan", ul.get(0).getAvt());
        if(ul.get(0).getRole() == 2){
            withDraw.setVisibility(View.VISIBLE);
        }
        Log.d("bug", String.valueOf(ul.get(0).getAvt()));
        Log.d("bug", "vo profile");
        if(ul.get(0).getAvt().equals("")){

        }else {
            String imageUrl = ul.get(0).getAvt();
            Picasso.get()
                    .load(imageUrl)
//                .resize(567, 499)
                    .resize(52, 37)
                    .into(selectImage);
        }


        // Khởi tạo Retrofit
        Retrofit retrofit = getRetrofitInstance();

        // Khởi tạo ApiService
        apiService = retrofit.create(ApiService.class);

        fetchApi();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
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
        reCharGer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_user_get.this, invoicePost.class);
                startActivity(intent);
            }
        });
        withDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_user_get.this, invoicePost.class);
                startActivity(intent);
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_user_get.this, Invoice_user_gets.class);
                startActivity(intent);
            }
        });

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back to the previous screen
            }
        });
    }
    private void uploadImage(){
        if(imageUri != null){
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
                            Toast.makeText(profile_user_get.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                            selectImage.setVisibility(View.GONE);
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(profile_user_get.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
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
                    Log.d("bug", String.valueOf(ref.getDownloadUrl()));
                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.d("bug", String.valueOf(downloadUri));
                        updateApi(apiService, downloadUri);

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }else {
            imageUri = Uri.parse("");
            updateApi(apiService, imageUri);
        }
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
    private void updateApi(ApiService apiService,  Uri downloadUri){
        // Tạo đối tượng User với các thuộc tính được chỉ định
        User user = new User();
        user.setAge(ul.get(0).getAge());
        user.setHeight(ul.get(0).getHeight());
        user.setWeight(ul.get(0).getWeight());
        user.setWorkingLevel(ul.get(0).getWorkingLevel());
        if(downloadUri.equals("")){
            user.setAvt(ul.get(0).getAvt());
            Log.d("bug","vo update 1");
            Log.d("bug", String.valueOf(downloadUri));
        }else {
            Log.d("bug","vo update 2");
            Log.d("bug", String.valueOf(downloadUri));
            user.setAvt(String.valueOf(downloadUri));
        }
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
                    fetchApi();
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

}
