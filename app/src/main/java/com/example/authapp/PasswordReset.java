package com.example.authapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {

    private EditText emailPassRes;

    private Button buttonPassRes, backToLoginPassRes;

    private ProgressBar progressBarPassRes;

    FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        emailPassRes = (EditText) findViewById(R.id.emailPassRes);
        buttonPassRes = (Button) findViewById(R.id.buttonPassRes);
        backToLoginPassRes = (Button) findViewById(R.id.backToLoginPassRes);
        progressBarPassRes = (ProgressBar) findViewById(R.id.progressBarPassRes);

        auth = FirebaseAuth.getInstance();

        backToLoginPassRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PasswordReset.this, MainActivity.class));
            }
        });

        buttonPassRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });


    }

    private void resetPassword() {
        String email = emailPassRes.getText().toString().trim();

        if(email.isEmpty()){
            emailPassRes.setError("Email is required!");
            emailPassRes.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailPassRes.setError("Please enter a valid email and try again.");
            emailPassRes.requestFocus();
            return;
        }

        progressBarPassRes.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(PasswordReset.this, "Check your email to change your password.", Toast.LENGTH_LONG).show();
                    progressBarPassRes.setVisibility(View.GONE);
                    startActivity(new Intent(PasswordReset.this, MainActivity.class));
                }
                else {
                    Toast.makeText(PasswordReset.this, "Something wrong happened! Try again.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}