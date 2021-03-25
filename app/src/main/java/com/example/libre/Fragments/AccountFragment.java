package com.example.libre.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Adapters.MyUploadsAdapter;
import com.example.libre.BookDetail;
import com.example.libre.Models.BookModel;
import com.example.libre.R;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment implements MyUploadsAdapter.MyBookListener {

    private List<BookModel> myBooks = new ArrayList<>();
    private RecyclerView myBooksRV;
    private MyUploadsAdapter myAdapter;
    private LinearLayoutManager horizontalLayout;

    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.account_fragment_layout,container,false);

        myBooksRV = view.findViewById(R.id.account_myBooksRV);
        myBooks.add(new BookModel("Yo Yo Honey Singh","₹20"));
        myBooks.add(new BookModel("Yo Yo Honey Singh","₹20"));
        myBooks.add(new BookModel("Yo Yo Honey Singh","₹20"));
        myBooks.add(new BookModel("Yo Yo Honey Singh","₹20"));
        myBooks.add(new BookModel("Yo Yo Honey Singh","₹20"));

        myAdapter = new MyUploadsAdapter(myBooks,this);

        myBooksRV.setAdapter(myAdapter);
        horizontalLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        myBooksRV.setLayoutManager(horizontalLayout);
        myBooksRV.hasFixedSize();
        return view;
    }

    @Override
    public void onMyBookClick(int position) {
        startActivity(new Intent(getContext(), BookDetail.class));
    }
}
