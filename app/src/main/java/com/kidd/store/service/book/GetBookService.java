package com.kidd.store.service.book;

import com.kidd.store.models.Book;

import java.util.List;

/**
 * Created by TuanAnhKid on 4/2/2018.
 */

public interface GetBookService {
    void onError();
    void onSuccess(List<Book> projects);
}
