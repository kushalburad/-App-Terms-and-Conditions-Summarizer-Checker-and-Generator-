package com.example.teproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MakerOP extends AppCompatActivity {

    private static final int STORAGE_CODE = 1000;

    @SuppressLint("UseCompatLoadingForDrawables")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makerop);

        SharedPreferences sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String appname = sharedPref.getString("appname", "YOUR APPLICATION");
        String personalinfo = sharedPref.getString("personalinfo", "YOUR PERSONAL INFO");
        String apptype = sharedPref.getString("apptype", "YOUR APP TYPE");
        String contact = sharedPref.getString("contact", "YOUR CONTACT");
        String companyname = sharedPref.getString("companyname", "COMPANYNAME");
        String permissions = sharedPref.getString("permissions", "PERMISSIONS");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = simpleDateFormat.format(calendar.getTime());

        TextView textview = findViewById(R.id.txtSum);
        textview.setText("App name : "+appname+"\nApp type : "+apptype+"\nPersonal Info : "+personalinfo+"\nContact : "+contact);

        String generartedterms = "<strong>Terms &amp; Conditions</strong> <p> "+companyname+" developed this app as "+apptype+" app. By downloading or using the app, these terms will automatically apply to you – you " +
                "should make sure therefore that you read them carefully before using the app. You’re not allowed to copy, or modify the app, any part of the app, or our " +
                "trademarks in any way. You’re not allowed to attempt to extract the source code of the app, and you also shouldn’t try to translate the app into other languages, " +
                "or make derivative versions. The app itself, and all the trade marks, copyright, database rights and other intellectual property rights related to it, still " +
                "belong to "+companyname+". </p> <p> "+companyname+" is committed to ensuring that the app is as useful and efficient as possible. For that reason, we reserve the right to " +
                "make changes to the app or to charge for its services, at any time and for any reason. We will never charge you for the app or its services without making it " +
                "very clear to you exactly what you’re paying for. </p> <p> The "+appname+" app stores and processes personal data that you have provided to us, in order to provide " +
                "OUR Service. It’s your responsibility to keep your phone and access to the app secure. We therefore recommend that you do not jailbreak or root your phone, which " +
                "is the process of removing software restrictions and limitations imposed by the official operating system of your device. It could make your phone vulnerable to " +
                "malware/viruses/malicious programs, compromise your phone’s security features and it could mean that the "+appname+" app won’t work properly or at all. </p>  <p> You " +
                "should be aware that there are certain things that "+companyname+" will not take responsibility for. Certain functions of the app will require the app to have an " +
                "active internet connection. The connection can be Wi-Fi, or provided by your mobile network provider, but "+companyname+" cannot take responsibility for the app not " +
                "working at full functionality if you don’t have access to Wi-Fi, and you don’t have any of your data allowance left. </p> <p></p> <p> If you’re using the app " +
                "outside of an area with Wi-Fi, you should remember that your terms of the agreement with your mobile network provider will still apply. As a result, you may be " +
                "charged by your mobile provider for the cost of data for the duration of the connection while accessing the app, or other third party charges. In using the app, " +
                "you’re accepting responsibility for any such charges, including roaming data charges if you use the app outside of your home territory (i.e. region or country) " +
                "without turning off data roaming. If you are not the bill payer for the device on which you’re using the app, please be aware that we assume that you have " +
                "received permission from the bill payer for using the app. </p> <p> Along the same lines, "+companyname+" cannot always take responsibility for the way you use the " +
                "app i.e. You need to make sure that your device stays charged – if it runs out of battery and you can’t turn it on to avail the Service, "+companyname+" cannot accept " +
                "responsibility. </p> <p> With respect to "+companyname+"’s responsibility for your use of the app, when you’re using the app, it’s important to bear in mind that " +
                "although we endeavour to ensure that it is updated and correct at all times, we do rely on third parties to provide information to us so that we can make it " +
                "available to you. "+companyname+" accepts no liability for any loss, direct or indirect, you experience as a result of relying wholly on this functionality of the app. " +
                "</p> <p> At some point, we may wish to update the app. The app is currently available on Android – the requirements for Android(and for any additional systems we " +
                "decide to extend the availability of the app to) may change, and you’ll need to download the updates if you want to keep using the app. "+companyname+" does not promise " +
                "that it will always update the app so that it is relevant to you and/or works with the Android version that you have installed on your device. However, you promise" +
                " to always accept updates to the application when offered to you, We may also wish to stop providing the app, and may terminate use of it at any time without " +
                "giving notice of termination to you. Unless we tell you otherwise, upon any termination, (a) the rights and licenses granted to you in these terms will end; (b) " +
                "you must stop using the app, and (if needed) delete it from your device. </p> <p><strong>Changes to This Terms and Conditions</strong></p> <p> WE may update our " +
                "Terms and Conditions from time to time. Thus, you are advised to review this page periodically for any changes. WE will notify you of any changes by posting the " +
                "new Terms and Conditions on this page. </p> <p> These terms and conditions are effective as of "+date+" </p> <p><strong>Contact Us</strong></p> <p> If you have " +
                "any questions or suggestions about OUR Terms and Conditions, do not hesitate to contact US at :\n"+contact+" \n"+personalinfo+"</p>"+" <p><strong>Permission required : </strong>" +
                "</p> <p> "+permissions +" </p>";

        textview.setText(Html.fromHtml(generartedterms));

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnSave = findViewById(R.id.saveBtn);
        btnSave.setTextColor(Color.BLACK);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(MakerOP.this)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                savePdf();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MakerOP.this);
                                alertDialogBuilder.setTitle("Storage Write");
                                alertDialogBuilder.setMessage("For writing pdf external storage write access is required.");
                                alertDialogBuilder.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                            }

                                        });
                                AlertDialog dialog = alertDialogBuilder.create();
                                dialog.show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
    }

    private void savePdf() {
        //create object of Document class
        Document mDoc = new Document();
        //pdf file name
        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        //pdf file path
        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        try {
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));
            mDoc.open();

            TextView textview = findViewById(R.id.txtSum);
            String mText = textview.getText().toString();

            mDoc.addAuthor("T&C");

            mDoc.add(new Paragraph(mText));

            mDoc.close();
            Toast.makeText(this, mFileName +".pdf\nis saved to\n"+ mFilePath, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}