package com.kidd.store.presenter.checkout;

import com.kidd.store.models.body.SetOrderBody;
import com.kidd.store.presenter.BaseInteractor;
import com.kidd.store.presenter.OnRequestCompleteListener;

import java.util.Set;

public interface CheckoutInteractor extends BaseInteractor{
    void checkout(Set<SetOrderBody> orderBodies, OnRequestCompleteListener listener);
}
