package com.example.libre.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements HomeAdapter.OnBookListenerHome {

    private Retrofit retrofit;
    private SharedPrefManager sharedPrefManager;
    private List<BookModel> bookModelList;
    private boolean clicked = false;
    private SwipeRefreshLayout refreshLayout;
    private String currentUid;
    private ProgressBar homeProgress;
    private androidx.appcompat.widget.SearchView searchView;


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
        homeProgress.setVisibility(View.VISIBLE);
        HomeAdapter adapter = new HomeAdapter(this);
        bookModelList = new ArrayList<>();
        refreshLayout= view.findViewById(R.id.swipeRefreshLayout);
        searchView=view.findViewById(R.id.homeFrag_searchView);

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

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProduct(newText,adapter);
                return false;
            }
        });

        return view;
    }

    private void searchProduct(String queryText,HomeAdapter adapter){
        if(queryText.equals("")){
            loadDataIntoRecyclerView(adapter);
        }else{
            bookModelList.clear();
            adapter.notifyDataSetChanged();
            homeProgress.setVisibility(View.VISIBLE);
            API_Caller caller=retrofit.create(API_Caller.class);
            Call<List<AllProducts>> searchCall=caller.searchProducts("search?xerox=book&search="+queryText);
            searchCall.enqueue(new Callback<List<AllProducts>>() {
                @Override
                public void onResponse(Call<List<AllProducts>> call, Response<List<AllProducts>> response) {
                    bookModelList.clear();
                    List<AllProducts> searchProdList=response.body();
                    for(AllProducts products:searchProdList){
                        if(products.getImage().size()!=0){
                            bookModelList.add(new BookModel(products.getTitle(),products.getBookauthor(),products.getDescription(),products.getAmount(),products.getImage().get(0),products.get_id()));
                        }else{
                            bookModelList.add(new BookModel(products.getTitle(),products.getBookauthor(),products.getDescription(),products.getAmount(),"",products.get_id()));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    homeProgress.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<List<AllProducts>> call, Throwable t) {
                    homeProgress.setVisibility(View.INVISIBLE);
                }
            });
        }

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
