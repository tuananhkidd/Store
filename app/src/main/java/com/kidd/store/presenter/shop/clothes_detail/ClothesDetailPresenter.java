package com.kidd.store.presenter.shop.clothes_detail;

import com.kidd.store.presenter.BasePresenter;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailPresenter extends BasePresenter{
    void fetchClothesDetail(String customerID, String clothesID);
    void saveClothes(String clothesID);
    void deleteSavedClothes(String clothesID);
    void firstFetchSimilarClothes(String clothesID);
    void loadMoreSimilarClothes(String clothesID);
}
