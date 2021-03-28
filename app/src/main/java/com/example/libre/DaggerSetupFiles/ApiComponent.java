package com.example.libre.DaggerSetupFiles;

import com.example.libre.AddBook;
import com.example.libre.AuthenticationActivity;
import com.example.libre.BookDetail;
import com.example.libre.CommentsActivity;
import com.example.libre.LoginActivity;
import com.example.libre.MainActivity;
import com.example.libre.RegisterActivity;
import com.example.libre.SplashScreen;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RetrofitClientModule.class,AppModule.class})
public interface ApiComponent {

    void injectMain(MainActivity mainActivity);

    void injectLogin(LoginActivity loginActivity);

    void injectRegister(RegisterActivity registerActivity);

    void injectAuth(AuthenticationActivity authenticationActivity);

    void injectInAddBook(AddBook addBook);

    void injectInBookDetails(BookDetail bookDetail);

    void injectInCommentsActivity(CommentsActivity commentsActivity);

    void injectInSplashScreen(SplashScreen splashScreen);
}
