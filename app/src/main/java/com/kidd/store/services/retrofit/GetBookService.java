package com.kidd.store.services.retrofit;

import com.kidd.store.models.Book;
import com.kidd.store.models.response.ResponseBody;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by TuanAnhKid on 4/4/2018.
 */

public interface GetBookService {
    @GET("api/products/books")
    Observable<Response<ResponseBody<List<Book>>>> getAllBook();
}
