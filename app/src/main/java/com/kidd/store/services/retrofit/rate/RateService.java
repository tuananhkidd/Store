package com.kidd.store.services.retrofit.rate;

import com.kidd.store.models.body.RateBody;
import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RateService {
    @PUT("/api/customers/{id}/rate")
    Observable<Response<ResponseBody<String>>> rateShop(@Path("id") String customerID, @Body RateBody rateBody);
}
