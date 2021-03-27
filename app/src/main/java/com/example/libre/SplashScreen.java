package com.example.libre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private ImageView logo;
    private TextView name;
    private TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo = findViewById(R.id.splash_logo);
        name = findViewById(R.id.splash_title);
        msg = findViewById(R.id.splash_msg);

        Animation logoAnim = AnimationUtils.loadAnimation(this,R.anim.splash_one);
        Animation titleAnim = AnimationUtils.loadAnimation(this,R.anim.splash_one);
        Animation msgAnim = AnimationUtils.loadAnimation(this,R.anim.splash_one);
        logo.setAnimation(logoAnim);
        name.setAnimation(titleAnim);
        msg.setAnimation(msgAnim);
    }
}