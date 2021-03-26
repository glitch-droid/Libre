package com.example.libre.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Constants.Constants;
import com.example.libre.Models.BookModel;
import com.example.libre.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyUploadsAdapter extends RecyclerView.Adapter<MyUploadsAdapter.MyBookHolder> {


    List<BookModel> myBooksList;
    private MyBookListener myBookListener;

    public MyUploadsAdapter(List<BookModel> myBooksList, MyBookListener myBookListener) {
        this.myBooksList = myBooksList;
        this.myBookListener = myBookListener;
    }

    @NonNull
    @Override
    public MyBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybooks_layout,parent,false);
        MyBookHolder myBookHolder = new MyBookHolder(view,myBookListener);
        return myBookHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookHolder holder, int position) {
        holder.bookName.setText(myBooksList.get(position).getBook());
        holder.bookPrice.setText(myBooksList.get(position).getPrice());
        String base= Constants.BASE_URL;
        if(myBooksList.get(position).getUrl().length()!=0){
            Picasso.get().load(base+myBooksList.get(position).getUrl())
                    .fit()
                    .centerInside()
                    .into(holder.bookImage);
        }
    }


    @Override
    public int getItemCount() {
        return myBooksList.size();
    }

    public static class MyBookHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView bookImage;
        TextView bookName;
        TextView bookPrice;
        MyBookListener bookListener;
        public MyBookHolder(@NonNull View itemView, MyBookListener bookListener ) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.myBook_image);
            bookPrice = itemView.findViewById(R.id.myBook_price);
            bookName = itemView.findViewById(R.id.myBook_name);
            this.bookListener = bookListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            bookListener.onMyBookClick(getAdapterPosition());
        }
    }
    public interface MyBookListener{
        void onMyBookClick(int position);
    }
}
