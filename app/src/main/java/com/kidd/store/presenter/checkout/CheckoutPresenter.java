package com.kidd.store.presenter.checkout;

import com.kidd.store.models.body.SetOrderBody;
import com.kidd.store.presenter.BasePresenter;

import java.util.Set;

public interface CheckoutPresenter extends BasePresenter {
    void checkout(Set<SetOrderBody> orderBodies);
}
