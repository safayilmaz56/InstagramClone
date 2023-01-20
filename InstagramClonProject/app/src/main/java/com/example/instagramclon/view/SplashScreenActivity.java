package com.example.instagramclon.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import com.example.instagramclon.R;
import com.example.instagramclon.databinding.ActivitySplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    private ProgressBar mProgress;

    private float maksimumProgress=100f,artacakProgress,progressMiktari=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();

        mProgress = (ProgressBar) findViewById(R.id.progressBar);


        try{
            new CountDownTimer(1500,1000){
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    if (user !=null){
                        Intent intent=new Intent(SplashScreenActivity.this,FeedActivity.class);
                        startActivity(intent);
                        finish();
                    }else if(user==null){
                        Intent mainIntent = new Intent(SplashScreenActivity.this,MainActivity.class);
                        finish();
                        startActivity(mainIntent);
                    }

                }
            }.start();

        }catch(Exception e){

        }

    }
}