package com.kidd.store.presenter.follow.following;

import com.kidd.store.presenter.BaseInteractor;

public interface SaveClothesInteractor extends BaseInteractor{
    void getSaveClothes(int pageIndex,int pageSize,OnGetSaveClothesSuccessListener listener);
}
