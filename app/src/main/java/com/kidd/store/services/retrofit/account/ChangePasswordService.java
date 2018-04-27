package com.kidd.store.services.retrofit.account;

import com.kidd.store.models.body.NewPassword;
import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChangePasswordService {
    @POST("/api/auths/customer/{id}/newPassword")
    Observable<Response<ResponseBody<String>>> changePassword(@Path("id") String customerID, @Body NewPassword newPassword);
}
