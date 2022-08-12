package com.omnitech.covidproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.omnitech.covidproject.R;

public class SplashScreen extends AppCompatActivity {


    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        imgView=findViewById(R.id.imgView);



        //imgView.animate().translationY(-800).setDuration(3000);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                finish();
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
            }
        },3000);



    }
}