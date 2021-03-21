package com.example.libre.Retrofit_Modules;

import android.service.autofill.UserData;

import com.example.libre.Retrofit_Modules.Models.CurrentUser;
import com.example.libre.Retrofit_Modules.Models.LoginFormat;
import com.example.libre.Retrofit_Modules.Models.MessageFormat;
import com.example.libre.Retrofit_Modules.Models.Products;
import com.example.libre.Retrofit_Modules.Models.RegisterFormat;
import com.example.libre.Retrofit_Modules.Models.UserDetails;
import com.example.libre.Retrofit_Modules.Models.VerificationFormat;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface API_Caller {
    @GET
    Call<List<Products>> getAllProducts(@Url String url);

    @POST("login")
    Call<String> loginUser(@Body LoginFormat loginFormat);

    @POST("register")
    Call<String> registerUser(@Body RegisterFormat registerFormat);

    @POST
    Call<MessageFormat> verifyUser(@Body VerificationFormat verificationFormat,@Url String url);

    @GET
    Call<UserDetails> getUserDetailsAfterLogin(@Url String url);

}
