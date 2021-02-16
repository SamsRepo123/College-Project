package com.example.chatapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentViewHolder extends RecyclerView.ViewHolder  {
    CircleImageView profileImageComment;
    TextView usernameComment, CommentTextView;
    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImageComment = itemView.findViewById(R.id.profile_image_comment);
        usernameComment = itemView.findViewById(R.id.usernameComment);
        CommentTextView = itemView.findViewById(R.id.commentTextView);
    }
}
