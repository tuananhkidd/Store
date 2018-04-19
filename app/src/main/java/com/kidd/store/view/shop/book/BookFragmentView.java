package com.kidd.store.view.shop.book;

import com.kidd.store.models.Book;

import java.util.List;

/**
 * Created by TuanAnhKid on 4/4/2018.
 */

public interface BookFragmentView {
    void loadAllBooks(List<Book> list);
}
