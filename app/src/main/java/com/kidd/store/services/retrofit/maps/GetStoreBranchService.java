package com.kidd.store.services.retrofit.maps;

import com.kidd.store.models.body.LatLngBody;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.models.response.StoreBranchViewModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetStoreBranchService {
    @POST("/api/admins/store_branch_distance")
    Observable<Response<ResponseBody<List<StoreBranchViewModel>>>> getAllBranchStore(@Body LatLngBody latLngBody);
}
