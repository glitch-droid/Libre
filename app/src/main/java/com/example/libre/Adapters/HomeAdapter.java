package com.example.libre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Constants.Constants;
import com.example.libre.Models.BookModel;
import com.example.libre.R;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.AllProducts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    List<BookModel> booksList = new ArrayList<>();

    private OnBookListenerHome listenerHome;

    public HomeAdapter(OnBookListenerHome listenerHome) {
        this.listenerHome = listenerHome;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product_layout,parent,false);
        ViewHolder holder = new ViewHolder(view,listenerHome);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookName.setText(booksList.get(position).getBook());
        holder.authorName.setText(booksList.get(position).getAuthor());
        holder.bookDescp.setText(booksList.get(position).getDescription());
        holder.bookPrice.setText("₹"+booksList.get(position).getPrice()+"/Week");
        String base= Constants.BASE_URL;
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


    static public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bookName;
        TextView authorName;
        TextView bookDescp;
        TextView bookPrice;
        ImageView productImage;
        OnBookListenerHome onBookListenerHome;

        public ViewHolder(@NonNull View itemView,OnBookListenerHome onBookListenerHome) {
            super(itemView);
            bookName = itemView.findViewById(R.id.homeRV_itemNameTV);
            authorName = itemView.findViewById(R.id.homeRV_itemAuthorTV);
            bookDescp = itemView.findViewById(R.id.homeRV_itemDescpTV);
            bookPrice = itemView.findViewById(R.id.homeRV_itemPriceTV);
            productImage=itemView.findViewById(R.id.homeRV_itemIV);
            this.onBookListenerHome = onBookListenerHome;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onBookListenerHome.onBookClick(getAdapterPosition());
        }
    }
    public interface OnBookListenerHome{
        void onBookClick(int position);
    }

}
