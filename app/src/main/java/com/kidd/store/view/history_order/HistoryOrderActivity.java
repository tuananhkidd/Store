package com.kidd.store.view.history_order;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kidd.store.R;
import com.kidd.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.kidd.store.adapter.RecyclerViewAdapter;
import com.kidd.store.adapter.SaveClothesAdapter;
import com.kidd.store.common.Constants;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.response.SaveClothesPreview;
import com.kidd.store.view.follow.SaveClothesView;
import com.kidd.store.view.shop.clothes_detail.ClothesDetailActivity;

import java.util.List;

import butterknife.BindView;

public class HistoryOrderActivity extends AppCompatActivity implements SaveClothesView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener{

    @BindView(R.id.rc_order)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rl_no_result)
    RelativeLayout rl_no_result;
    LoadingDialog loadingDialog;
    SaveClothesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
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
    public void loadMoreSaveClothes(List<SaveClothesPreview> clothesPreviewPageList) {
        adapter.addModels(clothesPreviewPageList, false);
    }

    @Override
    public void refreshSaveClothes(List<SaveClothesPreview> saveClothesViews) {
        adapter.refresh(saveClothesViews);
    }

    @Override
    public void showNoResult() {
        swipeRefreshLayout.setVisibility(View.GONE);
        rl_no_result.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoResult() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        rl_no_result.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.hide();
    }


    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        Intent intent = new Intent(this, ClothesDetailActivity.class);
        SaveClothesPreview saveClothesPreview = this.adapter.getItem(position, SaveClothesPreview.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, saveClothesPreview.getId());
        startActivity(intent);
    }
}
