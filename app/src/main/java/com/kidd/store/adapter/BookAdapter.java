package com.kidd.store.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidd.store.R;
import com.kidd.store.models.Book;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by TuanAnhKid on 3/31/2018.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    Context context;
    List<Book> lsBooks;

    public BookAdapter(Context context,List<Book> books) {
        this.context = context;
        this.lsBooks = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_book, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = lsBooks.get(position);
        holder.txt_title.setText(book.getTitle());
        holder.txt_author.setText(book.getAuthor());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txt_price.setText(decimalFormat.format(book.getPrice()));
    }

    @Override
    public int getItemCount() {
        return lsBooks.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder{

        ImageView img_logo;
        TextView txt_title;
        TextView txt_author;
        TextView txt_price;

        public BookViewHolder(View itemView) {
            super(itemView);
            img_logo = itemView.findViewById(R.id.img_logo);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_author = itemView.findViewById(R.id.txt_author);
            txt_price = itemView.findViewById(R.id.txt_price);
        }
    }
}
