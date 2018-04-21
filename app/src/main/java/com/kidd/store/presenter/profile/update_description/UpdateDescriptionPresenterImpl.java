package com.kidd.store.presenter.profile.update_description;

import android.content.Context;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.services.event_bus.DescriptionChangeEvent;
import com.kidd.store.view.profile.update_description.UpdateDescriptionView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by La Vo Tinh on 20/04/2018.
 */

public class UpdateDescriptionPresenterImpl implements UpdateDescriptionPresenter {
    private Context context;
    private UpdateDescriptionView updateDescriptionView;
    private UpdateDescriptionInterator updateDescriptionInterator;

    public UpdateDescriptionPresenterImpl(Context context, UpdateDescriptionView updateDescriptionView) {
        this.context = context;
        this.updateDescriptionView = updateDescriptionView;
        this.updateDescriptionInterator = new UpdateDescriptionInteratorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        updateDescriptionInterator.onViewDestroy();
    }

    @Override
    public void validateDescription(String des) {
        if (des.isEmpty()) {
            updateDescriptionView.showDescriptionError();
            return;
        }

        updateDescriptionView.showLoadingDialog();
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        updateDescriptionInterator.updateDescription(customerID, des, new OnUpdateDescriptionSuccessListener() {
            @Override
            public void onSuccess(String des) {
                updateDescriptionView.hideLoadingDialog();

                updateDescriptionView.backToProfileScreen();

                EventBus.getDefault().post(new DescriptionChangeEvent(des));
                Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                updateDescriptionView.hideLoadingDialog();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
