package com.example.libre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.libre.Adapters.HomeAdapter;
import com.example.libre.Models.BookModel;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<BookModel> books = new ArrayList<>();
    private API_Caller api_caller;
    private HomeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.home_recyclerView);
        books.add(new BookModel("Digital Electronics","Anand Kumar"));

        adapter = new HomeAdapter();
        adapter.setBooksList(books);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);
        getDataFromWeb();
    }

    public void getDataFromWeb(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://35.193.15.204:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api_caller=retrofit.create(API_Caller.class);
        Call<List<Products>> call=api_caller.getAllProducts("products/?xerox=book");
        Toast.makeText(getApplicationContext(),"Initialised!",Toast.LENGTH_SHORT).show();;
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if(response.isSuccessful()){
                    List<Products> allProds=response.body();

                    for(Products prod:allProds){
                        books.add(new BookModel(prod.getTitle(),prod.getBookauthor()));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                System.out.println("ERROR: "+t);
            }
        });
    }
}