package com.kidd.store.services.retrofit.following;

import com.kidd.store.common.RequestConstants;
import com.kidd.store.models.PageList;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.models.response.SaveClothesPreview;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetSaveClothesService {
    @GET("/api/customers/{customerID}/save_clothes")
    Observable<Response<ResponseBody<PageList<SaveClothesPreview>>>> getSaveClothes(
            @Path("customerID") String customerID,
            @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
            @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize,
            @Query(RequestConstants.SORT_BY_QUERY) String sortBy,
            @Query(RequestConstants.SORT_TYPE_QUERY) String sortType);
}
