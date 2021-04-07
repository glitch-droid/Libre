package com.example.libre;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.libre.Adapters.CommentsAdapter;
import com.example.libre.DaggerSetupFiles.MyApplication;
import com.example.libre.Models.CommentModel;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.FetchedComment;
import com.example.libre.Retrofit_Modules.Models.MessageFormat;
import com.example.libre.Retrofit_Modules.Models.PostCommentFormat;
import com.example.libre.Retrofit_Modules.Models.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentsActivity extends AppCompatActivity {
    private List<CommentModel> commentModelList = new ArrayList<>();
    private RecyclerView commentsRV;
    private static CommentsAdapter commentsAdapter;
    private EditText commentET;
    private static String currentUid;
    private String productId;
    private SwipeRefreshLayout refreshLayout;
    private ImageView close;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_activity_layout);


        Intent intent=getIntent();
        currentUid=intent.getStringExtra("uid");
        productId=intent.getStringExtra("prodId");


        ((MyApplication)getApplication()).getApiComponent().injectInCommentsActivity(this);

        close=findViewById(R.id.close_comments);
        commentsRV = findViewById(R.id.comments_rv);
        commentsRV.setItemAnimator(new SlideInUpAnimator());
        commentET=findViewById(R.id.comment_commentET);
        commentsAdapter = new CommentsAdapter(commentModelList,getApplicationContext(),retrofit,productId);
        commentsRV.setAdapter(commentsAdapter);
        commentsRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commentsRV.setHasFixedSize(true);

        refreshLayout=findViewById(R.id.comment_swipe_to_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillCommentsRV(productId);
            }
        });

        MaterialRippleLayout closeComment = findViewById(R.id.close_comment_ripple);
        closeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.cover_fade_in,R.anim.slide_down);
            }
        });
        fillCommentsRV(productId);
    }

    private void fillCommentsRV(String prodId){
        commentModelList.clear();
        API_Caller caller=retrofit.create(API_Caller.class);
        Call<Products> call=caller.getProductFromID("products/"+prodId+"/?xerox=book");
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if(response.isSuccessful()){
                    Products currentProduct=response.body();
                    List<FetchedComment> fetchedComments=currentProduct.getComments();
                    for(FetchedComment comment: fetchedComments){
                        commentModelList.add(new CommentModel(comment.getAuthor().getUsername(),comment.getComment(),comment.getAuthor().getId(),currentUid,comment.get_id()));
                    }
                    commentsAdapter.notifyDataSetChanged();
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

    public void submitComment(View view){
        String commentBody=commentET.getText().toString();
        if(!commentBody.equals("")){
            API_Caller caller=retrofit.create(API_Caller.class);
            PostCommentFormat commentFormat=new PostCommentFormat(commentBody);
            Call<MessageFormat> call=caller.postComment("products/"+productId+"/comment/?xerox=book",commentFormat);
            call.enqueue(new Callback<MessageFormat>() {
                @Override
                public void onResponse(Call<MessageFormat> call, Response<MessageFormat> response) {
                    if(response.isSuccessful()){
                        if(response.body().getMessage().equals("Success")){
                            Toast.makeText(getApplicationContext(),"Comment added!",Toast.LENGTH_SHORT).show();
                            commentET.setText("");
                            fillCommentsRV(productId);
                        }else{
                            Toast.makeText(getApplicationContext(),"Comment couldn't be added!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<MessageFormat> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Commend Failed to add!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void clearTextView(View view){
        commentET.setText("");
    }
}
