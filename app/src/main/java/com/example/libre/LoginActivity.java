package com.example.libre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.CurrentUser;
import com.example.libre.Retrofit_Modules.Models.LoginFormat;
import com.example.libre.Retrofit_Modules.Models.Products;
import com.example.libre.Retrofit_Modules.Models.UserDetails;
import com.example.libre.Retrofit_Modules.Retrofit_Network_Caller;
import com.google.android.material.button.MaterialButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;

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
    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();

        emailTV=findViewById(R.id.login_emailTV);
        passwordTV=findViewById(R.id.login_passwordTV);

        ((MyApplication)getApplication()).getApiComponent().injectLogin(this);

        login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailTV.getText().toString();
                String password=passwordTV.getText().toString();
                loginUser(email,password);
            }
        });

        register = findViewById(R.id.login_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
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
    public void loginUser(String username,String password){
        LoginFormat loginFormat=new LoginFormat();
        loginFormat.setPassword(password);
        loginFormat.setUsername(username);

        API_Caller api_caller=retrofit.create(API_Caller.class);
        Call<String> call=api_caller.loginUser(loginFormat);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseBody=response.body();
                Headers receivedHeader=response.headers();
                System.out.println("USERNAME: "+responseBody);
                Document document= Jsoup.parse(responseBody);
                Elements elements=document.select("input");
                if(elements.size()!=0){
                    Toast.makeText(getApplicationContext(),"Login Failed! Please enter correct credentials or check your network connection",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
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
