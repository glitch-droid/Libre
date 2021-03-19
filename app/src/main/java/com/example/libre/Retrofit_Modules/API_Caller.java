package com.example.libre.Retrofit_Modules;

import com.example.libre.Retrofit_Modules.Models.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface API_Caller {
    @GET
    Call<List<Products>> getAllProducts(@Url String url);
}
