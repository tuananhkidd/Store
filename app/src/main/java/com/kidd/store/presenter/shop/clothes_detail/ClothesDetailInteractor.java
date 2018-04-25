package com.kidd.store.presenter.shop.clothes_detail;

import com.kidd.store.presenter.BaseInteractor;
import com.kidd.store.presenter.OnRequestCompleteListener;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface ClothesDetailInteractor extends BaseInteractor{
    void getClothesDetail(String clothesID, OnGetClothesDetailCompleteListener listener);
    void saveJob(String jobID, OnRequestCompleteListener listener);
    void deleteSavedJob(String jobID, OnRequestCompleteListener listener);
    void getSimilarJobs(String jobID, int pageIndex, int pageSize, OnGetPageClothesPreviewCompleteListener listener);
}
