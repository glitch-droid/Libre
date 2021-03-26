package com.example.libre.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Adapters.BookmarkAdapter;
import com.example.libre.Models.BookModel;
import com.example.libre.R;

import java.util.ArrayList;
import java.util.List;

public class BookmarkedFragment extends Fragment implements BookmarkAdapter.OnBookClicked{

    private List<BookModel> bookMarksList = new ArrayList<>();
    private RecyclerView bookmarkRV;
    private BookmarkAdapter adapter;

    public BookmarkedFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bookmarked_fragment_layout,container,false);

        bookmarkRV = view.findViewById(R.id.bookmarks_RV);

        bookMarksList.add(new BookModel("Yo Yo Haani Sinku","Bhai Bhai","ibpsa piasf ivasfivsaipvdfi flhavf vafh jahlvfa vva hjavhf aljfljahf","₹659"));
        bookMarksList.add(new BookModel("Yo Yo Haani Sinku","Bhai Bhai","ibpsa piasf ivasfivsaipvdfi flhavf vafh jahlvfa vva hjavhf aljfljahf","₹659"));
        bookMarksList.add(new BookModel("Yo Yo Haani Sinku","Bhai Bhai","ibpsa piasf ivasfivsaipvdfi flhavf vafh jahlvfa vva hjavhf aljfljahf","₹659"));
        bookMarksList.add(new BookModel("Yo Yo Haani Sinku","Bhai Bhai","ibpsa piasf ivasfivsaipvdfi flhavf vafh jahlvfa vva hjavhf aljfljahf","₹659"));
        bookMarksList.add(new BookModel("Yo Yo Haani Sinku","Bhai Bhai","ibpsa piasf ivasfivsaipvdfi flhavf vafh jahlvfa vva hjavhf aljfljahf","₹659"));
        bookMarksList.add(new BookModel("Yo Yo Haani Sinku","Bhai Bhai","ibpsa piasf ivasfivsaipvdfi flhavf vafh jahlvfa vva hjavhf aljfljahf","₹659"));

        adapter = new BookmarkAdapter(bookMarksList,this);
        bookmarkRV.setAdapter(adapter);
        bookmarkRV.setLayoutManager(new LinearLayoutManager(getContext()));
        bookmarkRV.hasFixedSize();

        return view;
    }

    @Override
    public void onBookClick(int position) {

    }
}
