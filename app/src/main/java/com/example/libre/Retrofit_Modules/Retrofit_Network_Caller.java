package com.example.libre.Retrofit_Modules;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.libre.MainActivity;
import com.example.libre.Models.BookModel;
import com.example.libre.Retrofit_Modules.Models.LoginFormat;
import com.example.libre.Retrofit_Modules.Models.Products;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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
    private boolean login_success=false;
    private boolean register_success=false;
    private boolean verification_success=false;

    public Retrofit_Network_Caller(Context context){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://35.193.15.204:3000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api_caller=retrofit.create(API_Caller.class);
        this.context=context;
    }

    public API_Caller getApi_caller(){
        return api_caller;
    }

    public void getAllProducts(){
        Call<List<Products>> call=api_caller.getAllProducts("products/?xerox=book");
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if(response.isSuccessful()){
                    productsList =response.body();
                    for(Products products:productsList){
                        System.out.println("ITEM:"+products.getBookauthor());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(context,"An unexpected error occurred!",Toast.LENGTH_SHORT).show();;
            }
        });
    }


}
