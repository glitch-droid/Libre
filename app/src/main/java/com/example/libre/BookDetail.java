package com.example.libre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BookDetail extends AppCompatActivity {
    private BottomSheetDialog bottomSheetDialog;
    private Button getItButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        getItButton = findViewById(R.id.bookDetail_getItButton);
        
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

    private void showModalSheet() {
        bottomSheetDialog = new BottomSheetDialog(BookDetail.this,R.style.BottomSheetTheme);

        View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_bookdetails,
                (ViewGroup) findViewById(R.id.bottom_sheet));

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