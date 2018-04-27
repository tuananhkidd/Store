package com.kidd.store.presenter.shop.clothes_detail;

import com.kidd.store.presenter.BaseInteractor;
import com.kidd.store.presenter.OnRequestCompleteListener;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailInteractor extends BaseInteractor{
    void getClothesDetail(String clothesID, OnGetClothesDetailCompleteListener listener);
    void saveClothes(String clothesID, OnRequestCompleteListener listener);
    void deleteSavedClothes(String clothesID, OnRequestCompleteListener listener);
    void getSimilarClothes(String clothesID, int pageIndex, int pageSize, OnGetPageClothesPreviewCompleteListener listener);
}
