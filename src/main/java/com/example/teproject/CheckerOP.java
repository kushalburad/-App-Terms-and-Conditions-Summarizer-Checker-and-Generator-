package com.example.teproject;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckerOP extends AppCompatActivity {
    @SuppressLint("UseCompatLoadingForDrawables")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkerop);

         SharedPreferences sharedPref = getSharedPreferences("MyPref",MODE_PRIVATE);
        String summary = sharedPref.getString("check", "You didn't select an image.");
        ImageView img = (ImageView)findViewById(R.id.imageView2);
        final TextView txtSum = (TextView)findViewById(R.id.txtSum);
        if(!(summary.equals("You didn't select an image.") || summary.equals("No text detected in the given image.") || summary.equals("Image can't be checked for critical info."))) {
            img.setVisibility(View.INVISIBLE);
        }
        txtSum.setBackgroundColor(Color.WHITE);

        txtSum.setText(summary);
        Button btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}