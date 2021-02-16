package com.example.chatapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImage;
    TextView username,bio_f;
    public FindFriendViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImage = itemView.findViewById(R.id.ProfileImage_F);
        username = itemView.findViewById(R.id.Username_F);
        bio_f = itemView.findViewById(R.id.Bio_F);



    }
}
