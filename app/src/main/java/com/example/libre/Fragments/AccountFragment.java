package com.example.libre.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.libre.Adapters.MyUploadsAdapter;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AccountFragment extends Fragment implements MyUploadsAdapter.MyBookListener {

    private List<BookModel> myBooks = new ArrayList<>();
    private RecyclerView myBooksRV;
    private TextView userNameTV,userEmailTV,firstLetterTV;
    private MyUploadsAdapter myAdapter;
    private LinearLayoutManager horizontalLayout;
    private String currentUID;
    private SharedPrefManager manager;
    private Retrofit retrofit;
    private API_Caller api_caller;
    private SwipeRefreshLayout swipeRefreshLayout;

    public AccountFragment(Retrofit retrofit) {
        this.retrofit=retrofit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.account_fragment_layout,container,false);

        swipeRefreshLayout=view.findViewById(R.id.swipe_Refresh_Layout);
        api_caller=retrofit.create(API_Caller.class);
        manager=new SharedPrefManager(getContext());

        myBooksRV = view.findViewById(R.id.account_myBooksRV);
        userNameTV=view.findViewById(R.id.account_userNameTV);
        userEmailTV=view.findViewById(R.id.account_userEmailTV);
        firstLetterTV=view.findViewById(R.id.account_firstLetterTV);

        currentUID=manager.getValue(Constants.CURRENT_USER);
        String username=manager.getValue(Constants.USER_NAME);
        String email=manager.getValue(Constants.USER_EMAIL);
        userNameTV.setText(username);
        userEmailTV.setText(email);
        Character firstLetter=username.toUpperCase().charAt(0);
        firstLetterTV.setText(firstLetter.toString());
        myAdapter = new MyUploadsAdapter(myBooks,this);

        myBooksRV.setAdapter(myAdapter);
        horizontalLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        myBooksRV.setLayoutManager(horizontalLayout);
        myBooksRV.hasFixedSize();
        fillAllDetails(currentUID);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillAllDetails(currentUID);
            }
        });
        return view;
    }

    private void fillAllDetails(String userId){
        Call<CurrentUser> currentUserCall=api_caller.getUserFromID("profiles/"+userId+"/?xerox=book");
        currentUserCall.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                if(response.isSuccessful()){
                    myBooks.clear();
                    CurrentUser currentUser=response.body();
                    FillMyProducts fillMyProducts=new FillMyProducts(retrofit);
                    fillMyProducts.fillIntoList(currentUser.getMyproducts(),myBooks,myAdapter);
                    myAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {
                System.out.println("ERROR: Cant load the products!");
            }
        });
    }

    @Override
    public void onMyBookClick(int position) {
        BookModel bookModel = myBooks.get(position);
        Intent intent=new Intent(getContext(),BookDetail.class);
        intent.putExtra("id",bookModel.getId());
        intent.putExtra("uid",currentUID);
        intent.putExtra("status","edit");
        startActivity(intent);
    }
}
