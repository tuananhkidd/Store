package com.kidd.store.presenter.shop.clothes_detail;

import com.kidd.store.presenter.BasePresenter;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailPresenter extends BasePresenter{
    void fetchClothesDetail(String clothesID);
    void saveJob(String clothesID);
    void deleteSavedJob(String clothesID);
    void firstFetchSimilarJobs(String clothesID);
    void loadMoreSimilarJobs(String clothesID);
}
