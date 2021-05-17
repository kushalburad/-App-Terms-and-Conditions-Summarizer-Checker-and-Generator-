package com.example.teproject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.tomer.fadingtextview.FadingTextView;


public class SplashActivity extends Activity {

    Handler handler;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        FadingTextView fadingTextView;
        String[] text
                = { "Terms and Conditions", "Summarizer, Checker,\n Maker" };
        fadingTextView
                = findViewById(R.id.fadingTextView);
        fadingTextView.setTexts(text);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
        status = sharedPreferences.getString("subscribed", null);


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(status==null)
                {
                    Intent intent = new Intent(SplashActivity.this, SubscribeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(status.equals("remind later")) {
                    Intent intent = new Intent(SplashActivity.this, SubscribeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(status.equals("skipped")||status.equals("registered"))
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);

    }
}
