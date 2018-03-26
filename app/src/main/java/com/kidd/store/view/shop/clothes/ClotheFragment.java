package com.kidd.store.view.shop.clothes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidd.store.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClotheFragment extends Fragment {


    public ClotheFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clothe, container, false);
    }

}
