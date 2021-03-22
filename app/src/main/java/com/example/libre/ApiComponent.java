package com.example.libre;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

@Singleton
@Component(modules = {RetrofitClientModule.class,AppModule.class})
public interface ApiComponent {

    void injectMain(MainActivity mainActivity);

    void injectLogin(LoginActivity loginActivity);

    void injectRegister(RegisterActivity registerActivity);

    void injectAuth(AuthenticationActivity authenticationActivity);
}
