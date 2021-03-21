package com.example.libre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.libre.Adapters.HomeAdapter;
import com.example.libre.Models.BookModel;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.CurrentUser;
import com.example.libre.Retrofit_Modules.Models.Products;
import com.example.libre.Retrofit_Modules.Models.UserDetails;
import com.example.libre.Retrofit_Modules.Retrofit_Network_Caller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<BookModel> books = new ArrayList<>();
    private HomeAdapter adapter;

    @Inject
    Retrofit retrofit;

    @Inject
    Application application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        recyclerView = findViewById(R.id.home_recyclerView);

        ((MyApplication)getApplication()).getApiComponent().injectMain(this);

        adapter = new HomeAdapter();
        adapter.setBooksList(books);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);


        //Using retrofit
        API_Caller caller=retrofit.create(API_Caller.class);
        Call<UserDetails> call=caller.getUserDetailsAfterLogin("products/?xerox=book");
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if(response.isSuccessful()){
                    List<Products> products=response.body().getProducts();
                    CurrentUser user=response.body().getCurrentUser();
                    System.out.println("USER: "+user.getUsername());
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                System.out.println("ERROR: "+t);
            }
        });
    }



}