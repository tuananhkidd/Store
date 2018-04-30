package com.kidd.store.view.history_order;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.kidd.store.R;
import com.kidd.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.kidd.store.adapter.OrderClothesAdapter;
import com.kidd.store.adapter.RecyclerViewAdapter;
import com.kidd.store.adapter.SaveClothesAdapter;
import com.kidd.store.common.Constants;
import com.kidd.store.common.UserAuth;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.response.OrderViewModel;
import com.kidd.store.models.response.SaveClothesPreview;
import com.kidd.store.presenter.history.HistoryOrderPresenter;
import com.kidd.store.presenter.history.HistoryOrderPresenterImpl;
import com.kidd.store.view.follow.SaveClothesView;
import com.kidd.store.view.shop.clothes_detail.ClothesDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryOrderActivity extends AppCompatActivity implements HistoryOrderView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener{

    @BindView(R.id.rc_order)
    RecyclerView rc_order;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rl_no_result)
    RelativeLayout rl_no_result;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    LoadingDialog loadingDialog;
    OrderClothesAdapter adapter;
    HistoryOrderPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void initData() {
        presenter = new HistoryOrderPresenterImpl(this,this);
        loadingDialog = new LoadingDialog(this);
        adapter = new OrderClothesAdapter(this);
        adapter.addOnItemClickListener(this);
        adapter.setLoadingMoreListener(this);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v->{
            finish();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rc_order.setLayoutManager(linearLayoutManager);
        rc_order.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        rc_order.setHasFixedSize(true);
        rc_order.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

        presenter.onRefreshHistoryOrder();

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
    public void loadMoreOrderViewModel(List<OrderViewModel> orderViewModels) {
        adapter.addModels(orderViewModels, false);
    }

    @Override
    public void refreshOrderViewModel(List<OrderViewModel> orderViewModels) {
        adapter.refresh(orderViewModels);
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
        presenter.onRefreshHistoryOrder();
    }

    @Override
    public void onLoadMore() {
        presenter.onLoadmoreHistoryOrder();
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        Intent intent = new Intent(this, ClothesDetailActivity.class);
        OrderViewModel saveClothesPreview = this.adapter.getItem(position, OrderViewModel.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, saveClothesPreview.getId());
        startActivity(intent);
    }
}
