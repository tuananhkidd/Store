package com.kidd.store.view.shop.fragment.category;


import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kidd.store.R;
import com.kidd.store.SQLiteHelper.DBManager;
import com.kidd.store.adapter.CategoryAdapter;
import com.kidd.store.models.Category;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    ExpandableListView expandableListView;
    FloatingActionButton floatingActionButton;
    DBManager db;
    List<Category> categoryList;
    CategoryAdapter adapter;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category, container, false);
        initWidget(view);

        return view;
    }
    public void initWidget(View view){
        expandableListView = view.findViewById(R.id.exp_category);
        floatingActionButton = view.findViewById(R.id.flb_add);

        floatingActionButton.setOnClickListener(mOnClick);

        categoryList = db.getAllCategory();
        db = new DBManager(getActivity());
        adapter = new CategoryAdapter(categoryList);
        expandableListView.setAdapter(adapter);

    }
    View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };



}
