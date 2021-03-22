package com.example.libre.DaggerSetupFiles;

import android.app.Application;

import com.example.libre.DaggerSetupFiles.DaggerApiComponent;

public class MyApplication extends Application {
    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        apiComponent= DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .retrofitClientModule(new RetrofitClientModule(getApplicationContext()))
                .build();
    }

    public ApiComponent getApiComponent(){
        return apiComponent;
    }
}
