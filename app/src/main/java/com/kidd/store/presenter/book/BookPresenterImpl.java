package com.kidd.store.presenter.book;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.models.Book;
import com.kidd.store.presenter.BasePresenter;
import com.kidd.store.view.shop.book.BookFragmentView;

import java.util.List;

/**
 * Created by TuanAnhKid on 4/4/2018.
 */

public class BookPresenterImpl implements BookPresenter {
    private Context context;
    private BookFragmentView bookFragmentView;
    private BookInteractor bookInteractor;

    public BookPresenterImpl(Context context, BookFragmentView bookFragmentView) {
        this.context = context;
        this.bookFragmentView = bookFragmentView;
        this.bookInteractor = new BookInteratorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        bookInteractor.onViewDestroy();
    }

    @Override
    public void loadAllBook() {
        bookInteractor.loadAllBooks(new OnGetAllBookCompleteListener() {
            @Override
            public void onGetBookSuccess(List<Book> books) {
                bookFragmentView.loadAllBooks(books);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
