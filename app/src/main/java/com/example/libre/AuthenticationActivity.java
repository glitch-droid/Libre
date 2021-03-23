package com.example.libre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.libre.DaggerSetupFiles.MyApplication;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.MessageFormat;
import com.example.libre.Retrofit_Modules.Models.VerificationFormat;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthenticationActivity extends AppCompatActivity {
    EditText tokenET;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);
        tokenET=findViewById(R.id.verificationToken_ET);
        ((MyApplication)getApplication()).getApiComponent().injectAuth(this);
    }

    public void verifyUser(View view){
        String token=tokenET.getText().toString();
        if(!token.equals("")){
            VerificationFormat verificationFormat=new VerificationFormat();
            verificationFormat.setToken(token);
            API_Caller caller=retrofit.create(API_Caller.class);
            Call<MessageFormat> call=caller.verifyUser(verificationFormat,"verify/?xerox=book");
            call.enqueue(new Callback<MessageFormat>() {
                @Override
                public void onResponse(Call<MessageFormat> call, Response<MessageFormat> response) {
                    if(response.isSuccessful()){
                        String message=response.body().getMessage();
                        if(message.equals("Wrong code")){
                            Toast.makeText(getApplicationContext(),"Please enter correct code!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Account Verified!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<MessageFormat> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Account verification failed!",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Please enter the token!",Toast.LENGTH_SHORT).show();
        }

    }
}
