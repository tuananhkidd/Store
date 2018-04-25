package com.kidd.store.presenter.shop.clothes_detail;

import com.kidd.store.models.Clothes;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface OnGetClothesDetailCompleteListener {
    void onGetClothesDetailComplete(Clothes clothes);
    void onMessageEror(String msg);
}
