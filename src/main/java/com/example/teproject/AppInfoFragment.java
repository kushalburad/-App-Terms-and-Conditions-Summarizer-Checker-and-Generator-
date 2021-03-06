package com.example.teproject;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AppInfoFragment extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.appinfo, container, false);
        TextView textContent1 = root.findViewById(R.id.textContent1);
        TextView textContent2 = root.findViewById(R.id.textContent2);
        TextView textContent3 = root.findViewById(R.id.textContent3);
        String str1 = "\t\t\t\tWe believe that before downloading any app it's very crucial to go through its Terms and Conditions section. We know it's tempting to <b>skip the T and C section</b>, but it's important to establish what you can expect from the app and what the app expects from you. Violation of these policies may result in some <b>serious consequences</b>.";
        textContent1.setText(Html.fromHtml(str1));
        String str2 = "\u25CF\t\t\t\tSince the majority of people find reading T and C sections time consuming we have introduced a <b>Summary Feature</b> in our app which extracts important information from T and C section and generates a summary. <br></br>\u25CF\t\t\t\tFurthermore it extracts <b>Critical Information</b> present in T andC section which is very important for the users to know in advance. Given a clear and intelligible input image users can confidently rely on the results generated by the app as logic behind the code is accurate and unbiased.<br></br>\u25CF\t\t\t\tOur app also provides users with an additional feature of <b>Generating Terms and Conditions</b> for their personal use. All the information of our users are safe and will not be shared with any third party apps. We do respect our customers' privacy.";
        textContent2.setText(Html.fromHtml(str2));
        String str3 = "\u25BA\t\t\t\tExternal Storage Reading\n\u25BA\t\t\t\tExternal Storage Writing";
        textContent3.setText(str3);
        return root;
    }
}