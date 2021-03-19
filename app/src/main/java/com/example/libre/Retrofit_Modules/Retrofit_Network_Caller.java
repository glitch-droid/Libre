package com.example.libre.Retrofit_Modules;

import android.content.Context;
import android.widget.Toast;

import com.example.libre.Models.BookModel;
import com.example.libre.Retrofit_Modules.Models.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_Network_Caller {

    private List<Products> productsList=new ArrayList<>();
    private Context context;
    private API_Caller api_caller;


    public Retrofit_Network_Caller(Context context){
        this.context=context;
    }

    public void getAllProducts(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://35.193.15.204:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api_caller=retrofit.create(API_Caller.class);

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
