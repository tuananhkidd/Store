package com.kidd.store.services.retrofit.verify_email;

import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VerifyEmailService {
    @POST("api/auths/resend/registration/confirm/{username}")
    Observable<Response<ResponseBody<String>>> verifyEmail(@Path("username") String username);
}
