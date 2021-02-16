package com.example.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
private TextInputLayout InputEmail,InputPassword,InputPasswordConfirm;
Button btnRegister;
TextView alreadyHaveAccount;
FirebaseAuth mAuth;
ProgressDialog mLoadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InputEmail = findViewById(R.id.inputEmail);
        InputPassword = findViewById(R.id.inputPassword);
        InputPasswordConfirm = findViewById(R.id.InputPasswordConfirm);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        btnRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptRegistration();
            }
        });
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AttemptRegistration() {
        String Email = InputEmail.getEditText().getText().toString();
        String Password = InputPassword.getEditText().getText().toString();
        String ConfirmPassword = InputPasswordConfirm.getEditText().getText().toString();

        if(Email.isEmpty() || !Email.contains("@gmail")){
            showError(InputEmail, "Email is not valid");
        }
        else if(Password.isEmpty() || (Password.length()<=7)) {
            if(Password.isEmpty()) {
                showError(InputPassword, "Password field is empty");
            }
            else {
                showError(InputPassword, "Password must be greater than 8 letters");
            }
    }
        else if(ConfirmPassword.isEmpty() || !(ConfirmPassword.equals(Password))) {
            if(Password.isEmpty()) {
                showError(InputPassword, "Confirm Password field is empty");
            }
            else {
                showError(InputPassword, "Password did not match");
            }

        }
        else{
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Please wait until its done");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mLoadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this,"Registration is Successful",Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(RegisterActivity.this,SetupActivity.class); //Change to setup activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        mLoadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this,"Oops something went wrong...!",Toast.LENGTH_SHORT).show();
                        Toast.makeText(RegisterActivity.this,"Please try again later",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

}

    private void showError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();
    }
    }