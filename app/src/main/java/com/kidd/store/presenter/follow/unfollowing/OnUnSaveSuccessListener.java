package com.kidd.store.presenter.follow.unfollowing;

import com.kidd.store.models.PageList;
import com.kidd.store.models.response.SaveClothesPreview;

public interface OnUnSaveSuccessListener {
    void onSuccess(PageList<SaveClothesPreview> saveClothesPreviewPageList);
    void onError(String msg);
}
