package com.example.libre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libre.Constants.Constants;
import com.example.libre.DaggerSetupFiles.MyApplication;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.CurrentUser;
import com.example.libre.Retrofit_Modules.Models.Products;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookDetail extends AppCompatActivity {
    private BottomSheetDialog bottomSheetDialog;
    private Button getItButton;
    private ImageView backButton,productImage;
    private TextView bookName,authorName,description,bookPrice,sellerName,sellerEmail,sellerPhoneNo,sellerAddress;
    private String sellerUID;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        getItButton = findViewById(R.id.bookDetail_getItButton);

        ((MyApplication)getApplication()).getApiComponent().injectInBookDetails(this);

        productImage=findViewById(R.id.product_Image_IV);
        bookName=findViewById(R.id.bookDetail_bookName2TV);
        authorName=findViewById(R.id.bookDetail_bookAuthorTV);
        description=findViewById(R.id.bookDetail_bookDescriptionTV);


        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        getAllDetails(id);
        getItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModalSheet();
            }
        });

        backButton = findViewById(R.id.close_bookDetails);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getAllDetails(String id){
        API_Caller caller=retrofit.create(API_Caller.class);
        Call<Products> call=caller.getProductFromID("products/"+id+"/?xerox=book");
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if(response.isSuccessful()){
                    Products currentProduct=response.body();
                    sellerUID=currentProduct.getAuthor().getId();
                    bookName.setText(currentProduct.getTitle());
                    authorName.setText("A book by: "+currentProduct.getBookauthor());
                    description.setText(currentProduct.getDescription());
                    String baseUrl="http://35.193.15.204:3000/";
                    if(currentProduct.getImage().size()!=0){
                        Picasso.get().load(baseUrl+currentProduct.getImage().get(0))
                                .fit()
                                .centerInside()
                                .into(productImage);
                    }
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed to get details!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showModalSheet() {
        Intent intent=getIntent();
        String id=intent.getStringExtra("id");

        bottomSheetDialog = new BottomSheetDialog(BookDetail.this,R.style.BottomSheetTheme);

        View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_bookdetails,
                (ViewGroup) findViewById(R.id.bottom_sheet));
        bookPrice=sheetView.findViewById(R.id.book_priceTV);
        sellerName=sheetView.findViewById(R.id.bookDetail_sellerNameTV);
        sellerEmail=sheetView.findViewById(R.id.bookDetail_sellerEmailTV);
        sellerPhoneNo=sheetView.findViewById(R.id.bookDetail_sellerContactNoTV);
        sellerAddress=sheetView.findViewById(R.id.product_seller_address);

        API_Caller caller=retrofit.create(API_Caller.class);
        Call<Products> call=caller.getProductFromID("products/"+id+"/?xerox=book");
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if(response.isSuccessful()){
                    Products currentProd=response.body();
                    sellerAddress.setText("Country: "+currentProd.getAddress().getCountry()+
                            "\tState: "+currentProd.getAddress().getState()+
                            "\tCity: "+currentProd.getAddress().getCity()+
                            "\tArea: "+currentProd.getAddress().getArea());
                    bookPrice.setText("₹"+currentProd.getAmount()+"/Week");
                    sellerEmail.setText("Email: "+currentProd.getAuthor().getUsername());
                    sellerPhoneNo.setText("Contact No.: "+currentProd.getPhoneNumber());
                    Call<CurrentUser> seller=caller.getUserFromID("profiles/"+currentProd.getAuthor().getId()+"/?xerox=book");
                    seller.enqueue(new Callback<CurrentUser>() {
                        @Override
                        public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response1) {
                            if(response1.isSuccessful()){
                                CurrentUser user=response1.body();
                                sellerName.setText("Seller Name: "+user.getName());
                            }
                        }

                        @Override
                        public void onFailure(Call<CurrentUser> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Failed to get details!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed to get details!",Toast.LENGTH_SHORT).show();
            }
        });
        sheetView.findViewById(R.id.bookDetail_contactSeller).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookDetail.this, "Seller Contact", Toast.LENGTH_SHORT).show();
            }
        });
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }
}