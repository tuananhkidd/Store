package com.kidd.store.services.retrofit.splash;

import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface SplashService {
    @GET("/api/admins/version")
    Observable<Response<ResponseBody<Integer>>> getAppVersion();
}
