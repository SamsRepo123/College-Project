package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    ImageView imageView2;
    EditText inputEmail;
    Button btnReset;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        inputEmail = findViewById(R.id.inputPasswordReset);
        imageView2 = findViewById(R.id.imageView2);
        btnReset = findViewById(R.id.btnReset);
        mAuth = FirebaseAuth.getInstance();
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= inputEmail.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(ForgetPasswordActivity.this,"Please enter your email",Toast.LENGTH_SHORT).show();
//TODO Email is not sending
                }
                else{
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPasswordActivity.this,"Reset link has been sent to your email",Toast.LENGTH_SHORT).show();

                }
                }
            });
                }

            }
        });
    }
}