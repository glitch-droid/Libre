package com.example.libre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libre.Constants.Constants;
import com.example.libre.DaggerSetupFiles.MyApplication;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.LoginFormat;
import com.example.libre.SharedPrefsManager.SharedPrefManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashScreen extends AppCompatActivity {
    private ImageView logo;
    private TextView name;
    private TextView msg;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo = findViewById(R.id.splash_logo);
        name = findViewById(R.id.splash_title);
        msg = findViewById(R.id.splash_msg);

        ((MyApplication)getApplication()).getApiComponent().injectInSplashScreen(this);


        Animation logoAnim = AnimationUtils.loadAnimation(this,R.anim.splash_one);
        Animation titleAnim = AnimationUtils.loadAnimation(this,R.anim.splash_one);
        Animation msgAnim = AnimationUtils.loadAnimation(this,R.anim.splash_one);
        logo.setAnimation(logoAnim);
        name.setAnimation(titleAnim);
        msg.setAnimation(msgAnim);
        SharedPrefManager manager=new SharedPrefManager(getApplicationContext());
        String email=manager.getValue(Constants.LOGIN_EMAIL);
        String password=manager.getValue(Constants.LOGIN_PASS);
        if(email!=null&&password!=null){
            loginUser(email,password);
        }else{
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
            }, 1500);
        }
    }

    private void loginUser(String email, String password) {
        LoginFormat loginFormat=new LoginFormat();
        loginFormat.setPassword(password);
        loginFormat.setUsername(email);

        API_Caller api_caller=retrofit.create(API_Caller.class);
        Call<String> call=api_caller.loginUser(loginFormat);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseBody=response.body();

                System.out.println("USERNAME: "+responseBody);
                Document document= Jsoup.parse(responseBody);
                Elements elements=document.select("input");
                if(elements.size()!=0){
                    Toast.makeText(getApplicationContext(),"Login Failed! Please enter correct credentials or check your network connection",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPrefManager manager=new SharedPrefManager(getApplicationContext());
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Login Failed! Please enter correct credentials or check your network connection",Toast.LENGTH_SHORT).show();;
            }
        });
    }
}