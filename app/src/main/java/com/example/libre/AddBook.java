package com.example.libre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.libre.DaggerSetupFiles.MyApplication;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class AddBook extends AppCompatActivity {

    @Inject
    Retrofit retrofit;
    private ImageView selectedImage;
    private EditText bookNameET,authorNameET,descriptionET,priceET,phoneNumberET,countryET,stateET,areaET,cityET;
    private ImageView cutAddBook;

    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
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

    }

    private String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }

    public void selectImage(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 1);
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