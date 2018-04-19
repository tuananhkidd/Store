package com.kidd.store.presenter.book;

import com.kidd.store.presenter.BaseInteractor;

/**
 * Created by TuanAnhKid on 4/4/2018.
 */

public interface BookInteractor extends BaseInteractor {
    void loadAllBooks(OnGetAllBookCompleteListener listener);
}
