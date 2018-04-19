package com.kidd.store.presenter.feedback;

import com.kidd.store.presenter.BaseInteractor;

public interface FeedbackInteractor extends BaseInteractor {
    void sendFeedback(String feedback,OnSendFeedbackSuccessListener listener);
}
