package com.kidd.store.services.retrofit.profile;

import com.kidd.store.models.response.Profile;
import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetProfileService {
    @GET("api/customers/profiles/{id}")
    Observable<Response<ResponseBody<Profile>>> getProfile(@Path("id") String customerID);
}
