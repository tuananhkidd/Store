package com.kidd.store.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kidd.store.R;
import com.kidd.store.view.follow.SaveClothesFragment;
import com.kidd.store.view.shop.ShopFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuanAnhKid on 3/26/2018.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> lsFragment;
    Context context;

    public FragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        lsFragment = new ArrayList<>();
        lsFragment.add(new ShopFragment());
        lsFragment.add(new SaveClothesFragment());
//        lsFragment.add(new CartFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return lsFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return context.getString(R.string.Shopping);
            }
            case 1: {
                return context.getString(R.string.Following);
            }
//            case 2: {
//                return context.getString(R.string.Cart);
//            }
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return lsFragment.size();
    }

    public static int getItemID(int position) {
        switch (position) {
            case 0: {
                return R.id.item_shopping;
            }
            case 1: {
                return R.id.item_following;
            }
//            case 2: {
//                return R.id.item_card;
//            }
            default:{
                return -1;
            }
        }
    }
}
