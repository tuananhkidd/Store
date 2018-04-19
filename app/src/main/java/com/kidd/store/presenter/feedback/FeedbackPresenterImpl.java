package com.kidd.store.presenter.feedback;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.view.feedback.FeedbackView;

public class FeedbackPresenterImpl implements FeedbackPresenter {
    Context context;
    FeedbackInteractor feedbackInteractor;
    FeedbackView feedbackView;

    public FeedbackPresenterImpl(Context context, FeedbackView feedbackView) {
        this.context = context;
        this.feedbackView = feedbackView;
        this.feedbackInteractor = new FeedbackInteractorImpl(context);
    }

    @Override
    public void validateFeedbackInput(String feedback) {
        if (feedback.isEmpty()) {
            feedbackView.showFeedbackInputError();
            return;
        }

        feedbackView.showLoadingDialog();
        feedbackInteractor.sendFeedback(feedback, new OnSendFeedbackSuccessListener() {
            @Override
            public void onSuccess() {
                feedbackView.hideLoadingDialog();
                Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                feedbackView.backToHome();
            }

            @Override
            public void onError(String message) {
                feedbackView.hideLoadingDialog();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onViewDestroy() {
        feedbackInteractor.onViewDestroy();
    }
}
