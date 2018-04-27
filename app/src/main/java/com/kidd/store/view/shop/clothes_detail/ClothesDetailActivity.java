package com.kidd.store.view.shop.clothes_detail;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kidd.store.R;
import com.kidd.store.common.Config;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.Clothes;
import com.kidd.store.models.ClothesPreview;
import com.kidd.store.presenter.shop.clothes_detail.ClothesDetailPresenter;
import com.kidd.store.presenter.shop.clothes_detail.ClothesDetailPresenterImpl;
//import com.paypal.android.sdk.payments.PayPalConfiguration;
//import com.paypal.android.sdk.payments.PayPalPayment;
//import com.paypal.android.sdk.payments.PayPalService;
//import com.paypal.android.sdk.payments.PaymentActivity;
//import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ClothesDetailActivity extends AppCompatActivity implements ClothesDetailActivityView,View.OnClickListener{

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.img_clothes)
    ImageView imgClothes;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    @BindView(R.id.fab_save)
//    FloatingActionButton fabSave;
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
    @BindView(R.id.progress_loading_similar_clothes)
    ProgressBar progressLoadingSimilarClothes;
    private LoadingDialog loadingDialog;
    private ClothesDetailPresenter clothesDetailPresenter;
    String clothesID;

//    PayPalConfiguration configuration = new PayPalConfiguration()
//            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
//            .clientId(Config.CLIENT_ID);

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

    }

    private void initVariables() {
        ButterKnife.bind(this);
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
        loadingDialog = new LoadingDialog(this);
//        fabSave.setOnClickListener(this);
        btAddCart.setOnClickListener(this);
        btPay.setOnClickListener(this);
        clothesDetailPresenter.fetchClothesDetail(getIntent().getStringExtra(Constants.KEY_CLOTHES_ID));

        //start service paypal
//        Intent intent = new Intent(this, PayPalService.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
//        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constants.REQUEST_CODE_PAYPAL:{
//                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//                if(confirmation !=null){
//                    try {
//                        String paymentDetail = confirmation.toJSONObject().toString();
//                        Log.i( "onActivityResult: 11",paymentDetail);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
                break;
            }
        }
    }

    public void processPayment() {
//        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(10), "USD", "Checkout for Kidd Store",
//                PayPalPayment.PAYMENT_INTENT_SALE);
//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
//        startActivityForResult(intent,Constants.REQUEST_CODE_PAYPAL);
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
    public void showClothesDetail(Clothes clothes) {
        Glide.with(this).load(clothes.getLogoUrl()).
                apply(new RequestOptions().placeholder(R.drawable.book_logo))
                .into(imgClothes);
        tvNameClothes.setText(clothes.getName());
        tvCostClothes.setText(Utils.formatNumberMoney(clothes.getPrice())+" đ");
        tvDescriptionCLothes.setText(clothes.getDescription());
       // tvAcountRate.setText("số lượt đánh giá ("+ clothes.get);
    }

    @Override
    public void showErrorLoading(String message) {

    }

    @Override
    public void showProgressSimilarClothes() {

    }

    @Override
    public void hideProgressSimilarClothes() {

    }

    @Override
    public void showErrorSimilarClothes() {

    }

    @Override
    public void hideErrorSimilarClothes() {

    }

    @Override
    public void showSimilarLoadingMoreProgress() {

    }

    @Override
    public void hideSimilarLoadingMoreProgress() {

    }

    @Override
    public void enableLoadingMore(boolean enable) {

    }

    @Override
    public void addSimilarClothes(List<ClothesPreview> similarClothes) {

    }

    @Override
    public void switchButtonSaveJobToSaved() {

    }

    @Override
    public void switchButtonSaveJobToUnSaved() {

    }

    @Override
    public void showListSimilarClothes() {

    }

    @Override
    public void hideListSimilarClothes() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_pay:{
                processPayment();
                break;
            }
        }
    }
}
