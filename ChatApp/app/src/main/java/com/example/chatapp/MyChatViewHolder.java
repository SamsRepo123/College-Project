package com.example.chatapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyChatViewHolder extends RecyclerView.ViewHolder {
 CircleImageView firstUserProfile,secondUserProfile;
 TextView firstUserText, secondUserText;
    public MyChatViewHolder(@NonNull View itemView) {
        super(itemView);
        firstUserProfile = itemView.findViewById(R.id.firstUserProfile);
        secondUserProfile = itemView.findViewById(R.id.SecondUserProfile);
        firstUserText = itemView.findViewById(R.id.firstUserText);
        secondUserText = itemView.findViewById(R.id.SecondUserText);
    }
}
