package com.kidd.store.services.retrofit.account;

import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ForgetPasswordService {
    @POST("/api/auths/customer/{id}/reset_password")
    Observable<Response<ResponseBody<String>>> forgetPassword(@Path("id") String customerID, @Body String email);
}
