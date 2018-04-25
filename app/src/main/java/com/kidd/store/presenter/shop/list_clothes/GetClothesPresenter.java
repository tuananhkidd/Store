package com.kidd.store.presenter.shop.list_clothes;

import com.kidd.store.presenter.BasePresenter;

/**
 * Created by KingIT on 4/22/2018.
 */

public interface GetClothesPresenter extends BasePresenter{
    void loadMoreClothesPreviews();
    void refreshClothesPreviews();
}
