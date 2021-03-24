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
import com.example.libre.Retrofit_Modules.Models.FileUtils;
import com.example.libre.Retrofit_Modules.Models.MessageFormat;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddBook extends AppCompatActivity {

    @Inject
    Retrofit retrofit;
    private ImageView selectedImage;
    private EditText bookNameET,authorNameET,descriptionET,priceET,phoneNumberET,countryET,stateET,areaET,cityET;
    private ImageView cutAddBook;
    private Button submitButton;
    private Uri imagePath;

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

        ((MyApplication)getApplication()).getApiComponent().injectInAddBook(this);

        cutAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void saveChangesToBook(View view){
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
            Toast.makeText(getApplicationContext(),"Unexpected error occurred!",Toast.LENGTH_SHORT).show(); }
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