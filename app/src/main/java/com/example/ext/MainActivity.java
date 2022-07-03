package com.example.ext;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    RecyclerView recyclerView;
    ImageView empty_imageview;
    TextView no_data;
    BottomNavigationView bottomNavigationView;
    static User user;
    ArrayList<String> food_id, food__name, food_quantity, food_expiry;
    CustomAdapter customAdapter;
    boolean ascending = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(com.example.ext.MainActivity.this);
        recyclerView = findViewById(R.id.recyclerview);
        empty_imageview = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.no_data);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        food_id = new ArrayList<>();
        food__name = new ArrayList<>();
        food_quantity = new ArrayList<>();
        food_expiry = new ArrayList<>();
        Settings.days = myDB.getSettingsDays();
        storeDataInArrays();


        customAdapter = new CustomAdapter(com.example.ext.MainActivity.this, this, food_id, food__name,
                food_quantity, food_expiry);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(com.example.ext.MainActivity.this));



        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.addf:
                        startActivity(new Intent(getApplicationContext()
                                ,AddFood.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.fridge:
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }



    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() ==0 ){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()) {

                food_id.add(cursor.getString(0));
                food__name.add(cursor.getString(1));
                food_quantity.add(cursor.getString(2));
                food_expiry.add(cursor.getString(3));

            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Foods");
        builder.setMessage("Are you sure you want to delete the list ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB = new DatabaseHelper(com.example.ext.MainActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(com.example.ext.MainActivity.this, com.example.ext.MainActivity.class);
                startActivity(intent);
                finish();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}
