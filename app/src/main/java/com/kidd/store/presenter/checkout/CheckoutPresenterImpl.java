package com.kidd.store.presenter.checkout;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.models.body.SetOrderBody;
import com.kidd.store.presenter.OnRequestCompleteListener;
import com.kidd.store.services.ManageCart;
import com.kidd.store.view.cart.CartActivityView;

import java.util.Set;

public class CheckoutPresenterImpl implements CheckoutPresenter {
    private Context context;
    private CartActivityView cartActivityView;
    private CheckoutInteractor checkoutInteractor;

    public CheckoutPresenterImpl(Context context, CartActivityView cartActivityView) {
        this.context = context;
        this.cartActivityView = cartActivityView;
        this.checkoutInteractor = new CheckoutInteractorImpl(context);
    }

    @Override
    public void checkout(Set<SetOrderBody> orderBodies) {
        cartActivityView.showProgress();
        checkoutInteractor.checkout(orderBodies, new OnRequestCompleteListener() {
            @Override
            public void onSuccess() {
                cartActivityView.hideProgress();
                cartActivityView.backToHomeScreen();
                ManageCart.getCart().getItems().clear();
                Toast.makeText(context, context.getString(R.string.message), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onServerError(String message) {
                cartActivityView.hideProgress();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewDestroy() {
        checkoutInteractor.onViewDestroy();
    }
}
