package com.example.thanh.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thanh.activity.DatabaseHelper;
import com.example.thanh.model.Message;
import com.example.thanh.R;
import com.example.thanh.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private Context context;
    private List<Message> messageList;
    private int userId;
    private DatabaseHelper databaseHelper;
    private List<User> ul;

    public MessageAdapter(Context context, List<Message> messageList, int userId) {
        super(context, 0, messageList);
        this.context = context;
        this.messageList = messageList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_message, parent, false);
        }

        databaseHelper = new DatabaseHelper(context);
        ul = databaseHelper.getAllUser();
        // Lấy dữ liệu của conversation tại vị trí position
        Message message = messageList.get(position);

        // Hiển thị dữ liệu trong view
        if(message.getSenderId() != ul.get(0).get_id()){

            TextView chatPartner = view.findViewById(R.id.chatTextViewLeft);
            ImageView facePartner = view.findViewById(R.id.face_partner);
            TextView chatUser = view.findViewById(R.id.chatTextViewRight);
            ImageView faceUser = view.findViewById(R.id.face_user);
            chatPartner.setText(message.getMessageContent());
            chatPartner.setVisibility(View.VISIBLE);
            facePartner.setVisibility(View.VISIBLE);
            chatUser.setVisibility(View.GONE);
            faceUser.setVisibility(View.GONE);

            if(message.getUserInfo().getAvt().equals("")){
                Log.d("bug", "voo get avt equals");
            }else{
                String imageUrl1 = message.getUserInfo().getAvt();
                Picasso.get()
                        .load(imageUrl1)
                        .resize(50, 50)
                        .into(facePartner);
            }
        }
        else{
            TextView chatPartner = view.findViewById(R.id.chatTextViewLeft);
            ImageView facePartner = view.findViewById(R.id.face_partner);
            TextView chatUser = view.findViewById(R.id.chatTextViewRight);
            ImageView faceUser = view.findViewById(R.id.face_user);
            chatUser.setText(message.getMessageContent());
            chatPartner.setVisibility(View.GONE);
            facePartner.setVisibility(View.GONE);
            chatUser.setVisibility(View.VISIBLE);
            faceUser.setVisibility(View.VISIBLE);

            if(message.getUserInfo().getAvt().equals("")){
                Log.d("bug", "voo get avt equals");
            }else{
                String imageUrl1 = message.getUserInfo().getAvt();
                Picasso.get()
                        .load(imageUrl1)
                        .resize(50, 50)
                        .into(faceUser);
            }
        }
        return view;
    }

}


