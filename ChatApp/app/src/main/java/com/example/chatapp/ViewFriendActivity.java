package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaCodecInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ViewFriendActivity extends AppCompatActivity {

    DatabaseReference mUserRef,ReqRef,frndRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String profileImageUrl,username,firstname,lastname;
    CircleImageView profileImage;
    TextView Username,fullname;
    Button btnReq,btnDecline;
    String bio;
    String CurrentState="nothingHappen";
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);
         userID = getIntent().getStringExtra("userKey");
//        Toast.makeText(this,""+userID,Toast.LENGTH_SHORT).show();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        mAuth = FirebaseAuth.getInstance();
        ReqRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        frndRef = FirebaseDatabase.getInstance().getReference().child("Friends");
        mUser = mAuth.getCurrentUser();
        profileImage = findViewById(R.id.profileImage);
        btnReq = findViewById(R.id.btnReq);
        btnDecline = findViewById(R.id.btnDecline);

        Username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        loadUser();
        btnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAction(userID);
            }
        });
        CheckUserExistance(userID);
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unfriend(userID);
            }
        });
    }

    private void Unfriend(String userID) {
        if(CurrentState.equals("Friends")){
                frndRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        frndRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ViewFriendActivity.this,"Unfriended",Toast.LENGTH_SHORT).show();
                                CurrentState="nothingHappen";
                                btnReq.setText("Send Friend Request");
                                    btnDecline.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                    }
                });
    }
        if(CurrentState.equals("he_send_pending")){
            HashMap hashMap = new HashMap();
            hashMap.put("status","decline");
            ReqRef.child(userID).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(ViewFriendActivity.this,"Friend Request Decline",Toast.LENGTH_SHORT).show();
                    CurrentState="he_sent_decline";
                    btnReq.setVisibility(View.GONE);
                    btnDecline.setVisibility(View.GONE);
                }
                }
            });
        }
    }

    private void CheckUserExistance(String userID) {
        frndRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    CurrentState ="Friends";
                    btnReq.setText("message");
                    btnDecline.setText("unfriend");
                    btnDecline.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        frndRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    CurrentState ="Friends";
                    btnReq.setText("message");
                    btnDecline.setText("unfriend");
                    btnDecline.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ReqRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot.exists()){
                  if(snapshot.child("status").getValue().toString().equals("pending")){
                    CurrentState="I_sent_pending";
                    btnReq.setText("Cancel Friend Request");
                    btnDecline.setVisibility(View.GONE);
                  }
                  if(snapshot.child("status").getValue().toString().equals("decline")){
                      CurrentState="I_sent_decline";
                      btnReq.setText("Cancel Friend Request");
                      btnDecline.setVisibility(View.GONE);
                  }
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ReqRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("status").getValue().equals("pending")){
                        CurrentState="he_sent_pending";
                        btnReq.setText("Accept Request");
                        btnDecline.setText("Decline Request");
                        btnDecline.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(CurrentState.equals("nothingHappen")){
            CurrentState="nothingHappen";
            btnReq.setText("Send Friend Request");
            btnDecline.setVisibility(View.GONE);
        }
     }

    private void PerformAction(String userID) {
        if(CurrentState.equals("nothingHappen")){
            HashMap hashMap = new HashMap();
            hashMap.put("status","pending");
            ReqRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ViewFriendActivity.this,"Friend Request has been sent!",Toast.LENGTH_SHORT).show();
                        btnDecline.setVisibility(View.GONE);
                        CurrentState="I_sent_pending";
                        btnReq.setText("Cancel Friend Request");

                    }
                    else{
                        Toast.makeText(ViewFriendActivity.this,""+task.getException().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if(CurrentState.equals("I_sent_pending") || CurrentState.equals("I_sent_decline")){
            ReqRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ViewFriendActivity.this,"Request has been cancelled",Toast.LENGTH_SHORT).show();
                    CurrentState="nothingHappen";
                    btnReq.setText("Send Friend Request");
                    btnDecline.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(ViewFriendActivity.this,""+task.getException().toString(),Toast.LENGTH_SHORT).show();
                }
                }
            });
        }
        if(CurrentState.equals("he_sent_pending")){
            ReqRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){
                    HashMap hashMap = new HashMap();
                    hashMap.put("status","Friends");
                    hashMap.put("username",username);
                    hashMap.put("profileImageUrl",profileImageUrl);
                    hashMap.put("bio",bio);
                    frndRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                frndRef.child(userID).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        Toast.makeText(ViewFriendActivity.this,"Friend Request Accepted",Toast.LENGTH_SHORT).show();
                                   CurrentState="Friends";
                                   btnReq.setText("message");
                                   btnDecline.setText("unfriend");
                                   btnDecline.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }
                    });
                   }
                }
            });
        }
        if(CurrentState.equals("Friends")){
            Intent intent = new Intent(ViewFriendActivity.this,ChatActivity.class);
            intent.putExtra("OtherUserID",userID);
            startActivity(intent);
        }
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
                   bio=snapshot.child("bio").getValue().toString();
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