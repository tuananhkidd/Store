package com.kidd.store.presenter.book;

import com.kidd.store.models.Book;

import java.util.List;

/**
 * Created by TuanAnhKid on 4/4/2018.
 */

public interface OnGetAllBookCompleteListener {
    void onGetBookSuccess(List<Book> books);
    void onError(String message);
}
