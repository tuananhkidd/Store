package com.kidd.store.services.retrofit.profile;

import com.kidd.store.models.body.ProfileBody;
import com.kidd.store.models.response.Profile;
import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EditProfileService {
    @PUT("api/customers/profiles/{id}")
    Observable<Response<ResponseBody<Profile>>> updateProfile(@Path("id") String customerID, @Body ProfileBody profileBody);
}
