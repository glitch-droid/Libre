package com.example.libre.Retrofit_Modules;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Adapters.MyUploadsAdapter;
import com.example.libre.Models.BookModel;
import com.example.libre.Retrofit_Modules.Models.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FillMyProducts {
    private Retrofit retrofit;
    private ProgressBar progressBar;

    public FillMyProducts(Retrofit retrofit, ProgressBar progressBar){
        this.progressBar=progressBar;
        this.retrofit=retrofit;
    }

    public void fillIntoList(List<String> productIdList, List<BookModel> myBooks, RecyclerView.Adapter adapter, Context context){
        if(productIdList.size()==0){
            progressBar.setVisibility(View.INVISIBLE);
        }
        API_Caller caller=retrofit.create(API_Caller.class);
        for(String id:productIdList){
            Call<Products> productsCall=caller.getProductFromID("products/"+id+"/?xerox=book");
            productsCall.enqueue(new Callback<Products>() {
                @Override
                public void onResponse(Call<Products> call, Response<Products> response) {
                    if(response.isSuccessful()){
                        Products currentProduct=response.body();
                        myBooks.add(new BookModel(currentProduct.getTitle(),currentProduct.getBookauthor(),currentProduct.getDescription(),"₹"+currentProduct.getAmount(),currentProduct.getImage().get(0),currentProduct.get_id()));
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }else{
                        System.out.println("ERROR: Cant load the products!");
                        Toast.makeText(context, "No Bookmarks Yet!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Products> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    System.out.println("ERROR: Cant load the products!");
                }
            });
        }
    }
}
