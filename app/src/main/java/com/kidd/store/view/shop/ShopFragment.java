package com.kidd.store.view.shop;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidd.store.R;
import com.kidd.store.adapter.ShopFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    ShopFragmentAdapter adapter;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        initView(view);
        setupViewPager();
        return view;
    }

    public void initView(View rootView){
        viewPager = rootView.findViewById(R.id.view_pager_product);
        tabLayout = rootView.findViewById(R.id.tab_layout);
    }

    public void setupViewPager(){
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        adapter = new ShopFragmentAdapter(getChildFragmentManager(),getActivity());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
