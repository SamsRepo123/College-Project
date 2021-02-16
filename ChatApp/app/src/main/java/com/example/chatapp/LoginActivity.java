package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout InputEmail,InputPassword;
    Button btnLogin;
    TextView forgetPassword, createNewAccount;
    ProgressDialog mLoadingBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InputEmail = findViewById(R.id.inputEmail);
        InputPassword =findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        forgetPassword = findViewById(R.id.ForgetPassword);
        createNewAccount = findViewById(R.id.createnewAccount);
        mLoadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptLogin();
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
            }
        });
    }

    private void AttemptLogin() {
        String Email = InputEmail.getEditText().getText().toString();
        String Password = InputPassword.getEditText().getText().toString();

        if (Email.isEmpty() || !Email.contains("@gmail")) {
            showError(InputEmail, "Email is not valid");
        } else if (Password.isEmpty() || (Password.length() <= 7)) {
            if (Password.isEmpty()) {
                showError(InputPassword, "Password field is empty");
            } else {
                showError(InputPassword, "Incorrect Password");
            }
        } else {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Please wait until its done");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        mLoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Login is Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        mLoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this,"Oops something went wrong...!" , Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this,"Please try again later",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showError(TextInputLayout Field, String text) {
        Field.setError(text);
        Field.requestFocus();
    }

}
