package com.kidd.store.services.retrofit.feedback;

import com.kidd.store.models.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FeedbackService {
    @POST("/api/customers/{customerID}/feedback")
    Observable<Response<ResponseBody<String>>> sendFeedback(@Path("customerID") String customerID,
                                                            @Body String feedback);
}
