package com.kidd.store.presenter.rate;

import com.kidd.store.models.body.RateBody;
import com.kidd.store.presenter.BasePresenter;

public interface RatePresenter extends BasePresenter {
    void validateCmt(String cmt, RateBody rateBody);
}
