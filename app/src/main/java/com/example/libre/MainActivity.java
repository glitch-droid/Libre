package com.example.libre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.libre.Fragments.BookmarkedFragment;
import com.example.libre.Fragments.HomeFragment;
import com.example.libre.Fragments.ReadingFragment;
import com.example.libre.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationBar ;
    private Fragment fragment;
    private FrameLayout container;
    private LinearLayout actionBarBG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        container = findViewById(R.id.fragmentContainer);
        actionBarBG = findViewById(R.id.actionBar_bg);
        navigationBar = findViewById(R.id.bottomNavBar);
        navigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.explore :
                        fragment = new HomeFragment();
                        item.setIcon(R.drawable.explore_icon_filled);
                        actionBarBG.setBackgroundColor(getResources().getColor(R.color.red_primary));
                        navigationBar.getMenu().getItem(1).setIcon(R.drawable.reading_icon_outline);
                        navigationBar.getMenu().getItem(2).setIcon(R.drawable.bookmarked_icon_outlined);

                        break;
                    case R.id.reading :
                        fragment = new ReadingFragment();
                        item.setIcon(R.drawable.reading_icon_filled);
                        actionBarBG.setBackgroundColor(getResources().getColor(R.color.palette_2));
                        navigationBar.getMenu().getItem(0).setIcon(R.drawable.explore_icon_outline);
                        navigationBar.getMenu().getItem(2).setIcon(R.drawable.bookmarked_icon_outlined);


                        break;
                    case R.id.bookmark :
                        fragment = new BookmarkedFragment();
                        item.setIcon(R.drawable.bookmarker_icon_filled);
                        actionBarBG.setBackgroundColor(getResources().getColor(R.color.palette_4));
                        navigationBar.getMenu().getItem(0).setIcon(R.drawable.explore_icon_outline);
                        navigationBar.getMenu().getItem(1).setIcon(R.drawable.reading_icon_outline);

                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();

                return true;
            }
        });

    }


}