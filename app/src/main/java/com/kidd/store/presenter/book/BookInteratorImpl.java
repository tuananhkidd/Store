package com.kidd.store.presenter.book;

import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.models.Book;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.presenter.BaseInteractor;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.GetBookService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by TuanAnhKid on 4/4/2018.
 */

public class BookInteratorImpl implements BookInteractor {
    Context context;
    CompositeDisposable compositeDisposable;

    public BookInteratorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
        context = null;
    }


    @Override
    public void loadAllBooks(OnGetAllBookCompleteListener listener) {
        Observable<Response<ResponseBody<List<Book>>>> observable = ApiClient.getClient()
                .create(GetBookService.class).getAllBook();

        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    listener.onGetBookSuccess(response.body().getData());
                }, error -> {
                    listener.onError(context.getString(R.string.server_error));
                });
        compositeDisposable.add(disposable);

    }
}
