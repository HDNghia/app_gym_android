package com.example.thanh.adapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.activity.DatabaseHelper;
import com.example.thanh.model.User;
import com.squareup.picasso.Picasso;

import com.example.thanh.model.Post;
import com.example.thanh.R;

import java.util.List;

public class PostItemAdapter extends ArrayAdapter<Post> {
    private Context context;
    private List<Post> postItems;
    private DatabaseHelper databaseHelper;
    private List<User> ul;

    public PostItemAdapter(Context context, List<Post> postItems) {
        super(context, 0, postItems);
        this.context = context;
        this.postItems = postItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        }

        Post postItem = postItems.get(position);
        ImageView imageView = view.findViewById(R.id.posterImageView);

        if(postItem.getAttachmentId1().equals("null")){
            imageView.setVisibility(View.GONE);
//        ImageView imageView = view.findViewById(R.id.userImageView);

        }else {
            String imageUrl = postItem.getAttachmentId1();
            Picasso.get()
                    .load(imageUrl)
                    .resize(400, 200)
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView commentCountTextView = view.findViewById(R.id.commentCountTextView);
        TextView LikeCountTextView = view.findViewById(R.id.LikeCountTextView);

        titleTextView.setText(postItem.getCaption());
        commentCountTextView.setText(String.valueOf(postItem.getCountComment()));
        nameTextView.setText(postItem.getUserInfo().getLastName() + " " + postItem.getUserInfo().getFirstName());
        LikeCountTextView.setText(String.valueOf(postItem.getCountlike()));

        ImageView commentImageView = view.findViewById(R.id.commentImageView);
        ImageView likeImageView = view.findViewById(R.id.likeImageView);

        if(postItem.getCountlike() == 1){
            likeImageView.setImageResource(R.drawable.ic_baseline_recommend_24_liked);
        }else {
            likeImageView.setImageResource(R.drawable.ic_baseline_recommend_24);
        }


        final int postId = postItem.getId();

        commentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onCommentClick(postId);
                }
            }
        });
        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener1 != null) {
//                    Log.d("bug","voo like apdater");
                    clickListener1.onLikeClick(postId);
                }
            }
        });

        return view;
    }
    public interface OnCommentClickListener {
        void onCommentClick(int postId);
    }
    private OnCommentClickListener clickListener;

    public void setOnCommentClickListener(OnCommentClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnLikeClickListener {
        void onLikeClick(int postId);
    }
    private OnLikeClickListener clickListener1;

    public void setOnLikeClickListener(OnLikeClickListener clickListener) {
        this.clickListener1 = clickListener;
    }
}
