package com.kidd.store.services.retrofit.account;

import com.kidd.store.common.RequestConstants;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginServices {
    @POST("api/auths/customer/login")
    Observable<Response<ResponseBody<HeaderProfile>>> login(@Header(RequestConstants.AUTHORIZATION) String base64Account);
}
