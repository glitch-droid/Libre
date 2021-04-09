package com.example.libre.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.libre.Adapters.BookmarkAdapter;
import com.example.libre.BookDetail;
import com.example.libre.Constants.Constants;
import com.example.libre.Models.BookModel;
import com.example.libre.R;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.FillMyProducts;
import com.example.libre.Retrofit_Modules.Models.CurrentUser;
import com.example.libre.SharedPrefsManager.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookmarkedFragment extends Fragment implements BookmarkAdapter.OnBookClicked{

    private List<BookModel> bookMarksList = new ArrayList<>();
    private List<BookModel> checkingList = new ArrayList<>();
    private RecyclerView bookmarkRV;
    private ProgressBar loadingProgressBar;
    private BookmarkAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private String currentUID;
    private Retrofit retrofit;

    public BookmarkedFragment(Retrofit retrofit) {
        this.retrofit=retrofit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bookmarked_fragment_layout,container,false);

        bookmarkRV = view.findViewById(R.id.bookmarks_RV);
        bookmarkRV.setItemAnimator(new SlideInDownAnimator());
        refreshLayout=view.findViewById(R.id.bookmark_swipeToRefresh);
        loadingProgressBar=view.findViewById(R.id.progressBar);
        loadingProgressBar.setVisibility(View.VISIBLE);
        SharedPrefManager manager=new SharedPrefManager(getContext());
        currentUID=manager.getValue(Constants.CURRENT_USER);
        adapter = new BookmarkAdapter(bookMarksList,this,getContext(),retrofit);
        bookmarkRV.setAdapter(adapter);
        bookmarkRV.setLayoutManager(new LinearLayoutManager(getContext()));
        bookmarkRV.hasFixedSize();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchBookmarks();
            }
        });
        fetchBookmarks();
        System.out.println("SIZE: "+adapter.getItemCount());
        return view;
    }

    private void fetchBookmarks(){
        API_Caller caller=retrofit.create(API_Caller.class);
        Call<CurrentUser> currentUserCall=caller.getUserFromID("profiles/"+currentUID+"/?xerox=book");
        currentUserCall.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                if(response.isSuccessful()){
                    bookMarksList.clear();
                    CurrentUser user=response.body();
                    FillMyProducts products=new FillMyProducts(retrofit,loadingProgressBar);
                    products.fillIntoList(user.getBookmarks(),bookMarksList,adapter,getContext());
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {

            }
        });
    }
    @Override
    public void onBookClick(int position) {
        BookModel bookModel = bookMarksList.get(position);
        Intent intent=new Intent(getContext(),BookDetail.class);
        intent.putExtra("id",bookModel.getId());
        intent.putExtra("uid",currentUID);
        intent.putExtra("status","read");
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in,R.anim.cover_fade);
    }
}
