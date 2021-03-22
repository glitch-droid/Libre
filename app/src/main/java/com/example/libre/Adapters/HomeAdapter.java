package com.example.libre.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Models.BookModel;
import com.example.libre.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    List<BookModel> booksList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookName.setText(booksList.get(position).getBook());
        holder.authorName.setText(booksList.get(position).getAuthor());
        holder.bookDescp.setText(booksList.get(position).getDescription());
        holder.bookPrice.setText(booksList.get(position).getPrice());
        String base="http://35.193.15.204:3000/";
        if(booksList.get(position).getUrl().length()!=0){
            Picasso.get().load(base+booksList.get(position).getUrl())
                    .fit()
                    .centerInside()
                    .into(holder.productImage);
        }
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public void setBooksList(List<BookModel> booksList) {
        this.booksList = booksList;
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        TextView authorName;
        TextView bookDescp;
        TextView bookPrice;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.homeRV_itemNameTV);
            authorName = itemView.findViewById(R.id.homeRV_itemAuthorTV);
            bookDescp = itemView.findViewById(R.id.homeRV_itemDescpTV);
            bookPrice = itemView.findViewById(R.id.homeRV_itemPriceTV);
            productImage=itemView.findViewById(R.id.homeRV_itemIV);
        }
    }
}
