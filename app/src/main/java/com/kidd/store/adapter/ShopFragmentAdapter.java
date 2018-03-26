package com.kidd.store.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kidd.store.R;
import com.kidd.store.view.shop.book.BookFragment;
import com.kidd.store.view.shop.clothes.ClotheFragment;
import com.kidd.store.view.shop.electric.MobileFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuanAnhKid on 3/26/2018.
 */

public class ShopFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> lsFragment;
    Context context;

    public ShopFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        lsFragment = new ArrayList<>();
        lsFragment.add(new MobileFragment());
        lsFragment.add(new BookFragment());
        lsFragment.add(new ClotheFragment());
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return lsFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return context.getString(R.string.mobile);
            }
            case 1: {
                return context.getString(R.string.book);
            }
            case 2: {
                return context.getString(R.string.clothes);
            }
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return lsFragment.size();
    }
}
