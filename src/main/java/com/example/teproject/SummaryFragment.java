package com.example.teproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SummaryFragment extends Fragment {
    private static final int RESULT_LOAD_IMAGE = 1;
    String realPath;
    ImageView imageView;
    private static final int PERMISSION_REQUEST = 0;

    public String picturePath;

    boolean selectedImage=false;
    Bitmap bitmap = null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(getContext()));

        final View root = inflater.inflate(R.layout.fragment_summary, container, false);
        final Button btnSum;

        btnSum = (Button)root.findViewById(R.id.btnSum);
        imageView = (ImageView)root.findViewById(R.id.imgAdd);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, RESULT_LOAD_IMAGE);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                alertDialogBuilder.setTitle("Storage Read");
                                alertDialogBuilder.setMessage("For reading images external storage read access is required.");
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
        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedImage) {
                    bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    myWork();
                }
                else
                {
                    Toast.makeText(getActivity(),"You didn't select the image...",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    @SuppressLint("StaticFieldLeak")
    public void myWork() {
        try {
            final ProgressDialog Dialog = new ProgressDialog(getActivity());
            Dialog.setMessage("Summarizing...");
            Dialog.show();

            new AsyncTask<Object, Object, Object>() {

                protected String doInBackground(Object... params) {
                    try {
                        FirebaseVisionImage image;
                        image = FirebaseVisionImage.fromBitmap(bitmap);
                        FirebaseVisionTextRecognizer recognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
                        recognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                String detectedtext="";
                                List<FirebaseVisionText.TextBlock> blockList = firebaseVisionText.getTextBlocks();
                                if(blockList.size() ==0){
                                    Toast.makeText(getActivity(),"No text Found",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    for(int i=0;i<blockList.size();i++) {
                                        List<FirebaseVisionText.Line> lines = blockList.get(i).getLines();
                                        for(int j=0;j<lines.size();j++)
                                        {
                                            detectedtext = detectedtext + lines.get(j).getText()+"\n";
                                        }
                                    }
                                }

                                detectedtext = detectedtext.replaceAll("\\s+"," ");

                                String newop="You didn't select an image.";
                                String textArray[] = detectedtext.split(" ");
                                int wordcount = textArray.length;
                                if (!detectedtext.trim().equals("")) {
                                    if(wordcount<=100)
                                        newop = "Summary can't be created for the given image.";
                                    else {
                                        Python py = Python.getInstance();
                                        final PyObject pyobj = py.getModule("gensim_summary");
                                        PyObject obj = pyobj.callAttr
                                                ("main",detectedtext);
                                        newop = obj.toString();
                                    }
                                }
                                else
                                    newop = "No text detected in given image";
                                Context context = getActivity();
                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        "MyPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove("sum");
                                editor.apply();
                                editor.putString("sum",newop);
                                editor.apply();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                protected void onPostExecute(Object result) {

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Dialog.dismiss();// start your new activity here*/
                    Intent intent = new Intent(getActivity(), SummaryOP.class);
                    startActivity(intent);
                };

            }.execute(null, null, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImage= true;
                    InputStream is = null;
                    try {
                        is = getActivity().getContentResolver().openInputStream(data.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap thisimage = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(thisimage);
                }
            break;
        }
    }
}