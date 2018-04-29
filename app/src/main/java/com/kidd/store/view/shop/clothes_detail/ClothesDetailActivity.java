package com.kidd.store.view.shop.clothes_detail;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kidd.store.R;
import com.kidd.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.kidd.store.adapter.RateClothesAdapter;
import com.kidd.store.adapter.RecyclerViewAdapter;
import com.kidd.store.adapter.SimilarClothesAdapter;
import com.kidd.store.common.Constants;
import com.kidd.store.common.UserAuth;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.Clothes;
import com.kidd.store.models.ClothesPreview;
import com.kidd.store.models.response.ClothesViewModel;
import com.kidd.store.presenter.shop.clothes_detail.ClothesDetailPresenter;
import com.kidd.store.presenter.shop.clothes_detail.ClothesDetailPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ClothesDetailActivity extends AppCompatActivity implements ClothesDetailActivityView,View.OnClickListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener, RecyclerViewAdapter.OnItemClickListener{

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.img_clothes)
    ImageView imgClothes;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_save)
    FloatingActionButton fabSave;
    @BindView(R.id.tv_name_product)
    TextView tvNameClothes;
    @BindView(R.id.tv_cost_product)
    TextView tvCostClothes;
    @BindView(R.id.rating_product)
    MaterialRatingBar ratingClothes;
    @BindView(R.id.tv_acount_rate)
    TextView tvAcountRate;
    @BindView(R.id.tv_detail_product)
    TextView tvDescriptionCLothes;
    @BindView(R.id.rc_customer_rate)
    RecyclerView rcCustomerRate;
    @BindView(R.id.rc_product_similar)
    RecyclerView rcClothesSimilar;
    @BindView(R.id.bt_add_cart)
    Button btAddCart;
    @BindView(R.id.bt_pay)
    Button btPay;
    @BindView(R.id.ln_retry)
    LinearLayout lnRetry;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.progress_loading_similar_clothes)
    ProgressBar progressLoadingSimilarClothes;
    private LoadingDialog loadingDialog;
    private ClothesDetailPresenter clothesDetailPresenter;
    private RateClothesAdapter rateClothesAdapter;

    private SimilarClothesAdapter similarClothesAdapter;
    String clothesID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_detail);
        initVariables();

    }
    @Override
    public void onStart() {
        super.onStart();
        nestedScrollView.scrollTo(-1,-1);
        nestedScrollView.smoothScrollTo(0,0);
        if (clothesID!=null) {
         //   similarClothesAdapter= new SimilarClothesAdapter(this);
          //  rcClothesSimilar.setAdapter(similarClothesAdapter);
            clothesDetailPresenter.firstFetchSimilarClothes(clothesID);
        }
    }

    private void initVariables() {
        ButterKnife.bind(this);

        loadingDialog= new LoadingDialog(this);
        clothesDetailPresenter= new ClothesDetailPresenterImpl(this, this);
        nestedScrollView.scrollTo(-1, -1);
        nestedScrollView.smoothScrollTo(0, 0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setTitle(R.string.clothes_detail);
        }
        fabSave.setOnClickListener(this);
        btAddCart.setOnClickListener(this);
        btPay.setOnClickListener(this);
        clothesID= getIntent().getStringExtra(Constants.KEY_CLOTHES_ID);
        if(clothesID!=null) {
            clothesDetailPresenter.fetchClothesDetail(Utils.getSharePreferenceValues(this, Constants.CUSTOMER_ID),clothesID);
        }
        rcClothesSimilar.setVisibility(View.VISIBLE);
        similarClothesAdapter = new SimilarClothesAdapter(this);
        similarClothesAdapter.setLoadingMoreListener(this);
        similarClothesAdapter.addOnItemClickListener(this);
        rcClothesSimilar.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rcClothesSimilar.setAdapter(similarClothesAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void showProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideProgress() {
        loadingDialog.hide();
    }

    @Override
    public void showClothesDetail(ClothesViewModel clothes) {

        Glide.with(this).load(clothes.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.book_logo)).into(imgClothes);
        tvNameClothes.setText(clothes.getName());
        tvCostClothes.setText(Utils.formatNumberMoney(clothes.getPrice())+" đ");
        tvDescriptionCLothes.setText(clothes.getDescription());
        tvAcountRate.setText("số lượt đánh giá ("+ clothes.getRateClothesViewModels().size()+")");
        rateClothesAdapter = new RateClothesAdapter(this);
        fabSave.setBackgroundResource(R.drawable.ic_nosave);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rcCustomerRate.setLayoutManager(linearLayoutManager);
        rcCustomerRate.setAdapter(rateClothesAdapter);
        rateClothesAdapter.addModels(clothes.getRateClothesViewModels(), false);
    }

    @Override
    public void showErrorLoading(String message) {
        lnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressSimilarClothes() {
        progressLoadingSimilarClothes.setVisibility(View.VISIBLE);
        rcClothesSimilar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressSimilarClothes() {
        progressLoadingSimilarClothes.setVisibility(View.GONE);
        rcClothesSimilar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorSimilarClothes() {
        lnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorSimilarClothes() {
        lnRetry.setVisibility(View.GONE);
    }

    @Override
    public void showSimilarLoadingMoreProgress() {
        similarClothesAdapter.showLoadingItem(true);
    }

    @Override
    public void hideSimilarLoadingMoreProgress() {
        similarClothesAdapter.hideLoadingItem();
    }

    @Override
    public void enableLoadingMore(boolean enable) {
        similarClothesAdapter.enableLoadingMore(enable);
    }

    @Override
    public void addSimilarClothes(List<ClothesPreview> similarClothes) {
        similarClothesAdapter.addModels(similarClothes, false);
    }

    @Override
    public void switchButtonSaveJobToSaved() {

    }

    @Override
    public void switchButtonSaveJobToUnSaved() {

    }

    @Override
    public void showListSimilarClothes() {
      rcClothesSimilar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListSimilarClothes() {
        rcClothesSimilar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_save:{
                break;
            }
        }
    }

    @Override
    public void onLoadMore() {
        clothesDetailPresenter.loadMoreSimilarClothes(clothesID);
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {

    }
}
