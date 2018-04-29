package com.kidd.store.services.retrofit.history;

import com.kidd.store.common.RequestConstants;
import com.kidd.store.models.PageList;
import com.kidd.store.models.response.OrderViewModel;
import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HistoryOrderService {
    @GET("/api/customers/{customerID}/orders")
    Observable<Response<ResponseBody<PageList<OrderViewModel>>>> getAllOrder(@Path("customerID") String customerID,
                                                                             @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                             @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
                                                                             @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
                                                                             @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);
}
