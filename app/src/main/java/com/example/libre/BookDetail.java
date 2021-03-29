package com.example.libre;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
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
import com.example.libre.Retrofit_Modules.Models.BookmarkMessage;
import com.example.libre.Retrofit_Modules.Models.CurrentUser;
import com.example.libre.Retrofit_Modules.Models.MessageFormat;
import com.example.libre.Retrofit_Modules.Models.Products;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookDetail extends AppCompatActivity {
    private BottomSheetDialog bottomSheetDialog;
    private Button getItButton;
    private ImageView backButton,productImage;
    private TextView bookName,authorName,description,bookPrice,sellerName,sellerEmail,sellerPhoneNo,sellerAddress;
    private FloatingActionButton toComments,editFAB,deleteFab,bookmarkFAB;
    private String currentUid,pNo;
    private AlertDialog.Builder builder;
    private String area,city,state,country,title,bookauthor,bookdescription,bookphoneno,bookamount;
    private SwipeRefreshLayout refreshLayout;

    @Inject
    public Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        getItButton = findViewById(R.id.bookDetail_getItButton);

        ((MyApplication)getApplication()).getApiComponent().injectInBookDetails(this);

        refreshLayout=findViewById(R.id.bookmark_swipeToRefresh);
        productImage=findViewById(R.id.product_Image_IV);
        bookName=findViewById(R.id.bookDetail_bookName2TV);
        authorName=findViewById(R.id.bookDetail_bookAuthorTV);
        description=findViewById(R.id.bookDetail_bookDescriptionTV);
        toComments = findViewById(R.id.bookDetail_commentsFAB);
        editFAB = findViewById(R.id.bookDetail_editFAB);
        deleteFab = findViewById(R.id.bookDetail_deleteFAB);
        bookmarkFAB=findViewById(R.id.bookDetail_bookmarkFAB);
        builder = new AlertDialog.Builder(this);

        Intent intent=getIntent();
        String prodId=intent.getStringExtra("id");
        currentUid=intent.getStringExtra("uid");
        String status=intent.getStringExtra("status");

        if(status.equals("read")){
            editFAB.setVisibility(View.INVISIBLE);
            deleteFab.setVisibility(View.INVISIBLE);
            editFAB.setEnabled(false);
            deleteFab.setEnabled(false);
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllDetails(prodId);
            }
        });

        bookmarkFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarkCurrentBook(currentUid,prodId);
            }
        });
        getAllDetails(prodId);
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

        toComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),CommentsActivity.class);
                intent1.putExtra("prodId",prodId);
                System.out.println("TEST: "+currentUid);
                intent1.putExtra("uid",currentUid);
                startActivity(intent1);
            }
        });
        editFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent=new Intent(getApplicationContext(),AddBook.class);
                editIntent.putExtra("status","edit");
                editIntent.putExtra("area",area);
                editIntent.putExtra("city",city);
                editIntent.putExtra("country",country);
                editIntent.putExtra("state",state);
                editIntent.putExtra("phone",bookphoneno);
                editIntent.putExtra("amount",bookamount);
                editIntent.putExtra("title",title);
                editIntent.putExtra("author",bookauthor);
                editIntent.putExtra("desc",bookdescription);
                editIntent.putExtra("prodID",prodId);
                startActivity(editIntent);
            }
        });


        deleteFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       builder.setTitle("Alert!");
                        builder.setMessage("Do you want to delete this item ?")
                                .setCancelable(true)
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Action for Yes Button
                                        API_Caller caller=retrofit.create(API_Caller.class);
                                        Call<ResponseBody> call=caller.deleteProduct("products/"+prodId+"/?xerox=book");
                                        call.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if(response.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(),"Product Deleted!",Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }else{
                                                    Toast.makeText(getApplicationContext(),"Failed to delete!",Toast.LENGTH_SHORT).show();
                                                }
                                                dialog.cancel();
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(),"An error occurred! Failed to delete!",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                }
                }
        );
    }

    private void bookmarkCurrentBook(String userID,String prodID){
        API_Caller caller=retrofit.create(API_Caller.class);
        BookmarkMessage message=new BookmarkMessage();
        message.setBookmark("N");
        Call<MessageFormat> call=caller.addBookmark("bookmark/"+prodID+"/"+userID+"/?xerox=book",message);
        call.enqueue(new Callback<MessageFormat>() {
            @Override
            public void onResponse(Call<MessageFormat> call, Response<MessageFormat> response) {
                if(response.isSuccessful()){
                    if(response.body().getMessage().equals("Added to Bookmarks")){
                        Toast.makeText(getApplicationContext(),"Added to bookmarks!",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Failed to add to bookmarks!",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageFormat> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed to add to bookmarks!",Toast.LENGTH_SHORT).show();
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
                    area=currentProduct.getAddress().getArea();
                    city=currentProduct.getAddress().getCity();
                    state=currentProduct.getAddress().getState();
                    country=currentProduct.getAddress().getCountry();
                    bookphoneno=currentProduct.getPhoneNumber();
                    bookamount=currentProduct.getAmount();
                    bookauthor=currentProduct.getBookauthor();
                    title=currentProduct.getTitle();
                    bookdescription=currentProduct.getDescription();

                    bookName.setText(currentProduct.getTitle());
                    authorName.setText("A book by: "+currentProduct.getBookauthor());
                    description.setText(currentProduct.getDescription());
                    String baseUrl=Constants.BASE_URL;
                    if(currentProduct.getImage().size()!=0){
                        Picasso.get().load(baseUrl+currentProduct.getImage().get(0))
                                .fit()
                                .centerInside()
                                .into(productImage);
                    }
                    refreshLayout.setRefreshing(false);
                }else {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed to get details!",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
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
                            "\t State: "+currentProd.getAddress().getState()+
                            "\t City: "+currentProd.getAddress().getCity()+
                            "\t Area: "+currentProd.getAddress().getArea());
                    bookPrice.setText("₹"+currentProd.getAmount()+"/Week");
                    sellerEmail.setText("Email: "+currentProd.getAuthor().getUsername());
                    sellerPhoneNo.setText("Contact No.: "+currentProd.getPhoneNumber());
                    pNo=currentProd.getPhoneNumber();
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
                Intent intent1=new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:"+pNo));
                startActivity(intent1);
            }
        });
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }
}
