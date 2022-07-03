package com.example.ext;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class UpdateAndDeleteFood extends AppCompatActivity {

    EditText foodName_input, foodQuantity_input, expiryDate_input;
    Calendar calendar;

    Button update_button, delete_button;

    String id, name, quantity, expiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_and_delete_food);

        foodName_input = (EditText) findViewById(R.id.foodName_input2);
        foodQuantity_input = (EditText) findViewById(R.id.foodQuantity_input2);
        expiryDate_input = (EditText) findViewById(R.id.expiryDate_input2);
        update_button = (Button) findViewById(R.id.update_button);
        delete_button = (Button) findViewById(R.id.delete_button);

        calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d =calendar.get(Calendar.DAY_OF_MONTH);


        expiryDate_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog
                        (com.example.ext.UpdateAndDeleteFood.this, new DatePickerDialog.OnDateSetListener() {
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



        getAndSetIntentDate();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(com.example.ext.UpdateAndDeleteFood.this);
                name = foodName_input.getText().toString().trim();
                quantity = foodQuantity_input.getText().toString().trim();
                expiry = expiryDate_input.getText().toString().trim();
                myDB.updateOneRow(id, name, quantity, expiry);
            }
        });



        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }



    void getAndSetIntentDate() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("quantity") &&
                getIntent().hasExtra("date of expiry")) {

            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            quantity = getIntent().getStringExtra("quantity");
            expiry = getIntent().getStringExtra("date of expiry");

            //Setting Intent Data
            foodName_input.setText(name);
            foodQuantity_input.setText(quantity);
            expiryDate_input.setText(expiry);


        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }



    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ name);
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(com.example.ext.UpdateAndDeleteFood.this);
                myDB.deleteOneRow(id);
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

