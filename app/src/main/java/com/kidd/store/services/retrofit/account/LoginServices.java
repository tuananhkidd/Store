package com.kidd.store.services.retrofit.account;

import com.kidd.store.common.RequestConstants;
import com.kidd.store.models.Book;
import com.kidd.store.models.body.FacebookLoginBody;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LoginServices {
    @POST("api/auths/customer/login")
    Observable<Response<ResponseBody<HeaderProfile>>> login(@Header(RequestConstants.AUTHORIZATION) String base64Account,
                                                            @Body String fcmToken);


    @POST("api/auths/facebook/login/{facebookUserID}")
    Observable<Response<ResponseBody<Object>>> facebookLogin(@Path("facebookUserID") String facebookUserID,
                                                             @Body String fcmToken);

    @PUT("api/auths/facebook/register/{fcmToken}")
    Observable<Response<ResponseBody<HeaderProfile>>> facebookRegister(@Body FacebookLoginBody facebookLoginBody,
                                                                       @Path("fcmToken") String fcmToken);
}
