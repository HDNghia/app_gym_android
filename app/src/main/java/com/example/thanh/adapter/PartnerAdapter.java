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

import com.example.thanh.model.*;
import com.example.thanh.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PartnerAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> partnerList;

    public PartnerAdapter(Context context, List<User> partnerList) {
        super(context, 0, partnerList);
        this.context = context;
        this.partnerList = partnerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_partner, parent, false);
        }
        Log.d("bug", "Vo conversation adapter ");
        // Lấy dữ liệu của conversation tại vị trí position
        User partner = partnerList.get(position);

        // Hiển thị dữ liệu trong view
        ImageView face_partner = view.findViewById(R.id.face_partner);
//        String imageUrl = "https://i0.wp.com/post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/03/GettyImages-1092658864_hero-1024x575.jpg?w=1155&h=1528";
//        Picasso.get().load(imageUrl).into(face_partner);

        if(partner.getAvt().equals("")){
            Log.d("bug", "voo get avt equals");
        }else{
            String imageUrl1 = partner.getAvt();
            Picasso.get()
                    .load(imageUrl1)
                    .resize(50, 50)
                    .into(face_partner);
        }
        TextView nicknameTextView = view.findViewById(R.id.nicknameTextView);
//        TextView timestampTextView = view.findViewById(R.id.timestampTextView);
//        TextView LastMessage = view.findViewById(R.id.messageTextView);

        nicknameTextView.setText(partner.getFirstname());
//        timestampTextView.setText(String.valueOf(conversation.getLastActive() + "s"));
//        LastMessage.setText(conversation.getMessage());

        final String partnerName = partner.getFirstname();
        final Integer partnerId = partner.get_id();
        final int userId = 1;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onPartnerClick(partnerName, partnerId);
                }
            }
        });

        return view;
    }

    public interface OnPartnerClickListener {
        void onPartnerClick(String partnerName, int partnerId);
    }
    private OnPartnerClickListener clickListener;

    public void setOnPartnerClickListener(OnPartnerClickListener listener) {
        this.clickListener = listener;
    }

}


