package com.example.thanh.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thanh.model.*;
import com.example.thanh.R;

import java.util.List;

public class ConversationAdapter extends ArrayAdapter<Conversation> {
    private Context context;
    private List<Conversation> conversationList;

    public ConversationAdapter(Context context, List<Conversation> conversationList) {
        super(context, 0, conversationList);
        this.context = context;
        this.conversationList = conversationList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_conversation, parent, false);
        }
        Log.d("bug", "Vo conversation adapter ");
        // Lấy dữ liệu của conversation tại vị trí position
        Conversation conversation = conversationList.get(position);

        // Hiển thị dữ liệu trong view
//        ImageView face_partner = view.findViewById(R.id.face_partner);
//        String imageUrl = "https://i0.wp.com/post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/03/GettyImages-1092658864_hero-1024x575.jpg?w=1155&h=1528";
//        Picasso.get().load(imageUrl).into(face_partner);
        TextView nicknameTextView = view.findViewById(R.id.nicknameTextView);
        TextView timestampTextView = view.findViewById(R.id.timestampTextView);
        TextView LastMessage = view.findViewById(R.id.messageTextView);

        nicknameTextView.setText(conversation.getNickname());
        timestampTextView.setText(String.valueOf(conversation.getLastActive() + "s"));
        LastMessage.setText(conversation.getMessage());

        final int conversationId = conversation.get_id();
        final int userId = conversation.getUserId();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onConversationClick(conversationId, userId);
                }
            }
        });

        return view;
    }

    public interface OnConversationClickListener {
        void onConversationClick(int conversationId, int userId);
    }
    private OnConversationClickListener clickListener;

    public void setOnConversationClickListener(OnConversationClickListener listener) {
        this.clickListener = listener;
    }

}


