package com.kidd.store.presenter.shop.clothes_detail;

import com.kidd.store.models.Clothes;
import com.kidd.store.models.response.ClothesViewModel;

/**
 * Created by KingIT on 4/23/2018.
 */

public interface OnGetClothesDetailCompleteListener {
    void onGetClothesDetailComplete(ClothesViewModel clothesViewModel);
    void onMessageEror(String msg);
}
