package com.example.libre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.libre.Adapters.HomeAdapter;
import com.example.libre.DaggerSetupFiles.MyApplication;
import com.example.libre.Fragments.AccountFragment;
import com.example.libre.Fragments.BookmarkedFragment;
import com.example.libre.Fragments.HomeFragment;
import com.example.libre.Fragments.ReadingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationBar ;
    private Fragment fragment;
    private FrameLayout container;
    private LinearLayout actionBarBG;
    FloatingActionButton buttonMain;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    private boolean clicked = false;
    @Inject
    public Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        container = findViewById(R.id.fragmentContainer);
        navigationBar = findViewById(R.id.bottomNavBar);
        buttonMain = findViewById(R.id.floatingActionButton_main);
        fab1 = findViewById(R.id.floatingActionButton_1);
        fab2 = findViewById(R.id.floatingActionButton_2);

        ((MyApplication) getApplication()).getApiComponent().injectMain(this);

        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMainClicked();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddBook.class);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Fab2", Toast.LENGTH_SHORT).show();
            }
        });




        navigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.explore :
                        if(!(fragment instanceof HomeFragment)){
                            fragment = new HomeFragment(retrofit);
                            item.setIcon(R.drawable.explore_icon_filled);

                            navigationBar.getMenu().getItem(1).setIcon(R.drawable.reading_icon_outline);
                            navigationBar.getMenu().getItem(2).setIcon(R.drawable.man_icon_outline);
                            navigationBar.getMenu().getItem(3).setIcon(R.drawable.bookmarked_icon_outlined);
                        }
                        break;
                    case R.id.reading :
                        if(!(fragment instanceof ReadingFragment)){
                            fragment = new ReadingFragment();
                            item.setIcon(R.drawable.reading_icon_filled);

                            navigationBar.getMenu().getItem(0).setIcon(R.drawable.explore_icon_outline);
                            navigationBar.getMenu().getItem(2).setIcon(R.drawable.man_icon_outline);
                            navigationBar.getMenu().getItem(3).setIcon(R.drawable.bookmarked_icon_outlined);
                        }

                        break;
                    case R.id.account :
                        if(!(fragment instanceof AccountFragment)){
                            fragment = new AccountFragment();
                            item.setIcon(R.drawable.man_icon);

                            navigationBar.getMenu().getItem(0).setIcon(R.drawable.explore_icon_outline);
                            navigationBar.getMenu().getItem(1).setIcon(R.drawable.reading_icon_outline);
                            navigationBar.getMenu().getItem(3).setIcon(R.drawable.bookmarked_icon_outlined);
                        }

                        break;
                    case R.id.bookmark :
                        if(!(fragment instanceof BookmarkedFragment)){
                            fragment = new BookmarkedFragment();
                            item.setIcon(R.drawable.bookmarker_icon_filled);

                            navigationBar.getMenu().getItem(0).setIcon(R.drawable.explore_icon_outline);
                            navigationBar.getMenu().getItem(1).setIcon(R.drawable.reading_icon_outline);
                            navigationBar.getMenu().getItem(2).setIcon(R.drawable.man_icon_outline);
                        }
                        break;


                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();

                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment(retrofit)).commit();
    }
    private void onMainClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    private void setAnimation(boolean clicked) {
        if(clicked){
            fab1.setVisibility(View.GONE);
            fab1.setEnabled(false);
            fab2.setVisibility(View.GONE);
            fab2.setEnabled(false);
        }else{
            fab1.setVisibility(View.VISIBLE);
            fab1.setEnabled(true);
            fab2.setVisibility(View.VISIBLE);
            fab2.setEnabled(true);
        }
    }

    private void setVisibility(boolean clicked) {

        Animation rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open);
        Animation rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close);
        Animation toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom);
        Animation fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom);

        if(!clicked){
            fab1.startAnimation(fromBottom);
            fab2.startAnimation(fromBottom);
            buttonMain.startAnimation(rotateOpen);
        }else{
            fab1.startAnimation(toBottom);
            fab2.startAnimation(toBottom);
            buttonMain.startAnimation(rotateClose);
        }
    }


}