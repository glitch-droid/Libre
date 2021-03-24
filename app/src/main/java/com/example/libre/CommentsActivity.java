package com.example.libre;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Adapters.CommentsAdapter;
import com.example.libre.Models.CommentModel;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {
    private List<CommentModel> commentModelList = new ArrayList<>();
    private RecyclerView commentsRV;
    private CommentsAdapter commentsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_activity_layout);

        commentsRV = findViewById(R.id.comments_rv);

        commentModelList.add(new CommentModel("Mriga Bhai","This was a hilarious book to read....never stopped laughing. A must read!!"));
        commentModelList.add(new CommentModel("Mriga Bhai","This was a hilarious book to read....never stopped laughing. A must read!!..hilarious book to read....never stopped laughing...hilarious book to read....never stopped laughing..hilarious book to read....never stopped laughing"));
        commentModelList.add(new CommentModel("Mriga Bhai","This was a hilarious book to read....never stopped laughing. A must read!!..hilarious book to read....never stopped laughing..."));

        commentsAdapter = new CommentsAdapter(commentModelList);
        commentsRV.setAdapter(commentsAdapter);
        commentsRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
