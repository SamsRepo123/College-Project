package com.example.chatapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendMyViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImageUrl1;
    TextView username,bio;
    public FriendMyViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImageUrl1 = itemView.findViewById(R.id.ProfileImage_F);
        username = itemView.findViewById(R.id.Username_F);
        bio = itemView.findViewById(R.id.Bio_F);
    }
}
