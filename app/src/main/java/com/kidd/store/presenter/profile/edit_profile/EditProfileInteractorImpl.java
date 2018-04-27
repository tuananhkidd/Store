package com.kidd.store.presenter.profile.edit_profile;

import android.content.Context;

import com.kidd.store.common.Constants;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.common.Utils;
import com.kidd.store.models.body.ProfileBody;
import com.kidd.store.models.response.Profile;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.profile.EditProfileService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class EditProfileInteractorImpl implements EditProfileInterator {
    Context context;
    CompositeDisposable compositeDisposable;

    public EditProfileInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void updateProfile(ProfileBody profileBody, OnEditSuccessListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        Observable<Response<ResponseBody<Profile>>> observable = ApiClient.getClient().create(EditProfileService.class)
                .updateProfile(customerID, profileBody);
        Disposable disposable = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onError(response.message());
                                    break;
                                }
                                default:{
                                    listener.onError(response.message()+"deafault");
                                    break;
                                }
                            }
                        },
                        error -> {
                            listener.onError(error.getMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        compositeDisposable.clear();
    }
}
