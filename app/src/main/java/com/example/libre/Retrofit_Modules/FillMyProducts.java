package com.example.libre.Retrofit_Modules;

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
    public FillMyProducts(Retrofit retrofit){
        this.retrofit=retrofit;
    }

    public void fillIntoList(List<String> productIdList, List<BookModel> myBooks, MyUploadsAdapter adapter){
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
                    }else{
                        System.out.println("ERROR: Cant load the products!");
                    }
                }

                @Override
                public void onFailure(Call<Products> call, Throwable t) {
                    System.out.println("ERROR: Cant load the products!");
                }
            });
        }
    }
}
