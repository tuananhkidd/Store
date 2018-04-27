package com.kidd.store.presenter.follow.unfollowing;

import com.kidd.store.presenter.BaseInteractor;

public interface UnSaveClothesInterator extends BaseInteractor {
    void UnSaveClothes(String clothesID,OnUnSaveSuccessListener listener);
}
