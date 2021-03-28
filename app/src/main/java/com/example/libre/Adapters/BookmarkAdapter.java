package com.example.libre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libre.Constants.Constants;
import com.example.libre.Models.BookModel;
import com.example.libre.R;
import com.example.libre.Retrofit_Modules.API_Caller;
import com.example.libre.Retrofit_Modules.Models.BookmarkMessage;
import com.example.libre.Retrofit_Modules.Models.MessageFormat;
import com.example.libre.SharedPrefsManager.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkHolder> {

    private List<BookModel> booksMarkedList;
    private static OnBookClicked bmListener;
    private Retrofit retrofit;
    private Context context;
    public BookmarkAdapter(List<BookModel> booksMarkedList, OnBookClicked bmListener, Context context,Retrofit retrofit) {
        this.booksMarkedList = booksMarkedList;
        this.bmListener = bmListener;
        this.context=context;
        this.retrofit=retrofit;
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
        BookModel currentBook=booksMarkedList.get(position);
        String base= Constants.BASE_URL;
        if(currentBook.getUrl().length()!=0) {
            Picasso.get().load(base + currentBook.getUrl())
                    .fit()
                    .centerInside()
                    .into(holder.imageBM);
        }
        holder.clearBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API_Caller caller=retrofit.create(API_Caller.class);
                SharedPrefManager manager=new SharedPrefManager(context);
                BookmarkMessage bookmarkMessage=new BookmarkMessage();
                bookmarkMessage.setBookmark("Y");
                Call<MessageFormat> deleteCall=caller.deleteBookmark("bookmark/"+currentBook.getId()+"/"+manager.getValue(Constants.CURRENT_USER)+"/?xerox=book",bookmarkMessage);
                deleteCall.enqueue(new Callback<MessageFormat>() {
                    @Override
                    public void onResponse(Call<MessageFormat> call, Response<MessageFormat> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context,"Bookmark deleted! Please refresh to see changes",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"Failed to delete!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageFormat> call, Throwable t) {
                        Toast.makeText(context,"Failed to delete bookmarked item!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
            bmListener.onBookClick(getAdapterPosition());
        }
    }
    public interface OnBookClicked{
        void onBookClick(int position);
    }
}
