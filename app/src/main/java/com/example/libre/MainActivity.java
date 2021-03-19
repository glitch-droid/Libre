package com.example.libre;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.libre.Adapters.HomeAdapter;
import com.example.libre.Models.BookModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<BookModel> books = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.home_recyclerView);
        books.add(new BookModel("Digital Electronics","Anand Kumar"));
        books.add(new BookModel("Power Electronics","PS Bimrah"));
        books.add(new BookModel("Harry Potter","JK Rowling"));
        books.add(new BookModel("Digital Electronics","Anand Kumar"));
        books.add(new BookModel("Power Electronics","PS Bimrah"));
        books.add(new BookModel("Harry Potter","JK Rowling"));
        books.add(new BookModel("Digital Electronics","Anand Kumar"));
        books.add(new BookModel("Power Electronics","PS Bimrah"));
        books.add(new BookModel("Harry Potter","JK Rowling"));

        HomeAdapter adapter = new HomeAdapter();
        adapter.setBooksList(books);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);
    }
}