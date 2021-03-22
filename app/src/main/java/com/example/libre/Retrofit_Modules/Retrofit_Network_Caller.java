package com.example.libre.Retrofit_Modules;

import android.content.Context;
import android.content.Intent;
import android.service.autofill.UserData;
import android.widget.Toast;

import com.example.libre.MainActivity;
import com.example.libre.Models.BookModel;
import com.example.libre.Retrofit_Modules.Models.CurrentUser;
import com.example.libre.Retrofit_Modules.Models.LoginFormat;
import com.example.libre.Retrofit_Modules.Models.Products;
import com.example.libre.Retrofit_Modules.Models.UserDetails;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Retrofit_Network_Caller {

    private List<Products> productsList=new ArrayList<>();
    private Context context;
    private API_Caller api_caller;

    public Retrofit_Network_Caller(Context context){

    }

    public API_Caller getApi_caller(){
        return api_caller;
    }

}
