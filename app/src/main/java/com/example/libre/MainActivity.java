package com.example.libre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.libre.Adapters.HomeAdapter;
import com.example.libre.DaggerSetupFiles.MyApplication;
import com.example.libre.Fragments.AccountFragment;
import com.example.libre.Fragments.BookmarkedFragment;
import com.example.libre.Fragments.HomeFragment;
import com.example.libre.Fragments.ReadingFragment;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.SharedPrefsManager.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationBar ;
    private Fragment fragment;
    private FrameLayout container;
    private LinearLayout actionBarBG;
    FloatingActionButton buttonMain;
    private ImageView logout;
    private boolean clicked = false;
    private AlertDialog.Builder builder;
    @Inject
    public Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        container = findViewById(R.id.fragmentContainer);
        navigationBar = findViewById(R.id.bottomNavBar);
        buttonMain = findViewById(R.id.floatingActionButton_main);
        logout = findViewById(R.id.logoutButton);
        builder = new AlertDialog.Builder(this);

        ((MyApplication) getApplication()).getApiComponent().injectMain(this);

        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddBook.class);
                startActivity(intent);
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

                            navigationBar.getMenu().getItem(1).setIcon(R.drawable.man_icon_outline);
                            navigationBar.getMenu().getItem(2).setIcon(R.drawable.bookmarked_icon_outlined);
                            buttonMain.show();
                        }
                        break;

                    case R.id.account :
                        if(!(fragment instanceof AccountFragment)){
                            fragment = new AccountFragment(retrofit);
                            item.setIcon(R.drawable.man_icon);

                            navigationBar.getMenu().getItem(0).setIcon(R.drawable.explore_icon_outline);
                            navigationBar.getMenu().getItem(2).setIcon(R.drawable.bookmarked_icon_outlined);
                            buttonMain.hide();
                        }

                        break;
                    case R.id.bookmark :
                        if(!(fragment instanceof BookmarkedFragment)){
                            fragment = new BookmarkedFragment(retrofit);
                            item.setIcon(R.drawable.bookmarker_icon_filled);

                            navigationBar.getMenu().getItem(0).setIcon(R.drawable.explore_icon_outline);
                            navigationBar.getMenu().getItem(1).setIcon(R.drawable.man_icon_outline);
                            buttonMain.hide();
                        }
                        break;


                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();

                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment(retrofit)).commit();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Hey!");
                builder.setMessage("Do you want to Logout ?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                logoutUser();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void logoutUser(){
        Toast.makeText(getApplicationContext(),"Please wait... Logging out!", Toast.LENGTH_SHORT).show();

        API_Caller caller=retrofit.create(API_Caller.class);
        Call<ResponseBody> call=caller.logoutUSer("logout");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    SharedPrefManager manager=new SharedPrefManager(getApplicationContext());
                    manager.clearAll();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed to logout!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}