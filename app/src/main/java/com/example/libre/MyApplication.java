package com.example.libre;

import android.app.Application;

public class MyApplication extends Application {
    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        apiComponent=DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .retrofitClientModule(new RetrofitClientModule(getApplicationContext()))
                .build();
    }

    public ApiComponent getApiComponent(){
        return apiComponent;
    }
}
