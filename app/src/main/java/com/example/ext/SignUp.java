package com.example.ext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    EditText emailField, passwordField, nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailField = (EditText) findViewById(R.id.email_field);
        passwordField = (EditText) findViewById(R.id.password_field);
        nameField = (EditText) findViewById(R.id.name_field);
    }
    public void register(View v) {

        DatabaseHelper databaseHelper = new DatabaseHelper(com.example.ext.SignUp.this);

        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(com.example.ext.SignUp.this, "email and password can't be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String name =nameField.getText().toString().trim();
        int result =  databaseHelper.signUp(email,password,name);
        if (result != -1){
            MainActivity.user =  databaseHelper.Login(email,password);
            Intent i = new Intent(com.example.ext.SignUp.this,MainActivity.class);
            startActivity(i);
        }

    }
    public void loginNavigate(View v) {
        Intent i = new Intent(com.example.ext.SignUp.this,Login.class);
        startActivity(i);


    }
}
