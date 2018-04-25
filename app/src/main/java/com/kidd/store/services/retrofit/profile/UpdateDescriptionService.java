package com.kidd.store.services.retrofit.profile;

import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by La Vo Tinh on 20/04/2018.
 */

public interface UpdateDescriptionService {
    @POST("api/customers/{customerID}/description")
    Observable<Response<ResponseBody<String>>> updateDescription(@Path("customerID") String customerID,@Body String description);
}
