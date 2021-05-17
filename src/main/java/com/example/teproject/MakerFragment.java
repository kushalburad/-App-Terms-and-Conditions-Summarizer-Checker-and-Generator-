package com.example.teproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class MakerFragment extends Fragment {
    boolean scrollEnabled=false;
    ViewPager viewPager;
    SpringDotsIndicator springDotsIndicator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_maker, container, false);
        viewPager = root.findViewById(R.id.view_pager);
        springDotsIndicator = root.findViewById(R.id.dots);
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        springDotsIndicator.setViewPager(viewPager);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==1) {

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollEnabled=false;
                return true;
            }
        });

        Button next = root.findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollEnabled= true;
                if(viewPager.getCurrentItem()==0) {
                    EditText editName = getActivity().findViewById(R.id.editTextName);
                    EditText editContact = getActivity().findViewById(R.id.editTextPhone);
                    EditText editPersonal = getActivity().findViewById(R.id.editTextInfo);
                    if (editName.getText().toString().trim().equals("")) {
                        viewPager.setCurrentItem(0);
                        Toast.makeText(getActivity(), "App Name can't be blank", Toast.LENGTH_SHORT).show();
                    }
                    else if (editPersonal.getText().toString().trim().equals("")) {
                        viewPager.setCurrentItem(0);
                        Toast.makeText(getActivity(), "Personally Indetifiable Info can't be blank", Toast.LENGTH_SHORT).show();
                    }
                    else if (editContact.getText().toString().trim().equals("")) {
                        viewPager.setCurrentItem(0);
                        Toast.makeText(getActivity(), "Contact Info can't be blank", Toast.LENGTH_SHORT).show();
                    }
                    else
                        viewPager.setCurrentItem(1);
                }
                else if(viewPager.getCurrentItem()==1) {
                    RadioGroup radioGroup = getActivity().findViewById(R.id.radioGroup);
                    if(radioGroup.getCheckedRadioButtonId()==-1)
                    {
                        viewPager.setCurrentItem(1);
                        Toast.makeText(getActivity(), "App type must be selected", Toast.LENGTH_SHORT).show();
                    }
                    else
                    viewPager.setCurrentItem(2);
                }
                else if(viewPager.getCurrentItem()==2) {
                    RadioGroup radioGroup = getActivity().findViewById(R.id.radioGroupCompany);
                    EditText companyindividual = getActivity().findViewById(R.id.editCI);
                    if(radioGroup.getCheckedRadioButtonId()==-1)
                    {
                        viewPager.setCurrentItem(1);
                        Toast.makeText(getActivity(), "Select owner of the app", Toast.LENGTH_SHORT).show();
                    }
                    else if(companyindividual.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(getActivity(), "Enter the name", Toast.LENGTH_SHORT).show();
                    }
                    else
                        viewPager.setCurrentItem(3);
                }
                else if(viewPager.getCurrentItem()==3) {
                    int cnt = 1;
                    String permissions="";
                    CheckBox checkBody = getActivity().findViewById(R.id.checkBody);
                    CheckBox checkCalender = getActivity().findViewById(R.id.checkCalender);
                    CheckBox checkCallHistory = getActivity().findViewById(R.id.checkCallHistory);
                    CheckBox checkContacts = getActivity().findViewById(R.id.checkContacts);
                    CheckBox checkCamera = getActivity().findViewById(R.id.checkCamera);
                    CheckBox checkLocation = getActivity().findViewById(R.id.checkLocation);
                    CheckBox checkMicro = getActivity().findViewById(R.id.checkMicro);
                    CheckBox checkSMS = getActivity().findViewById(R.id.checkSMS);
                    CheckBox checkPhone = getActivity().findViewById(R.id.checkPhone);
                    CheckBox checkStorage = getActivity().findViewById(R.id.checkStorage);
                    CheckBox checkMotion = getActivity().findViewById(R.id.checkMotion);

                    if(checkBody.isChecked()) {
                        permissions = permissions + cnt + ")" + checkBody.getText() + "<br></br>";
                        cnt++;
                    }
                    if(checkCalender.isChecked()) {
                        permissions = permissions + cnt + ")" + checkCalender.getText() + "<br></br>";
                        cnt++;
                    }
                    if(checkCallHistory.isChecked()){
                        permissions = permissions+cnt+")"+checkCallHistory.getText()+"<br></br>";
                    cnt++;
                    }
                    if(checkContacts.isChecked()){
                        permissions = permissions+cnt+")"+checkContacts.getText()+"<br></br>";
                    cnt++;
                    }
                    if(checkCamera.isChecked()){
                        permissions = permissions+cnt+")"+checkCamera.getText()+"<br></br>";
                    cnt++;
                    }
                    if(checkLocation.isChecked()){
                        permissions = permissions+cnt+")"+checkLocation.getText()+"<br></br>";
                    cnt++;
                    }
                    if(checkMicro.isChecked()){
                        permissions = permissions+cnt+")"+checkMicro.getText()+"<br></br>";
                                cnt++;
                                }
                    if(checkSMS.isChecked()){
                        permissions = permissions+cnt+")"+checkSMS.getText()+"<br></br>";
                                cnt++;
                                }
                    if(checkPhone.isChecked()){
                        permissions = permissions+cnt+")"+checkPhone.getText()+"<br></br>";
                                cnt++;
                                }
                    if(checkStorage.isChecked()){
                        permissions = permissions+cnt+")"+checkStorage.getText()+"<br></br>";
                                cnt++;
                                }
                    if(checkMotion.isChecked()){
                        permissions = permissions+cnt+")"+checkMotion.getText()+"<br></br>";
                                cnt++;
                                }

                    Toast.makeText(getActivity(), "Finishing up !", Toast.LENGTH_SHORT).show();
                    Context context = getActivity();
                    SharedPreferences sharedPref = context.getSharedPreferences(
                            "MyPref", Context.MODE_PRIVATE);
                    EditText editText = getActivity().findViewById(R.id.editTextName);
                    String appname = editText.getText().toString();
                    editText = getActivity().findViewById(R.id.editTextInfo);
                    String personalinfo = editText.getText().toString();
                    editText = getActivity().findViewById(R.id.editTextPhone);
                    String contact = editText.getText().toString();
                    String apptype;
                    editText = getActivity().findViewById(R.id.editCI);
                    String ci = editText.getText().toString();
                    RadioGroup radioGroup = getActivity().findViewById(R.id.radioGroup);
                    if(radioGroup.getCheckedRadioButtonId()==0)
                    {
                        apptype = "Free";
                    }
                    else if(radioGroup.getCheckedRadioButtonId()==1)
                    {
                        apptype = "Open Source";
                    }
                    else if(radioGroup.getCheckedRadioButtonId()==2)
                    {
                        apptype = "Freemium";
                    }
                    else if(radioGroup.getCheckedRadioButtonId()==3)
                    {
                        apptype = "AD Supported";
                    }
                    else if(radioGroup.getCheckedRadioButtonId()==4)
                    {
                        apptype = "Paid";
                    }
                    else
                    {
                        apptype = "Free";
                    }

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.apply();
                    editor.putString("appname",appname);
                    editor.putString("personalinfo",personalinfo);
                    editor.putString("apptype",apptype);
                    editor.putString("contact",contact);
                    editor.putString("companyname",ci);
                    editor.putString("permissions",permissions);
                    editor.apply();
                    Intent intent = new Intent(getActivity(),MakerOP.class);
                    startActivity(intent);
                }
            }
        });

        Button back = root.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollEnabled= true;
                if(viewPager.getCurrentItem()==0)
                    Toast.makeText(getActivity(),"Already at first",Toast.LENGTH_SHORT).show();
                else if(viewPager.getCurrentItem()==1)
                    viewPager.setCurrentItem(0);
                else if(viewPager.getCurrentItem()==2)
                    viewPager.setCurrentItem(1);
                else if(viewPager.getCurrentItem()==3)
                    viewPager.setCurrentItem(2);
            }
        });
        return root;
    }
}