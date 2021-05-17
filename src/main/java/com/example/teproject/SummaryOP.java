package com.example.teproject;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryOP extends AppCompatActivity {

    Button btnBack;
    ImageButton btnCopyClip;
    TextView txtSum;

    @SuppressLint("UseCompatLoadingForDrawables")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summaryop);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnCopyClip = findViewById(R.id.btnCopyClip);
        txtSum = (TextView) findViewById(R.id.txtSum);

            SharedPreferences sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE);
            String summary = sharedPref.getString("sum", "You didn't select an image.");
            ImageView img = (ImageView) findViewById(R.id.imageView2);
            if (!(summary.equals("You didn't select an image.") || summary.equals("No text detected in the given image.") || summary.equals("Summary can't be created for the given image."))) {
                img.setVisibility(View.INVISIBLE);
            }
            txtSum.setBackgroundColor(Color.WHITE);

            txtSum.setText(summary);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            btnCopyClip.setColorFilter(Color.BLACK);
            btnCopyClip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!(txtSum.getText().equals("You didn't select an image.") || txtSum.getText().equals("No text detected in the given image.") || txtSum.getText().equals("Summary can't be created for the given image."))) {
                        int sdk = android.os.Build.VERSION.SDK_INT;
                        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                                    getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setText(txtSum.getText());
                        } else {
                            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                                    getSystemService(Context.CLIPBOARD_SERVICE);
                            android.content.ClipData clip = android.content.ClipData
                                    .newPlainText("Summary", txtSum.getText());
                            clipboard.setPrimaryClip(clip);
                        }
                        Toast.makeText(SummaryOP.this, "Summary copied to Clipboard", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(SummaryOP.this, "No summary generated", Toast.LENGTH_SHORT).show();
                }
            });
    }
}
