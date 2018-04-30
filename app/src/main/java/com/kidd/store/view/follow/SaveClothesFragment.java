package com.kidd.store.view.follow;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kidd.store.R;
import com.kidd.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.kidd.store.adapter.RecyclerViewAdapter;
import com.kidd.store.adapter.SaveClothesAdapter;
import com.kidd.store.common.Constants;
import com.kidd.store.common.UserAuth;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.response.SaveClothesPreview;
import com.kidd.store.presenter.follow.following.SaveClothesPresenter;
import com.kidd.store.presenter.follow.following.SaveClothesPresenterImpl;
import com.kidd.store.presenter.follow.unfollowing.UnSaveClothesPresenter;
import com.kidd.store.presenter.follow.unfollowing.UnSaveClothesPresenterImpl;
import com.kidd.store.services.event_bus.OnSaveClothesEvent;
import com.kidd.store.services.event_bus.UserAuthorizationChangedEvent;
import com.kidd.store.view.shop.clothes_detail.ClothesDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaveClothesFragment extends Fragment implements SaveClothesView,
        RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        SaveClothesAdapter.OnButtonSaveClick {

    @BindView(R.id.rc_save_clothes)
    RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rl_no_result)
    RelativeLayout rl_no_result;
    SaveClothesAdapter adapter;
    SaveClothesPresenter presenter;
    LoadingDialog loadingDialog;
    UnSaveClothesPresenter unSaveClothesPresenter;
    @BindView(R.id.txt_result)
    TextView txt_result;


    public SaveClothesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        ButterKnife.bind(this, view);
        initData();
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedUserAuthChangedEvent(UserAuthorizationChangedEvent userAuthorizationChangedEvent) {
        if (UserAuth.isUserLoggedIn(getActivity())) {
            hideNoResult();
            txt_result.setText(getString(R.string.no_save));
            presenter.refreshSaveClothes();
        } else {
            showNoResult();
            txt_result.setText(getString(R.string.function_request_login));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedSaveClothesEvent(OnSaveClothesEvent saveClothesEvent) {
       presenter.refreshSaveClothes();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    void initData() {
        Context context = getActivity();
        loadingDialog = new LoadingDialog(context);
        adapter = new SaveClothesAdapter(context, false, this);
        adapter.addOnItemClickListener(this);
        adapter.setLoadingMoreListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.addItemDecoration(new DividerItemDecoration(context, linearLayoutManager.getOrientation()));
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryLight, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

        unSaveClothesPresenter = new UnSaveClothesPresenterImpl(context, this);
        presenter = new SaveClothesPresenterImpl(context, this);
        if (UserAuth.isUserLoggedIn(context)) {
            hideNoResult();
            presenter.refreshSaveClothes();
            txt_result.setText(getString(R.string.no_save));
        } else {
            showNoResult();
            txt_result.setText(getString(R.string.function_request_login));
        }
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
        presenter.refreshSaveClothes();
    }

    @Override
    public void onLoadMore() {
        presenter.loadMoreSaveClothes();
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        Context context = getActivity();
        Intent intent = new Intent(context, ClothesDetailActivity.class);
        SaveClothesPreview saveClothesPreview = this.adapter.getItem(position, SaveClothesPreview.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, saveClothesPreview.getId());
        context.startActivity(intent);
    }


    @Override
    public void onClick(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Bỏ theo dõi");
        builder.setMessage("Bạn có chắc chắn muốn bỏ theo dõi sản phẩm này ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SaveClothesPreview saveClothesPreview = adapter.getItem(pos, SaveClothesPreview.class);
                unSaveClothesPresenter.UnSaveClothes(saveClothesPreview.getId());
            }
        });
        builder.setNegativeButton("Không", null);
        builder.show();
    }
}
