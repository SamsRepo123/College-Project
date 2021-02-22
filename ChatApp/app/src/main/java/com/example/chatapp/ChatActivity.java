package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.utils.Chat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    EditText inputMessage;
    ImageView imageView, btnSendMessage;
    CircleImageView userProfileImageAppbar;
    TextView usernameAppBar,status;
    String OtherUserID;
    DatabaseReference mUserRef,messageRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String OtherUsername,OtherUserProfileImageLink,OtherUserStatus;
    String myProfileImageLink;
    FirebaseRecyclerOptions<Chat>option;
    FirebaseRecyclerAdapter<Chat,MyChatViewHolder>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        OtherUserID =getIntent().getStringExtra("OtherUserID");
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        inputMessage =  findViewById(R.id.inputMessage);
        imageView =  findViewById(R.id.imageView);
        userProfileImageAppbar = findViewById(R.id.userProfileImageAppbar);
        usernameAppBar = findViewById(R.id.usernameAppBar);
        status = findViewById(R.id.status);
        btnSendMessage = findViewById(R.id.btnSendMessage);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        messageRef = FirebaseDatabase.getInstance().getReference().child("Message");

        recyclerView = findViewById(R.id.RecyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoadOtherUser();
        LoadMyProfile();
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });
        loadMessage();
    }

    private void LoadMyProfile() {
        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myProfileImageLink = snapshot.child("profileImage").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Could'nt load user profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMessage() {
        option = new FirebaseRecyclerOptions.Builder<Chat>().setQuery(messageRef.child(mUser.getUid()).child(OtherUserID),Chat.class).build();
        adapter = new FirebaseRecyclerAdapter<Chat, MyChatViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull MyChatViewHolder holder, int position, @NonNull Chat model) {
                if(model.getUserID().equals(mUser.getUid())){
                   holder.firstUserText.setVisibility(View.GONE);
                   holder.firstUserProfile.setVisibility(View.GONE);
                    holder.secondUserText.setVisibility(View.VISIBLE);
                    holder.secondUserProfile.setVisibility(View.VISIBLE);
                    Picasso.get().load(myProfileImageLink).into(holder.secondUserProfile);

                    holder.secondUserText.setText(model.getMessage());

                }
                else{
                    holder.firstUserText.setVisibility(View.VISIBLE);
                    holder.firstUserProfile.setVisibility(View.VISIBLE);
                    holder.secondUserText.setVisibility(View.GONE);
                    holder.secondUserProfile.setVisibility(View.GONE);

                    holder.firstUserText.setText(model.getMessage());
                    Picasso.get().load(OtherUserProfileImageLink).into(holder.firstUserProfile);

                }

            }

            @NonNull
            @Override
            public MyChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleview_message,parent,false);

                return new MyChatViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


    private void SendMessage() {
        String message = inputMessage.getText().toString();
        if(message.isEmpty()){

        }
        else{
            HashMap hashMap = new HashMap();
            hashMap.put("message",message);
            hashMap.put("status","unseen");
            hashMap.put("userID",mUser.getUid());
            messageRef.child(OtherUserID).child(mUser.getUid()).push().updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                    messageRef.child(mUser.getUid()).child(OtherUserID).push().updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                       if(task.isSuccessful()){
                           inputMessage.setText(null);
                           Toast.makeText(ChatActivity.this,"message sent", Toast.LENGTH_SHORT).show();
                       }
                        }
                    });
                    }
                }
            });
        }
    }

    private void LoadOtherUser() {
        mUserRef.child(OtherUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                OtherUsername=snapshot.child("username").getValue().toString();
                OtherUserProfileImageLink=snapshot.child("profileImage").getValue().toString();
                OtherUserStatus=snapshot.child("status").getValue().toString();

                    Picasso.get().load(OtherUserProfileImageLink).into(userProfileImageAppbar);
                    usernameAppBar.setText(OtherUsername);
                    status.setText(OtherUserStatus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this,""+error.getMessage(),Toast.LENGTH_SHORT);
            }
        });
    }
}