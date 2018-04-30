package com.kidd.store.services;

import com.kidd.store.models.Cart;
import com.kidd.store.models.Item;

import java.util.ArrayList;

/**
 * Created by KingIT on 4/29/2018.
 */

public class ManageCart {
    private static Cart cart;
    public static Cart getCart(){
        if(cart==null){
            cart= new Cart(new ArrayList<Item>(), 0);
        }
        return cart;
    }

}
