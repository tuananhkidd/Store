package com.kidd.store.presenter.rate;

import com.kidd.store.models.body.RateBody;
import com.kidd.store.presenter.BaseInteractor;

public interface RateInteractor  extends BaseInteractor{
    void rateShop(RateBody rateBody,OnRateSuccessListener listener);
}
