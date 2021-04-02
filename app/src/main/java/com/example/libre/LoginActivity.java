package com.example.libre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libre.Constants.Constants;
import com.example.libre.DaggerSetupFiles.MyApplication;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.LoginFormat;
import com.example.libre.SharedPrefsManager.SharedPrefManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.inject.Inject;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView register;
    TextView verify;
    TextView emailTV;
    TextView passwordTV;
    ProgressBar loginProgress;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailTV=findViewById(R.id.login_emailTV);
        emailTV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    emailTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail_filled,0,0,0);
                }else{
                    emailTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail_icon,0,0,0);
                }
            }
        });
        passwordTV=findViewById(R.id.login_passwordTV);
        passwordTV.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    passwordTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password_filled,0,0,0);

                }else{
                    passwordTV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password_icon,0,0,0);
                }
            }
        });
        login = findViewById(R.id.login_button);
        loginProgress = findViewById(R.id.loginProgress);
        loginProgress.setVisibility(View.INVISIBLE);

        ((MyApplication)getApplication()).getApiComponent().injectLogin(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);
                loginProgress.setVisibility(View.VISIBLE);
            }
        });

        register = findViewById(R.id.login_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                finish();
            }
        });

        verify = findViewById(R.id.login_verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AuthenticationActivity.class));
            }
        });
    }

    public void loginUser(View view){
        String email=emailTV.getText().toString();
        String password=passwordTV.getText().toString();
        if(!email.equals("")&&!password.equals("")){
            LoginFormat loginFormat=new LoginFormat();
            loginFormat.setPassword(password);
            loginFormat.setUsername(email);

            API_Caller api_caller=retrofit.create(API_Caller.class);
            Call<String> call=api_caller.loginUser(loginFormat);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    loginProgress.setVisibility(View.INVISIBLE);
                    String responseBody=response.body();
                    System.out.println("LOGIN RES: "+responseBody);
                    System.out.println("LOGIN RES: "+responseBody.length());
                    if(responseBody.length()==3400){
                        Toast.makeText(getApplicationContext(),"Login Failed! Please enter correct credentials or check your network connection",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                        SharedPrefManager manager=new SharedPrefManager(getApplicationContext());
                        manager.storeKeyValuePair(Constants.LOGIN_EMAIL,email);
                        manager.storeKeyValuePair(Constants.LOGIN_PASS,password);
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loginProgress.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Login Failed! Please enter correct credentials or check your network connection",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Please enter all details!",Toast.LENGTH_SHORT).show();
        }

    }
}