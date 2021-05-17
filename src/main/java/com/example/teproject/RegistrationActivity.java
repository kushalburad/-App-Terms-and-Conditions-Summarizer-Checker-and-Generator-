package com.example.teproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    Button btnSendOTP,btnVerify;
    EditText editEmail,editOTP;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SendEmail sendObj;


    public boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    int RandomOTP=0;

    private void sendEmail() {
        final String checkemail = editEmail.getText().toString().replace(".","");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(checkemail)) {
                    Toast.makeText(RegistrationActivity.this,"Hey, You are already registered !",Toast.LENGTH_SHORT).show();
                    editor.putString("subscribed","registered");
                    editor.apply();
                    Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    //Getting content for email
                    String email = editEmail.getText().toString().trim();
                    String subject = "Your OTP For Registration";
                    int r = 100000 + new Random().nextInt(900000);
                    String message = "Enter this OTP : "+r;
                    RandomOTP = r;

                    //Creating SendMail object
                    SendEmail sm = new SendEmail(RegistrationActivity.this, email, subject, message);

                    //Executing sendmail to send email
                    sm.execute();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    String emailaddress;
    boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerscreen);
        btnSendOTP=findViewById(R.id.btnSendOTP);
        editEmail=findViewById(R.id.editEmail);
        editOTP=findViewById(R.id.editOTP);
        btnVerify=findViewById(R.id.btnVerify);
        sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editOTP.setEnabled(false);
        btnVerify.setEnabled(false);

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(editEmail.getText().toString().equals(""))) {
                    emailaddress = editEmail.getText().toString();
                    if(isValid(emailaddress)) {
                        sendEmail();
                        editOTP.setEnabled(true);
                        btnVerify.setEnabled(true);
                        editEmail.setEnabled(false);
                        btnSendOTP.setEnabled(false);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Email Address Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(editOTP.getText().toString())==RandomOTP){
                    DatabaseReference databaseReference;
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    String emailaddress = editEmail.getText().toString();
                    databaseReference.child("users").child(emailaddress.replace(".","")).setValue(emailaddress);

                    Toast.makeText(getApplicationContext(),"Email Verified",Toast.LENGTH_SHORT).show();
                    editor.putString("subscribed","registered");
                    editor.apply();
                    Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"OTP Incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}