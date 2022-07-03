package com.example.ext;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class AddFood extends AppCompatActivity {

    DatabaseHelper myDB;
    BottomNavigationView bottomNavigationView;
    EditText foodName_input, foodQuantity_input, expiryDate_input;
    Calendar calendar;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        foodName_input = (EditText) findViewById(R.id.foodName_input);
        foodQuantity_input = (EditText) findViewById(R.id.foodQuantity_input);
        expiryDate_input = (EditText) findViewById(R.id.expiryDate_input);
        add_button = (Button) findViewById(R.id.add_button);

        calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d =calendar.get(Calendar.DAY_OF_MONTH);


        expiryDate_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.ext.AddFood.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String sDate = d + "/" + (m+1) + "/" + y ;
                        expiryDate_input.setText(sDate);
                    }
                }, y, m, d );
                //Disable past date
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();
            }
        });



        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB = new DatabaseHelper(com.example.ext.AddFood.this);
                myDB.addFood(foodName_input.getText().toString().trim(),
                        Double.parseDouble(foodQuantity_input.getText().toString().trim()),
                        expiryDate_input.getText().toString().trim());
            }
        });



        bottomNavigationView.setSelectedItemId(R.id.addf);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.addf:
                        return true;
                    case R.id.fridge:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext()
                                ,Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


    }
}
