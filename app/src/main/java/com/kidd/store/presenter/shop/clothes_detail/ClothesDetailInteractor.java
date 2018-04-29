package com.kidd.store.presenter.shop.clothes_detail;

import com.kidd.store.models.body.OrderBody;
import com.kidd.store.models.body.RateClothesBody;
import com.kidd.store.presenter.BaseInteractor;
import com.kidd.store.presenter.OnRequestCompleteListener;
import com.kidd.store.presenter.account.register.OnRegisterCompleteListener;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailInteractor extends BaseInteractor{
    void getClothesDetail(String clothesID, OnGetClothesDetailCompleteListener listener);
    void saveClothes(String clothesID, OnRequestCompleteListener listener);
    void deleteSavedClothes(String clothesID, OnRequestCompleteListener listener);
    void getSimilarClothes(String clothesID, int pageIndex, int pageSize, OnGetPageClothesPreviewCompleteListener listener);
    void rateClothes(String clothesID, RateClothesBody rateClothesBody, OnRequestCompleteListener listener);
    void getAllRateClothes(String clothesID,OnGetPageRateClothesSuccessListener listener);
    void orderClothes( String clothesID, OrderBody orderBody,OnRequestCompleteListener listener);
}
