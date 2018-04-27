package com.kidd.store.presenter.follow.following;

import com.kidd.store.models.PageList;
import com.kidd.store.models.response.SaveClothesPreview;

import java.util.List;

public interface OnGetSaveClothesSuccessListener {
    void onSuccess(PageList<SaveClothesPreview> saveClothesPreviews);
    void onError(String msg);
}
