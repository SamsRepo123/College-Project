package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewFriendActivity extends AppCompatActivity {

    DatabaseReference mUserRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String profileImageUrl,username,firstname,lastname;
    CircleImageView profileImage;
    TextView Username,fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);
        String userID = getIntent().getStringExtra("userKey");
//        Toast.makeText(this,""+userID,Toast.LENGTH_SHORT).show();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        profileImage = findViewById(R.id.profileImage);
        Username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        loadUser();
    }

    private void loadUser() {
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists()){
            profileImageUrl=snapshot.child("profileImage").getValue().toString();
                   username=snapshot.child("username").getValue().toString();
                   firstname=snapshot.child("firstname").getValue().toString();
                   lastname=snapshot.child("lastname").getValue().toString();
                   Picasso.get().load(profileImageUrl).into(profileImage);
                   Username.setText(username);
                   fullname.setText(firstname+""+lastname);
               }
               else{
                   Toast.makeText(ViewFriendActivity.this,"404 Not Found!",Toast.LENGTH_SHORT).show();

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewFriendActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}