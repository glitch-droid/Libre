package com.example.libre;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.libre.DaggerSetupFiles.MyApplication;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.Address;
import com.example.libre.Retrofit_Modules.Models.EditProductBody;
import com.example.libre.Retrofit_Modules.Models.EditProductFormat;
import com.example.libre.Retrofit_Modules.Models.FileUtils;
import com.example.libre.Retrofit_Modules.Models.MessageFormat;
import com.example.libre.Retrofit_Modules.Models.Products;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddBook extends AppCompatActivity {

    @Inject
    Retrofit retrofit;

    private ImageView selectedImage;
    private EditText bookNameET,authorNameET,descriptionET,priceET,phoneNumberET,countryET,stateET,areaET,cityET;
    private FloatingActionButton selectImageButton;
    private ImageView cutAddBook;
    private Button submitButton;
    private Uri imagePath;
    private String status;
    private String prodID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        submitButton=findViewById(R.id.addBook_done);
        cutAddBook = findViewById(R.id.close_addBook);
        bookNameET=findViewById(R.id.addBook_bookNameET);
        authorNameET=findViewById(R.id.addBook_bookAuthorET);
        descriptionET=findViewById(R.id.addBook_bookDescpET);
        priceET=findViewById(R.id.addBook_bookPriceET);
        selectedImage=findViewById(R.id.addBook_bookImageIV);
        phoneNumberET=findViewById(R.id.addBook_sellerPhoneET);
        countryET=findViewById(R.id.addBook_sellerCountryET);
        stateET=findViewById(R.id.addBook_sellerStateET);
        areaET=findViewById(R.id.addBook_sellerAreaET);
        cityET=findViewById(R.id.addBook_sellerCityET);
        selectImageButton=findViewById(R.id.addBook_fabAddImage);

        Intent intent=getIntent();
        status=intent.getStringExtra("status");
        String address_area=intent.getStringExtra("area");
        String address_city=intent.getStringExtra("city");
        String address_state=intent.getStringExtra("state");
        String address_country=intent.getStringExtra("country");
        String phoneNumber=intent.getStringExtra("phone");
        String amt=intent.getStringExtra("amount");
        String title=intent.getStringExtra("title");
        String bookAuthor=intent.getStringExtra("author");
        String description=intent.getStringExtra("desc");
        prodID=intent.getStringExtra("prodID");



        if(status!=null){
            if(status.equals("edit")){
                areaET.setText(address_area);
                cityET.setText(address_city);
                stateET.setText(address_state);
                countryET.setText(address_country);

                phoneNumberET.setText(phoneNumber);
                bookNameET.setText(title);
                priceET.setText(amt);
                authorNameET.setText(bookAuthor);
                descriptionET.setText(description);

                selectedImage.setVisibility(View.GONE);
                selectImageButton.setVisibility(View.GONE);
                selectImageButton.setEnabled(false);
            }
        }

        ((MyApplication)getApplication()).getApiComponent().injectInAddBook(this);

        cutAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void editBook(){
        submitButton.setEnabled(false);
        String city=cityET.getText().toString();
        String area=areaET.getText().toString();
        String state=stateET.getText().toString();
        String country=countryET.getText().toString();
        String phoneNo=phoneNumberET.getText().toString();
        String bookName=bookNameET.getText().toString();
        String authorName=authorNameET.getText().toString();
        String description=descriptionET.getText().toString();
        String price=priceET.getText().toString();

        if(!city.equals("")&&
                !area.equals("")&&
                !state.equals("")&&
                !country.equals("")&&
                !phoneNo.equals("")&&
                !bookName.equals("")&&
                !authorName.equals("")&&
                !description.equals("")&&
                !price.equals("")){
            EditProductBody products=new EditProductBody();
            Address address=new Address();
            address.setState(state);
            address.setArea(area);
            address.setCity(city);
            address.setCountry(country);
            products.setAddress(address);
            products.setAmount(price);
            products.setBookauthor(authorName);
            products.setPhoneNumber(phoneNo);
            products.setTitle(bookName);
            products.setDescription(description);
            EditProductFormat format=new EditProductFormat();
            format.setProduct(products);
            API_Caller caller=retrofit.create(API_Caller.class);
            Call<MessageFormat> call=caller.editProduct("products/"+prodID+"/?xerox=book",format);
            call.enqueue(new Callback<MessageFormat>() {
                @Override
                public void onResponse(Call<MessageFormat> call, Response<MessageFormat> response) {
                    if(response.isSuccessful()){
                        if(response.body().getMessage().equals("Success")){
                            Toast.makeText(getApplicationContext(),"Product Edited!",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Failed to edit product!",Toast.LENGTH_SHORT).show();
                            submitButton.setEnabled(true);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MessageFormat> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error occurred! Failed to edit product!",Toast.LENGTH_SHORT).show();
                    submitButton.setEnabled(true);
                }
            });
        }
    }

    public void saveChangesToBook(View view){
        if(status!=null){
            if(status.equals("edit")){
                editBook();
            }
        }else{
            submitButton.setEnabled(false);
            String city=cityET.getText().toString();
            String area=areaET.getText().toString();
            String state=stateET.getText().toString();
            String country=countryET.getText().toString();
            String phoneNo=phoneNumberET.getText().toString();
            String bookName=bookNameET.getText().toString();
            String authorName=authorNameET.getText().toString();
            String description=descriptionET.getText().toString();
            String price=priceET.getText().toString();
            try {
                File file=FileUtils.getFileFromUri(getApplicationContext(),imagePath);


                RequestBody imageRequest=RequestBody.create(MediaType.parse("image/jpeg"),file);
                MultipartBody.Part imageBody=MultipartBody.Part.createFormData("image",String.valueOf(System.currentTimeMillis())+".jpeg",imageRequest);

                if(!city.equals("")&&
                        !area.equals("")&&
                        !state.equals("")&&
                        !country.equals("")&&
                        !phoneNo.equals("")&&
                        !bookName.equals("")&&
                        !authorName.equals("")&&
                        !description.equals("")&&
                        !price.equals("")&&
                        imagePath!=null){
                    API_Caller caller=retrofit.create(API_Caller.class);
                    Call<MessageFormat> call=caller.createProduct("products/xeroxbook/",bookName,description,price,authorName,phoneNo,area,city,state,country,imageBody);
                    call.enqueue(new Callback<MessageFormat>() {
                        @Override
                        public void onResponse(Call<MessageFormat> call, Response<MessageFormat> response) {
                            if(response.isSuccessful()){
                                String message=response.body().getMessage();
                                if(message.equals("Success")){
                                    Toast.makeText(getApplicationContext(),"Product added successfully!",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Failed to create product!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            submitButton.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<MessageFormat> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Failed to add the book!",Toast.LENGTH_SHORT).show();
                            submitButton.setEnabled(true);
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Please enter all the details!",Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Unexpected error occurred!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void selectImage(View view){

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            // Verify permissions
            for (String str : permissions) {
                if (AddBook.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //request for access
                    AddBook.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, 1);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {
        super.onActivityResult(requestCode,
                resultCode,
                data);
        if (requestCode == 1
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            selectedImage.setVisibility(View.VISIBLE);
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                imagePath);
                selectedImage.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}