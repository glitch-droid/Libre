package com.example.libre.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Adapters.HomeAdapter;
import com.example.libre.Models.BookModel;
import com.example.libre.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private boolean clicked = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home_fragment_layout,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.homeFragmentRV);

        HomeAdapter adapter = new HomeAdapter();
        List<BookModel> bookModelList = new ArrayList<>();

        String rr = String.valueOf(R.string.loren_ipsum);

        bookModelList.add(new BookModel("Modern Control Engineering","By  Katsuhiko Ogata",rr,"₹559"));
        bookModelList.add(new BookModel("Modern Control Engineering","By  Katsuhiko Ogata",rr,"₹559"));
        bookModelList.add(new BookModel("Modern Control Engineering","By  Katsuhiko Ogata",rr,"₹559"));
        bookModelList.add(new BookModel("Modern Control Engineering","By  Katsuhiko Ogata",rr,"₹559"));
        bookModelList.add(new BookModel("Modern Control Engineering","By  Katsuhiko Ogata",rr,"₹559"));
        bookModelList.add(new BookModel("Modern Control Engineering","By  Katsuhiko Ogata",rr,"₹559"));
        bookModelList.add(new BookModel("Modern Control Engineering","By  Katsuhiko Ogata",rr,"₹559"));
        adapter.setBooksList(bookModelList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.hasFixedSize();

        return view;
    }


    public HomeFragment() {
    }
}
