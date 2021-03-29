package com.example.libre.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.libre.Adapters.HomeAdapter;
import com.example.libre.BookDetail;
import com.example.libre.Constants.Constants;
import com.example.libre.Models.BookModel;
import com.example.libre.R;
import com.example.libre.Retrofit_Modules.Models.AllProducts;
import com.example.libre.SharedPrefsManager.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.CurrentUser;
import com.example.libre.Retrofit_Modules.Models.Products;
import com.example.libre.Retrofit_Modules.Models.UserDetails;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements HomeAdapter.OnBookListenerHome{

    private Retrofit retrofit;
    private SharedPrefManager sharedPrefManager;
    private List<BookModel> bookModelList;
    private boolean clicked = false;
    private SwipeRefreshLayout refreshLayout;
    private String currentUid;
    private ProgressBar homeProgress;
    public HomeFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home_fragment_layout,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.homeFragmentRV);
        sharedPrefManager=new SharedPrefManager(getContext());

        homeProgress = view.findViewById(R.id.homeProgress);

        HomeAdapter adapter = new HomeAdapter(this);
        bookModelList = new ArrayList<>();
        refreshLayout= view.findViewById(R.id.swipeRefreshLayout);
        adapter.setBooksList(bookModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.hasFixedSize();
        loadDataIntoRecyclerView(adapter);
        adapter.notifyDataSetChanged();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataIntoRecyclerView(adapter);
            }
        });

        return view;
    }

    private void loadDataIntoRecyclerView(HomeAdapter adapter){
        API_Caller caller=retrofit.create(API_Caller.class);
        Call<UserDetails> call=caller.getUserDetailsAfterLogin("products/?xerox=book");
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if(response.isSuccessful()&&response.body().getCurrentUser()!=null){
                    bookModelList.clear();
                    List<AllProducts> books=response.body().getProducts();
                    CurrentUser user=response.body().getCurrentUser();
                    currentUid=user.getId();
                    sharedPrefManager.storeKeyValuePair(Constants.CURRENT_USER,currentUid);
                    sharedPrefManager.storeKeyValuePair(Constants.USER_NAME,user.getName());
                    sharedPrefManager.storeKeyValuePair(Constants.USER_EMAIL,user.getUsername());
                    System.out.println("TEST: "+user.getId());
                    for(AllProducts prod:books){
                        if(prod.getImage().size()!=0){
                            bookModelList.add(new BookModel(prod.getTitle(),prod.getBookauthor(),prod.getDescription(),String.valueOf(prod.getAmount()),prod.getImage().get(0),prod.get_id()));
                        }else{
                            bookModelList.add(new BookModel(prod.getTitle(),prod.getBookauthor(),prod.getDescription(),String.valueOf(prod.getAmount()),"",prod.get_id()));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    homeProgress.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(getContext(),"Internal server error! Please restart the app or wait till the error gets resolved!",Toast.LENGTH_LONG).show();
                    refreshLayout.setRefreshing(false);
                    homeProgress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                System.out.println("ERROR: "+t);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public HomeFragment(Retrofit retrofit) {
        this.retrofit=retrofit;
    }

    @Override
    public void onBookClick(int position) {
        BookModel bookModel = bookModelList.get(position);
        Intent intent=new Intent(getContext(),BookDetail.class);
        intent.putExtra("id",bookModel.getId());
        intent.putExtra("uid",currentUid);
        intent.putExtra("status","read");
        startActivity(intent);
    }
}
