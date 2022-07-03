package com.example.ext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList food_id, food_name, food_quantity, food_expiry;

    private Animation translate_anim;

    CustomAdapter(Activity activity, Context context, ArrayList food_id, ArrayList food_name,
                  ArrayList food_quantity, ArrayList food_expiry){
        this.activity = activity;
        this.context = context;
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_quantity = food_quantity;
        this.food_expiry = food_expiry;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.food_id_txt.setText(String.valueOf(food_id.get(position)));
        holder.food_name_txt.setText(String.valueOf(food_name.get(position)));
        holder.food_quantity_txt.setText(String.valueOf(food_quantity.get(position)));
        holder.expiry_txt.setText(String.valueOf(food_expiry.get(position)));
        setExpireColor(String.valueOf(food_expiry.get(position)),holder.expiry_txt);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAndDeleteFood.class);
                intent.putExtra("id", String.valueOf(food_id.get(position)));
                intent.putExtra("name", String.valueOf(food_name.get(position)));
                intent.putExtra("quantity", String.valueOf(food_quantity.get(position)));
                intent.putExtra("date of expiry", String.valueOf(food_expiry.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    void setExpireColor (String date,TextView textView){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dates = format.parse(date);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime ldt =LocalDateTime.ofInstant((dates.toInstant()), ZoneId.systemDefault());
                //LocalDateTime.from(dates.toInstant()).plusDays(Settings.days);
                if (ldt.isBefore(LocalDateTime.now().plusDays(Settings.days))){
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setTextColor(Color.RED);}
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return food_id.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView food_id_txt, food_name_txt, food_quantity_txt, expiry_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            food_id_txt = itemView.findViewById(R.id.food_id_txt);
            food_name_txt = itemView.findViewById(R.id.food_name_txt);
            food_quantity_txt = itemView.findViewById(R.id.food_quantity_txt);
            expiry_txt = itemView.findViewById(R.id.expiry_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }

}
