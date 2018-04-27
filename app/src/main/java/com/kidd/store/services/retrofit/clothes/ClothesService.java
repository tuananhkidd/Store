package com.kidd.store.services.retrofit.clothes;

import com.kidd.store.common.RequestConstants;
import com.kidd.store.models.Clothes;
import com.kidd.store.models.ClothesPreview;
import com.kidd.store.models.PageList;
import com.kidd.store.models.response.ClothesViewModel;
import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface ClothesService {
    @GET("/api/products/clothes")
    Observable<Response<ResponseBody<PageList<ClothesPreview>>>> getClothesPreview(@Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize);
    @POST("/api/products/clothes/{id}")
    Observable<Response<ResponseBody<ClothesViewModel>>> getClothesViewModel(@Path("id") String clothesID);

    @GET("/api/products/similarClothes/{id}")
    Observable<Response<ResponseBody<PageList<ClothesPreview>>>> getSimilarClothesPreview(@Path("id") String clothesID,
                                                                                           @Query(RequestConstants.PAGE_INDEX_QUERY) int pageIndex,
                                                                                   @Query(RequestConstants.PAGE_SIZE_QUERY) int pageSize);

}
