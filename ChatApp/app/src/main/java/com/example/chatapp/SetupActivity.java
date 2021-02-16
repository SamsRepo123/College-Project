package com.example.chatapp;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import androidx.annotation.NonNull;
//import android.widget.Toast;
//import android.app.ProgressDialog;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;

//import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    CircleImageView profileImageView;
    EditText inputUsername, inputFirstName, inputLastName, inputBio;
    Button btnSave;
    private static  final int REQUEST_CODE = 101;
    Uri imageUri;
//    FirebaseAuth mAuth;
//    FirebaseUser mUser;
//    DatabaseReference mRef;
//    StorageReference storageRef;
//    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        profileImageView = findViewById(R.id.profile_image);
        inputUsername = findViewById(R.id.inputUsername);
        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputBio = findViewById(R.id.inputBio);
        btnSave = findViewById(R.id.btnSave);
//        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();
//        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
//        storageRef = FirebaseStorage.getInstance().getReference().child("Profile");
//        mLoadingBar = new ProgressDialog(this);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SaveData();
//            }
//        });
//    }
//
//    private void SaveData() {
//        String username = inputUsername.getText().toString();
//        String Firstname = inputFirstName.getText().toString();
//        String Lastname = inputLastName.getText().toString();
//        String Bio = inputBio.getText().toString();
//        if (username.isEmpty() || username.length() < 6) {
//            if (username.isEmpty()) {
//                showError(inputUsername, "Username Required!");
//            } else {
//                showError(inputUsername, "Minimum 6 Characters Required!");
//            }
//
//        } else if (Firstname.isEmpty()) {
//            showError(inputFirstName, "Your First Name Required!");
//        } else if (Lastname.isEmpty()) {
//            showError(inputLastName, "Your Last Name Required!");
//        }
//
//        // This is optional parameter
////        else if (Bio.isEmpty()){
////            showError(inputBio,"Bio Required!");
////        }
//
//        else {
//            mLoadingBar.setTitle("Setting up User Profile!");
//            mLoadingBar.setCanceledOnTouchOutside(false);
//            mLoadingBar.show();
//            storageRef.child(mUser.getUid()).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    if(task.isSuccessful()){
//                        storageRef.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                HashMap hashmap = new HashMap<>();
//                                hashmap.put("username",username);
//                                hashmap.put("First_Name",Firstname);
//                                hashmap.put("Last_Name",Lastname);
//                                hashmap.put("Bio",Bio);
//                                hashmap.put("profileImage",uri.toString());
//                                hashmap.put("status","offline");
//
//                                mRef.child(mUser.getUid()).updateChildren(hashmap).addOnSuccessListener(new OnSuccessListener() {
//                                    @Override
//                                    public void onSuccess(Object o) {
//                                        Intent intent = new Intent(SetupActivity.this,MainActivity.class);
//                                        startActivity(intent);
//                                        mLoadingBar.dismiss();
//                                        Toast.makeText(SetupActivity.this,"Please wait...",Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        mLoadingBar.dismiss();
//                                        Toast.makeText(SetupActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                        });
//                    }
//                }
//            });
//        }
//    }
//    private void showError(EditText input, String s) {
//        input.setError(s);
//        input.requestFocus();
//    }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
        }
    }
}