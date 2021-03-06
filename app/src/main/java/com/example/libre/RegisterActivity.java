package com.example.libre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libre.DaggerSetupFiles.MyApplication;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.RegisterFormat;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    TextView loginHere;
    EditText emailET,nameET,areaET,cityET,countryET,passwordET,confirmPasswordET;
    Button registerButton;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        ((MyApplication)getApplication()).getApiComponent().injectRegister(this);

        emailET=findViewById(R.id.register_emailTV);
        nameET=findViewById(R.id.register_nameTV);
        areaET=findViewById(R.id.register_areaTV);
        cityET=findViewById(R.id.register_cityTV);
        countryET=findViewById(R.id.register_countryTV);
        passwordET=findViewById(R.id.register_passwordTV);
        confirmPasswordET=findViewById(R.id.register_confirmPasswordTV);

        loginHere = findViewById(R.id.register_login);
        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });



        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser(){
        String email=emailET.getText().toString();
        String name=nameET.getText().toString();
        String area=areaET.getText().toString();
        String city=cityET.getText().toString();
        String country=countryET.getText().toString();
        String password=passwordET.getText().toString();
        String confirmPasswordString=confirmPasswordET.getText().toString();

        if(confirmPasswordString.equals(password)&&
                !email.equals("")&&
                !name.equals("")&&
                !area.equals("")&&
                !city.equals("")&&
                !country.equals("")&&
                !password.equals("")){
            API_Caller api_caller=retrofit.create(API_Caller.class);
            RegisterFormat registerFormat=new RegisterFormat();
            registerFormat.setEmail(email);
            registerFormat.setName(name);
            registerFormat.setArea(area);
            registerFormat.setCity(city);
            registerFormat.setCountry(country);
            registerFormat.setPassword(password);
            Call<String> call=api_caller.registerUser(registerFormat);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        String responseText=response.body();
                        System.out.println("REGISTER: "+responseText);
                        Toast.makeText(getApplicationContext(),"Registered!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),AuthenticationActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Failed to Register!",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Please enter all details!",Toast.LENGTH_SHORT).show();
        }
    }
}
