package com.kidd.store.view.shop;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kidd.store.R;
import com.kidd.store.adapter.ClothesPreviewAdapter;
import com.kidd.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.kidd.store.adapter.RecyclerViewAdapter;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.models.ClothesPreview;
import com.kidd.store.models.PageList;
import com.kidd.store.presenter.shop.list_clothes.GetClothesPresenter;
import com.kidd.store.presenter.shop.list_clothes.GetClothesPresenterImpl;
import com.kidd.store.view.shop.clothes_detail.ClothesDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements ShopFragmentView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {
    @BindView(R.id.rc_posts)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    SearchView searchView;
    ClothesPreviewAdapter adapter;
    GetClothesPresenter presenter;
    List<ClothesPreview> clothesPreviews;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new GetClothesPresenterImpl(getContext(), this);
        setHasOptionsMenu(true);
        initData();
    }

    private void initData() {
        Context context = getActivity();
        adapter = new ClothesPreviewAdapter(context);
        adapter.addOnItemClickListener(this);
        adapter.setLoadingMoreListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.addItemDecoration(new DividerItemDecoration(context, linearLayoutManager.getOrientation()));
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

        presenter.refreshClothesPreviews();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    adapter.clear();
                    adapter.addModels(clothesPreviews,false);
                }else{
                    enableRefreshing(false);
                    loadUiSearch(newText);
                }
                return false;
            }
        });
        searchView.setQueryHint(getString(R.string.search));
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadUiSearch(String text) {
        List<ClothesPreview> ls = new ArrayList<>();
        for (int i = 0; i < clothesPreviews.size(); i++) {
            String aux_name = Utils.deAccent(clothesPreviews.get(i).getName());
            String aux_text = Utils.deAccent(text);
            boolean found = aux_name.replaceAll(" ", "").toUpperCase().contains(aux_text.replaceAll(" ", "").toUpperCase());
            if (found) {
                ls.add(clothesPreviews.get(i));
            }
        }
        adapter.clear();
        adapter.addModels(ls,false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadMoreProgress() {
        adapter.showLoadingItem(true);
    }

    @Override
    public void hideLoadMoreProgress() {
        adapter.hideLoadingItem();
    }

    @Override
    public void enableLoadMore(boolean enable) {
        adapter.enableLoadingMore(enable);
    }

    @Override
    public void enableRefreshing(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public void showRefreshingProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshingProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addClothesPreviews(PageList<ClothesPreview> clothesPreviewPageList) {
        adapter.addModels(clothesPreviewPageList.getResults(), false);
    }

    @Override
    public void refreshClothesPreview(PageList<ClothesPreview> clothesPreviewPageList) {
        adapter.refresh(clothesPreviewPageList.getResults());
        this.clothesPreviews = clothesPreviewPageList.getResults();
    }

    @Override
    public void onRefresh() {
        presenter.refreshClothesPreviews();
    }

    @Override
    public void onLoadMore() {
        presenter.loadMoreClothesPreviews();
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        Context context = getActivity();
        ClothesPreview clothesPreview = this.adapter.getItem(position, ClothesPreview.class);
        Intent intent = new Intent(context, ClothesDetailActivity.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, clothesPreview.getId());
        context.startActivity(intent);
    }
}
