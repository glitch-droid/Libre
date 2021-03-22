package com.example.libre.DaggerSetupFiles;

import com.example.libre.AuthenticationActivity;
import com.example.libre.LoginActivity;
import com.example.libre.MainActivity;
import com.example.libre.RegisterActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RetrofitClientModule.class,AppModule.class})
public interface ApiComponent {

    void injectMain(MainActivity mainActivity);

    void injectLogin(LoginActivity loginActivity);

    void injectRegister(RegisterActivity registerActivity);

    void injectAuth(AuthenticationActivity authenticationActivity);
}
