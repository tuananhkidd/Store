package com.kidd.store.services.retrofit.checkout;

import com.kidd.store.models.body.SetOrderBody;
import com.kidd.store.models.response.ResponseBody;

import java.util.Set;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CheckoutService {
    @PUT("/api/customers/{customerID}/orders")
    Observable<Response<ResponseBody<String>>> checkout(@Path("customerID") String customerID,
                                                        @Body Set<SetOrderBody> orderBodies);
}
