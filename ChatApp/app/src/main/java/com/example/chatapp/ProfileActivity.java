package com.example.chatapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileImageView;
    EditText inputUsername, inputFirstname, inputLastname, inputBio;
    Button btnUpdate;
    DatabaseReference mUserRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImageView = findViewById(R.id.circleImageView);
        inputFirstname = findViewById(R.id.inputFirstname_P);
        inputLastname = findViewById(R.id.inputLastname_P);
        inputUsername = findViewById(R.id.inputUsername_p);
        inputBio = findViewById(R.id.inputBio_p);
        btnUpdate = findViewById(R.id.btnUpdate_P);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef= FirebaseDatabase.getInstance().getReference().child("Users");

        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String profileImageUrl = snapshot.child("profileImage").getValue().toString();
                    String bio = snapshot.child("bio").getValue().toString();
                    String firstName = snapshot.child("firstname").getValue().toString();
                    String lastName = snapshot.child("lastname").getValue().toString();
                    String username = snapshot.child("username").getValue().toString();

                    Picasso.get().load(profileImageUrl).into(profileImageView);
                    inputBio.setText(bio);
                    inputFirstname.setText(firstName);
                    inputLastname.setText(lastName);
                    inputUsername.setText(username);


                }
                else{
                    Toast.makeText(ProfileActivity.this, "User does not exists...!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "" +error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}