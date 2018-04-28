package com.kidd.store.view.shop.clothes_detail;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kidd.store.R;
import com.kidd.store.common.Config;
import com.kidd.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.kidd.store.adapter.RateClothesAdapter;
import com.kidd.store.adapter.RecyclerViewAdapter;
import com.kidd.store.adapter.SimilarClothesAdapter;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.custom.RatingDialog;
import com.kidd.store.models.Clothes;
import com.kidd.store.models.ClothesPreview;
import com.kidd.store.models.body.RateClothesBody;
import com.kidd.store.models.response.ClothesViewModel;
import com.kidd.store.models.response.RateClothesViewModel;
import com.kidd.store.presenter.shop.clothes_detail.ClothesDetailPresenter;
import com.kidd.store.presenter.shop.clothes_detail.ClothesDetailPresenterImpl;
import com.paypal.android.sdk.payments.PayPalConfiguration;
//import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ClothesDetailActivity extends AppCompatActivity implements
        ClothesDetailActivityView,
        View.OnClickListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        RecyclerViewAdapter.OnItemClickListener,
        RatingDialog.onClickRateButton{

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.img_clothes)
    ImageView imgClothes;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_save)
    ImageButton fabSave;
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
    @BindView(R.id.img_rate)
    ImageView img_rate;
    private LoadingDialog loadingDialog;
    private ClothesDetailPresenter clothesDetailPresenter;
    private RateClothesAdapter rateClothesAdapter;
    ClothesViewModel clothesViewModel;
    RatingDialog ratingDialog;
    List<RateClothesViewModel> rateClothesViewModels;
    Dialog dialogRating;


    private SimilarClothesAdapter similarClothesAdapter;
    String clothesID;

    PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_detail);
        initVariables();

    }

    @Override
    public void onStart() {
        super.onStart();
        nestedScrollView.scrollTo(-1, -1);
        nestedScrollView.smoothScrollTo(0, 0);
        if (clothesID != null) {
            clothesDetailPresenter.firstFetchSimilarClothes(clothesID);
            clothesDetailPresenter.fetchClothesDetail(getIntent().getStringExtra(Constants.KEY_CLOTHES_ID));
        }
    }

    private void initVariables() {
        ButterKnife.bind(this);

        loadingDialog = new LoadingDialog(this);
        clothesDetailPresenter = new ClothesDetailPresenterImpl(this, this);
        nestedScrollView.scrollTo(-1, -1);
        nestedScrollView.smoothScrollTo(0, 0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setTitle(R.string.clothes_detail);
        }
        ratingDialog = new RatingDialog(this);
        loadingDialog = new LoadingDialog(this);
        fabSave.setOnClickListener(this);
        img_rate.setOnClickListener(this);
        btAddCart.setOnClickListener(this);
        btPay.setOnClickListener(this);


        //start service paypal
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(intent);
        clothesID = getIntent().getStringExtra(Constants.KEY_CLOTHES_ID);
//        if (clothesID != null) {
//            clothesDetailPresenter.fetchClothesDetail(clothesID);
//        }
        rcClothesSimilar.setVisibility(View.VISIBLE);
        similarClothesAdapter = new SimilarClothesAdapter(this);
        similarClothesAdapter.setLoadingMoreListener(this);
        similarClothesAdapter.addOnItemClickListener(this);
        rcClothesSimilar.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rcClothesSimilar.setAdapter(similarClothesAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_PAYPAL: {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation !=null){
                    try {
                        String paymentDetail = confirmation.toJSONObject().toString();
                        Log.i( "onActivityResult: 11",paymentDetail);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }

    }

    public void processPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(10), "USD", "Checkout for Kidd Store",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent,Constants.REQUEST_CODE_PAYPAL);
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
        this.clothesViewModel = clothes;
        Glide.with(this).load(clothes.getLogoUrl()).apply(new RequestOptions().placeholder(R.drawable.book_logo)).into(imgClothes);
        tvNameClothes.setText(clothes.getName());
        tvCostClothes.setText(Utils.formatNumberMoney(clothes.getPrice()) + " đ");
        tvDescriptionCLothes.setText(clothes.getDescription());
        tvAcountRate.setText("số lượt đánh giá (" + clothes.getRateClothesViewModels().size() + ")");
        fabSave.setImageResource(clothes.isSaved() ? R.drawable.ic_save : R.drawable.ic_nosave);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

        rateClothesAdapter = new RateClothesAdapter(ClothesDetailActivity.this);
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
    public void refreshSimilarClothes(List<ClothesPreview> similarClothes) {
        similarClothesAdapter.refresh(similarClothes);
    }

    @Override
    public void loadmoreSimilarClothes(List<ClothesPreview> similarClothes) {
        similarClothesAdapter.addModels(similarClothes, false);

    }

    @Override
    public void switchButtonSaveJobToSaved() {
        fabSave.setImageResource(R.drawable.ic_save);
        clothesViewModel.setSaved(true);
    }

    @Override
    public void switchButtonSaveJobToUnSaved() {
        fabSave.setImageResource(R.drawable.ic_nosave);
        clothesViewModel.setSaved(false);
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
    public void hideRatingDialog() {
        dialogRating.dismiss();
    }

    @Override
    public void getAllRateClothes(List<RateClothesViewModel> rateClothesViewModelList) {
        rateClothesAdapter.clear();
        rateClothesAdapter.addModels(rateClothesViewModelList,false);
        rateClothesAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pay: {
                processPayment();
                break;
            }
            case R.id.fab_save: {
                if (clothesViewModel.isSaved()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Bạn có chắc chắn không theo dõi sản phẩm này nữa ?")
                            .setTitle("Hủy theo dõi")
                            .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    clothesDetailPresenter.deleteSavedClothes(clothesID);
                                }
                            })
                            .setPositiveButton("Không", null)
                            .show();
                } else {
                    clothesDetailPresenter.saveClothes(clothesID);
                }
                break;
            }
            case R.id.img_rate:{
//                ratingDialog.show();
//                ratingDialog.setClickRateButton(this);
//                ratingDialog.setTitle(getString(R.string.rate));
                dialogRating = new Dialog(this);
                dialogRating.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogRating.setContentView(R.layout.rating_dialog);
                MaterialRatingBar ratingBar;
                EditText edt_msg;
                Button btn_submit;

                ratingBar = dialogRating.findViewById(R.id.rating_product);
                edt_msg = dialogRating.findViewById(R.id.edt_cmt);
                btn_submit = dialogRating.findViewById(R.id.btn_rate);

                btn_submit.setOnClickListener(v1->{
                    clothesDetailPresenter.rateClothes(clothesID,new RateClothesBody(edt_msg.getText().toString(),
                            (int) ratingBar.getRating()));

                });

                dialogRating.show();
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
        ClothesPreview clothesPreview = this.similarClothesAdapter.getItem(position, ClothesPreview.class);
        Intent intent = new Intent(this, ClothesDetailActivity.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, clothesPreview.getId());
        startActivity(intent);
    }

    @Override
    public void onClickButton(int rate, String msg) {
    }
}
