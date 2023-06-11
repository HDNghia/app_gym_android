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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thanh.adapter.CommentAdapter;
import com.example.thanh.model.PostReaction;
import com.example.thanh.R;
import com.example.thanh.retrofit.ApiService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentActivity  extends AppCompatActivity {

//    private LinearLayout commentLayout;
    private TextView commentTextView;
    private EditText commentEditText;
    private Button postCommentButton;
    private int post_id;
    private int user_id;
    private ApiService apiService;
    private List<PostReaction> PostReactionList;
    private CommentAdapter adapter;
    private ListView listView_comment;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        post_id = getIntent().getIntExtra("post_id", -1);
        user_id = getIntent().getIntExtra("user_id", -1);
        Log.d("bug", String.valueOf(post_id));

        // Lấy tham chiếu đến các thành phần giao diện của Comment
//        commentLayout = findViewById(R.id.commentLayout);
        listView_comment = findViewById(R.id.list_comment);
        commentEditText = findViewById(R.id.commentEditText);
        postCommentButton = findViewById(R.id.postCommentButton);

        ImageButton btnGoBack = findViewById(R.id.btnBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấp vào một cuộc trò chuyện
                Intent intent = new Intent(CommentActivity.this, post_user_gets.class);
                startActivity(intent);
//                onBackPressed(); // Navigate back to the previous screen
            }
        });

        // Thực hiện các hành động tương ứng
        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEditText.getText().toString();
                postPostReaction(user_id, post_id, comment);
            }
        });

        // Khởi tạo Retrofit
        Retrofit retrofit = getRetrofitInstance();

        // Khởi tạo ApiService
        apiService = retrofit.create(ApiService.class);

        // Gọi API method GET
        getPostReaction(user_id, post_id);
    }
    private void getPostReaction(int userId, int postId) {
        Log.d("bug","voo1");
        Call<List<PostReaction>> call = apiService.getPostReaction(userId, postId);
        Log.d("bug", "voo2");
        call.enqueue(new Callback<List<PostReaction>>() {
            @Override
            public void onResponse(Call<List<PostReaction>> call, Response<List<PostReaction>> response) {
                if (response.isSuccessful()) {
                    PostReactionList = response.body();
                    Log.d("bug", String.valueOf(new Gson().toJson(PostReactionList)));
                    // Xử lý dữ liệu postReaction ở đây
                    adapter = new CommentAdapter(CommentActivity.this, PostReactionList);
                    listView_comment.setAdapter(adapter);
                    Log.d("API", "Post Reaction: " + PostReactionList.toString());
                } else {
                    Log.e("API", "Failed to get Post Reaction");
                }
            }
            @Override
            public void onFailure(Call<List<PostReaction>> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
    private void postPostReaction(int userId, int postId, String comment) {

        // Tạo object postReaction
        PostReaction postReaction = new PostReaction();
        postReaction.setPostId(postId);
        postReaction.setUserId(userId);
        postReaction.setAttachmentId("");
        postReaction.setComment(comment);
        postReaction.setTypeReact(1);
        postReaction.setReactedDate(1);
        postReaction.setDisReact(false);

        // Gửi yêu cầu POST
        Call<PostReaction> call = apiService.postPostReaction(postReaction);
        call.enqueue(new Callback<PostReaction>() {
            @Override
            public void onResponse(Call<PostReaction> call, Response<PostReaction> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    PostReaction postReaction = response.body();
//                    Log.d("bug", String.valueOf(new Gson().toJson(postedFoodUser)));
                    adapter.setNotifyOnChange(true);
                    getPostReaction(user_id, post_id);
//                    }
                } else {
                    // Xử lý lỗi khi gửi yêu cầu POST
                    Toast.makeText(CommentActivity.this, "Failed to post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostReaction> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi trong quá trình gửi yêu cầu POST
                Toast.makeText(CommentActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
