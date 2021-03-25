package com.example.libre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Models.CommentModel;
import com.example.libre.R;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.MessageFormat;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentHolder> {

    private static List<CommentModel> commentsList;
    Context context;
    Retrofit retrofit;
    String productID;
    public CommentsAdapter(List<CommentModel> commentsList,Context context,Retrofit retrofit,String productID) {
        this.commentsList = commentsList;
        this.context=context;
        this.retrofit=retrofit;
        this.productID=productID;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout,parent,false);
        CommentHolder holder = new CommentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        CommentModel currentComment=commentsList.get(position);
        holder.name.setText(commentsList.get(position).getUserName());
        holder.comment.setText(commentsList.get(position).getComment());

        if(!currentComment.getCommenterId().equals(currentComment.getCurrentUserId())){
            holder.deleteComment.setVisibility(View.GONE);
        }

        holder.deleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API_Caller caller=retrofit.create(API_Caller.class);
                Call<ResponseBody> call=caller.deleteComment("products/"+productID+"/comment/"+currentComment.getCommentID());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context,"Comment was deleted! Please refresh to see changes",Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context,"Failed to delete comment!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    static public class CommentHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView comment;
        ImageView deleteComment;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.comment_userNameTV);
            comment = itemView.findViewById(R.id.comment_userCommentTV);
            deleteComment = itemView.findViewById(R.id.comment_deleteIconMyComment);
        }
    }
}
