package com.kidd.store.services.retrofit.register;

import com.kidd.store.common.RequestConstants;
import com.kidd.store.models.body.CustomerRegisterBody;
import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("/api/auths/customers/register")
    Observable<Response<ResponseBody<String>>> CustomerRegister(@Header(RequestConstants.AUTHORIZATION) String base64Account,
                                                                @Body CustomerRegisterBody body);
}
