package com.example.libre.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Models.CommentModel;
import com.example.libre.R;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentHolder> {

    List<CommentModel> commentsList;

    public CommentsAdapter(List<CommentModel> commentsList) {
        this.commentsList = commentsList;
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

        holder.name.setText(commentsList.get(position).getUserName());
        holder.comment.setText(commentsList.get(position).getComment());

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
