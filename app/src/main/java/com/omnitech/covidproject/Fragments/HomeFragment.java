package com.omnitech.covidproject.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.omnitech.covidproject.Activities.MainActivity;
import com.omnitech.covidproject.R;


public class HomeFragment extends Fragment {


    LinearLayout lytCallNow;
    LinearLayout lytSmsNow;
    LinearLayout lytTest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initViews(view);

    }

    private void initViews(View view)
    {

        lytCallNow=view.findViewById(R.id.lytCallNow);
        lytSmsNow=view.findViewById(R.id.lytSendSms);
        lytTest=view.findViewById(R.id.lytTest);




        lytCallNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:999"));
                startActivity(intent);

            }
        });



        lytSmsNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + 999));
                intent.putExtra("sms_body", "Type Your Message...");
                startActivity(intent);

            }
        });


        lytTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ( (MainActivity)getActivity()).navigateToWebView();
            }
        });

    }
}