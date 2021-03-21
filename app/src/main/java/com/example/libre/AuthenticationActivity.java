package com.example.libre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.VerificationFormat;
import com.example.libre.Retrofit_Modules.Retrofit_Network_Caller;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {
    EditText tokenET;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);
        tokenET=findViewById(R.id.verificationToken_ET);
    }

    public void verifyUser(View view){
        String token=tokenET.getText().toString();
        VerificationFormat verificationFormat=new VerificationFormat();
        verificationFormat.setToken(token);
        Retrofit_Network_Caller retrofit_network_caller=new Retrofit_Network_Caller(getApplicationContext());
        API_Caller caller=retrofit_network_caller.getApi_caller();
        Call<String> call=caller.verifyUser(verificationFormat);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String res=response.body();
                    System.out.println("RESPONSE: "+res);
                    Toast.makeText(getApplicationContext(),"Account Verified!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Account verification failed!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
