package com.kidd.store.presenter.shop.clothes_detail;

import android.content.Context;

import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.common.Utils;
import com.kidd.store.models.ClothesPreview;
import com.kidd.store.models.PageList;
import com.kidd.store.models.body.OrderBody;
import com.kidd.store.models.body.RateClothesBody;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.presenter.OnRequestCompleteListener;
import com.kidd.store.presenter.account.register.OnRegisterCompleteListener;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.clothes.ClothesService;
import com.kidd.store.services.retrofit.following.UnSaveClothesService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by KingIT on 4/23/2018.
 */

public class ClothesDetailInteractorImpl implements ClothesDetailInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ClothesDetailInteractorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewDestroy() {
        this.compositeDisposable.clear();
    }

    @Override
    public void getClothesDetail(String clothesID, OnGetClothesDetailCompleteListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .getClothesViewModel(customerID, clothesID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onGetClothesDetailComplete(response.body().getData());
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onMessageEror(response.message());
                                    break;
                                }
                                default: {
                                    listener.onMessageEror(response.message());
                                    break;
                                }

                            }
                        }, error -> {
                            listener.onMessageEror(context.getString(R.string.server_error));
                        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void getSimilarClothes(String clothesID, int pageIndex, int pageSize,
                                  OnGetPageClothesPreviewCompleteListener listener) {
        Observable<Response<ResponseBody<PageList<ClothesPreview>>>> observable =
                ApiClient.getClient().create(ClothesService.class)
                        .getSimilarClothesPreview(clothesID, pageIndex, pageSize, null, null);

        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(response -> {
                    switch (response.code()) {
                        case ResponseCode.OK: {
                            listener.onGetPageClothesPreviewsSuccess(response.body().getData());
                            break;
                        }
                        case ResponseCode.NOT_FOUND: {
                            listener.onMessageEror(response.message());
                            break;
                        }
                        default: {
                            listener.onMessageEror(response.message());
                            break;
                        }
                    }
                }, error -> {
                    listener.onMessageEror(context.getString(R.string.server_error));
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void rateClothes(String clothesID, RateClothesBody rateClothesBody, OnRequestCompleteListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);

        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .rateClothes(customerID, clothesID, rateClothesBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onServerError(response.message());
                                    break;
                                }
                                default: {
                                    listener.onServerError(response.message());
                                    break;
                                }
                            }
                        },
                        error -> {
                            listener.onServerError(error.getMessage());
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void getAllRateClothes(String clothesID, OnGetPageRateClothesSuccessListener listener) {
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .getAllRateClothes(clothesID, null, null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onGetRateClothesSuccess(response.body().getData());
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onError(response.message());
                                    break;
                                }
                                default: {
                                    listener.onError(response.message());
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
    public void saveClothes(String clothesID, OnRequestCompleteListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        Disposable disposable = ApiClient.getClient().create(ClothesService.class)
                .saveClothes(customerID, clothesID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onServerError("Customer not found");
                                    break;
                                }
                                default: {
                                    listener.onServerError(response.message());
                                    break;
                                }
                            }
                        },
                        error -> {
                            listener.onServerError(error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }

    @Override
    public void deleteSavedClothes(String clothesID, OnRequestCompleteListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        Disposable disposable = ApiClient.getClient().create(UnSaveClothesService.class)
                .UnSaveClothes(customerID, clothesID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onServerError("Customer not found");
                                    break;
                                }
                                default: {
                                    listener.onServerError(response.message());
                                    break;
                                }
                            }
                        },
                        error -> {
                            listener.onServerError(error.getMessage());
                        });

        compositeDisposable.add(disposable);
    }

    @Override
    public void orderClothes(String clothesID, OrderBody orderBody, OnRequestCompleteListener listener) {
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);
        Observable<Response<ResponseBody<String>>> observable = ApiClient.getClient().create(ClothesService.class)

                .orderClothes(customerID, clothesID, orderBody);

        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                case ResponseCode.OK: {
                                    listener.onSuccess();
                                    break;
                                }
                                case ResponseCode.NOT_FOUND: {
                                    listener.onServerError(response.message());
                                    break;
                                }
                                default: {
                                    listener.onServerError(response.message());
                                    break;
                                }
                            }
                        },
                        error -> {
                            listener.onServerError(error.getMessage());
                        }
                );

        compositeDisposable.add(disposable);

    }
}
