package com.kidd.store.presenter.rate;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.models.body.RateBody;
import com.kidd.store.view.rate.RateView;

public class RatePresenterImpl implements RatePresenter {
    private Context context;
    private RateView rateView;
    private RateInteractor rateInteractor;
    public RatePresenterImpl(Context context, RateView rateView) {
        this.context = context;
        this.rateView = rateView;
        this.rateInteractor = new RateInteractorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        rateInteractor.onViewDestroy();
    }

    @Override
    public void validateCmt(String cmt, RateBody rateBody) {
        if(cmt.isEmpty()){
            rateView.showCommentError();
            return;
        }

        rateView.showLoadingDialog();

        rateInteractor.rateShop(rateBody, new OnRateSuccessListener() {
            @Override
            public void onRateSuccess() {
                rateView.hideLoadingDialog();
                rateView.backToHomeScreen();
                Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                rateView.hideLoadingDialog();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
