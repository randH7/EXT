package com.example.ext;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Date;

public class Settings extends AppCompatActivity {

    static int days = 1;
    DatabaseHelper myDB;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.addf:
                        startActivity(new Intent(getApplicationContext()
                                , AddFood.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.fridge:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });
        readData();
        setDropDownMenu();
    }

    private void setDropDownMenu() {
        DatabaseHelper dh = new DatabaseHelper(com.example.ext.Settings.this);
        com.example.ext.Settings.days = dh.getSettingsDays();
        Spinner dropdown = findViewById(R.id.spinner2);
        String[] items = new String[]{"1 day before", "2 days before", "3 days before", "4 days before"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(com.example.ext.Settings.days - 1);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                com.example.ext.Settings.days = position + 1;
                dh.updateDays(com.example.ext.Settings.days);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }


    void readData() {
        myDB = new DatabaseHelper(com.example.ext.Settings.this);
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Date date = new Date();
        } else {
            while (cursor.moveToNext()) {
                String date = cursor.getString(3);
                System.out.println(date);
            }
        }
    }


    public void logoutCLick(View v) {
        finishAffinity();
        startActivity(new Intent(com.example.ext.Settings.this, SplashScreen.class));

    }
}