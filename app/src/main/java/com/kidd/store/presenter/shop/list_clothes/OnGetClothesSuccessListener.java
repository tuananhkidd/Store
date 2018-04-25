package com.kidd.store.presenter.shop.list_clothes;

import com.kidd.store.models.ClothesPreview;
import com.kidd.store.models.PageList;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface OnGetClothesSuccessListener {
    void onGetPageClothesPreviewsSuccess(PageList<ClothesPreview> clothesPreviewPageList);
    void onMessageEror(String msg);
}
