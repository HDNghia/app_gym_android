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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thanh.R;
import com.example.thanh.adapter.PostItemAdapter;
import com.example.thanh.model.LikeRequest;
import com.example.thanh.model.Post;
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
import com.google.gson.Gson;
//import com.example.thanh.databinding.PostBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class post_user_gets extends NavActivity implements PostItemAdapter.OnCommentClickListener, PostItemAdapter.OnLikeClickListener {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.post;
    }

    @Override
    protected int getCheckedNavigationItemId() {
        return R.id.nav_home;
    }

    private ListView feedsListView;
    private DatabaseHelper databaseHelper;
//    private ArrayAdapter<String> feedsAdapter;
    private PostItemAdapter adapter;
    private List<Post> PostList;
    private ApiService apiService;
    private EditText postEditText;
    private ImageView selectImage;
    private Button btnPost;

//    private PostBinding binding;
    private Uri imageUri;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    private ImageView firebaseimage;
    private List<User> ul;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("bug", "Vô post");
//        binding = PostBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        feedsListView = findViewById(R.id.feedsListView);
        postEditText = findViewById(R.id.postEditText);
        btnPost = findViewById(R.id.btnPost);
        selectImage = findViewById(R.id.selectImage);
        firebaseimage = findViewById(R.id.firebaseimage);

        // Khởi tạo Retrofit
        Retrofit retrofit = getRetrofitInstance();

        // Khởi tạo ApiService
        apiService = retrofit.create(ApiService.class);
        databaseHelper = new DatabaseHelper(this);
        ul = databaseHelper.getAllUser();

        getAllPost(apiService);
        btnPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("bug", "vô select iamge");
                uploadImage();
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bug", "vô select iamge");
                selectImage();
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
                            firebaseimage.setImageURI(null);
                            Toast.makeText(post_user_gets.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                            firebaseimage.setVisibility(View.GONE);
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(post_user_gets.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
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
                        post(apiService, postEditText.getText().toString(), downloadUri);

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }else {
            imageUri = Uri.parse("null");
            post(apiService, postEditText.getText().toString(), imageUri);
        }
        }
    private void selectImage() {
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
            firebaseimage.setVisibility(View.VISIBLE);
            firebaseimage.setImageURI(imageUri);
        }
    }
    private void getAllPost(ApiService apiService) {
        Call<List<Post>> call = apiService.getPostList();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    PostList = response.body();
                    Log.d("bug", String.valueOf(new Gson().toJson(PostList)));
//                    // Tạo adapter và gán cho ListView
                    adapter = new PostItemAdapter(post_user_gets.this, PostList);
                    adapter.setOnCommentClickListener(post_user_gets.this); // Đăng ký listener
                    adapter.setOnLikeClickListener(post_user_gets.this); // Đăng ký listener
                    feedsListView.setAdapter(adapter);
                } else {
                    Toast.makeText(post_user_gets.this, "Failed to get post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(post_user_gets.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void post(ApiService apiService, String postt, Uri downloadUri) {

        // Tạo object Message

        Post post = new Post();
        post.setOwnerId(ul.get(0).get_id());
        post.setCaption(postt);
        post.setCreatedDate(1);
        post.setAttachmentId1(String.valueOf(downloadUri));

        // Gửi yêu cầu POST
        Call<Post> call = apiService.postPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    Post post = response.body();
//                    Log.d("bug", String.valueOf(new Gson().toJson(postedFoodUser)));
                    getAllPost(apiService);
                    Toast.makeText(post_user_gets.this, "Post successfully", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    // Xử lý lỗi khi gửi yêu cầu POST
                    Toast.makeText(post_user_gets.this, "Failed to post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi trong quá trình gửi yêu cầu POST
                Toast.makeText(post_user_gets.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCommentClick(int postId) {
        // Xử lý sự kiện khi người dùng nhấp vào một cuộc trò chuyện
        Intent intent = new Intent(post_user_gets.this, CommentActivity.class);
//         Khởi chạy Intent
        intent.putExtra("post_id", postId);
        intent.putExtra("user_id",  ul.get(0).get_id());
        startActivity(intent);
    }

    @Override
    public void onLikeClick(int postId) {
        LikeRequest likeRequest = new LikeRequest(postId, ul.get(0).get_id());

        // Gửi POST request
        Call<Void> call = apiService.likePost(likeRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xử lý khi request thành công
//                    adapter.setNotifyOnChange(true);
                    getAllPost(apiService);
                } else {
                    // Xử lý khi request thất bại
                    Toast.makeText(post_user_gets.this, "Failed to like post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý khi request gặp lỗi
                Toast.makeText(post_user_gets.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
