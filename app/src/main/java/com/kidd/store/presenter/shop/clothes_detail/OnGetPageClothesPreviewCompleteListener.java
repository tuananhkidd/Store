package com.kidd.store.presenter.shop.clothes_detail;

import com.kidd.store.models.ClothesPreview;
import com.kidd.store.models.PageList;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface OnGetPageClothesPreviewCompleteListener {
    void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList);
    void onMessageEror(String msg);
}
