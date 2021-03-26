package com.example.libre.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Models.BookModel;
import com.example.libre.R;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkHolder> {

    private List<BookModel> booksMarkedList;
    private OnBookClicked bmListener;

    public BookmarkAdapter(List<BookModel> booksMarkedList, OnBookClicked bmListener) {
        this.booksMarkedList = booksMarkedList;
        this.bmListener = bmListener;
    }

    @NonNull
    @Override
    public BookmarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_items_layout,parent,false);
        BookmarkAdapter.BookmarkHolder holder = new BookmarkAdapter.BookmarkHolder(view,bmListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkHolder holder, int position) {
        holder.bookNameBM.setText(booksMarkedList.get(position).getBook());
        holder.authorBM.setText(booksMarkedList.get(position).getAuthor());
        holder.bookPriceBM.setText(booksMarkedList.get(position).getPrice());
        holder.descpBM.setText(booksMarkedList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return booksMarkedList.size();
    }

    static public class BookmarkHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bookNameBM;
        ImageView imageBM;
        TextView descpBM;
        TextView bookPriceBM;
        TextView authorBM;
        Button clearBookMark;
        OnBookClicked mListener;

        public BookmarkHolder(@NonNull View itemView, OnBookClicked mListener) {
            super(itemView);
            bookNameBM = itemView.findViewById(R.id.bookmarkRV_itemNameTV);
            imageBM = itemView.findViewById(R.id.bookmarkRV_itemIV);
            descpBM = itemView.findViewById(R.id.bookmarkRV_itemDescpTV);
            bookPriceBM = itemView.findViewById(R.id.bookmarkRV_itemPriceTV);
            authorBM = itemView.findViewById(R.id.bookmarkRV_itemAuthorTV);
            clearBookMark = itemView.findViewById(R.id.removeBookmarkButton);
            this.mListener = mListener;

            itemView.setOnClickListener(this);
            clearBookMark.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface OnBookClicked{
        void onBookClick(int position);
    }
}
