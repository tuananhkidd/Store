package com.kidd.store.services.retrofit.following;

import com.kidd.store.models.PageList;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.models.response.SaveClothesPreview;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface UnSaveClothesService {
    @DELETE("/api/customers/{customerID}/save_clothes/{id}")
    Observable<Response<ResponseBody<PageList<SaveClothesPreview>>>> UnSaveClothes(@Path("customerID") String customerID,
                                                                                   @Path("id") String clothesID);
}
