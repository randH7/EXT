package com.example.ext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText emailField, passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailField = (EditText) findViewById(R.id.email_field);
        passwordField = (EditText) findViewById(R.id.password_field);
    }

    public void onClick(View v) {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        DatabaseHelper databaseHelper = new DatabaseHelper(com.example.ext.Login.this);
        MainActivity.user = databaseHelper.Login(email, password);
        User user = MainActivity.user;
        if (user != null) {
            Intent i = new Intent(com.example.ext.Login.this,MainActivity.class);
            startActivity(i);
        }

    }

    public void signUpNavigate(View v) {
        Intent i = new Intent(com.example.ext.Login.this, SignUp.class);
        startActivity(i);

    }
}
