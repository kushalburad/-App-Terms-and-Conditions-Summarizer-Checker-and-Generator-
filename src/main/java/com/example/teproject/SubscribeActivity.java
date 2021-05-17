package com.example.teproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SubscribeActivity extends AppCompatActivity {
    Button reg_btn,skip_btn;
    CheckBox remind_ch;
    boolean checkboxselected=false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribescreen);
        reg_btn=findViewById(R.id.btn_register);
        sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        skip_btn=findViewById(R.id.btn_skip);
        remind_ch=findViewById(R.id.check_remind);

        reg_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SubscribeActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });

        remind_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(remind_ch.isChecked()){
                    checkboxselected=true;
                }
                else{
                    checkboxselected=false;
                }
            }
        });

        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkboxselected){
                    editor.putString("subscribed","remind later");
                    editor.apply();
                    Intent i = new Intent(SubscribeActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    editor.putString("subscribed", "skipped");
                    editor.apply();
                    Intent i = new Intent(SubscribeActivity.this, MainActivity.class);
                    startActivity(i);
                }
                finish();
            }
        });
    }
}