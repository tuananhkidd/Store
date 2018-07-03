package com.kidd.store.view.cart;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.kidd.store.R;
import com.kidd.store.adapter.CartAdapter;
import com.kidd.store.adapter.RecyclerViewAdapter;
import com.kidd.store.common.Config;
import com.kidd.store.common.Constants;
import com.kidd.store.common.ToastUtils;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.Item;
import com.kidd.store.models.body.SetOrderBody;
import com.kidd.store.presenter.checkout.CheckoutPresenter;
import com.kidd.store.presenter.checkout.CheckoutPresenterImpl;
import com.kidd.store.services.ManageCart;
import com.kidd.store.view.shop.clothes_detail.ClothesDetailActivity;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements
        RecyclerViewAdapter.OnItemClickListener,
        CartActivityView,
        CartAdapter.ClickChangeCount {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rc_item)
    RecyclerView rcItem;
    @BindView(R.id.tv_cost_cart)
    TextView tvCostCart;
    @BindView(R.id.bt_pay_cart)
    Button btPayCart;
    private CartAdapter cartAdapter;

    private LoadingDialog loadingDialog;

    CheckoutPresenter presenter;

    PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.CLIENT_ID);

    private MenuItem itemDelete;
    private MenuItem itemSubmit;
    private MenuItem itemCancel;

    Set<SetOrderBody> setOrderBodies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        initData();

        setOrderBodies = new HashSet<>();
        presenter = new CheckoutPresenterImpl(this, this);
        //start service paypal
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(intent);

    }

    public void processPayment(int value) {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(value), "USD", "Checkout for Kidd Store",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, Constants.REQUEST_CODE_PAYPAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_PAYPAL: {
                if (data != null) {
                    PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirmation != null) {
                        try {
                            presenter.checkout(setOrderBodies);
                            String paymentDetail = confirmation.toJSONObject().toString();
                            Log.i("onActivityResult: 11", paymentDetail);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                }
                break;
            }
        }

    }

    private void initData() {
        loadingDialog = new LoadingDialog(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setTitle(R.string.cart_clothes);
        }
        cartAdapter = new CartAdapter(this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcItem.setLayoutManager(linearLayoutManager);
        rcItem.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        rcItem.setHasFixedSize(true);
        rcItem.setAdapter(cartAdapter);
        cartAdapter.addModels(ManageCart.getCart().getItems(), false);
        ManageCart.getCart().getItems();
        tvCostCart.setText(Utils.formatNumberMoney(ManageCart.getCart().getTotalMoney()) + " đ");

        btPayCart.setOnClickListener(v -> {
            if(ManageCart.getCart().getItems().size()!=0){
                for (Item item : ManageCart.getCart().getItems()) {
                    setOrderBodies.add(new SetOrderBody(item.getColor(),
                            item.getSize(),
                            item.getCount(),
                            item.getClothes().getPrice(),
                            item.getClothes().getId()));
                }
                processPayment(ManageCart.getCart().getTotalMoney());
            }else {
                ToastUtils.quickToast(this,"Giỏ hàng rỗng !");
                return;
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        itemDelete = menu.findItem(R.id.action_delete);
        itemSubmit = menu.findItem(R.id.action_submit);
        itemCancel = menu.findItem(R.id.action_cancel);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
            }
            break;
            case R.id.action_delete: {
                switchToDeleteMode();
            }
            break;

            case R.id.action_submit: {
                for (int i = 0; i < cartAdapter.getItemCount(); i++) {
                    if (cartAdapter.isItemSelected(i)) {
                        ManageCart.getCart().removeToCart(cartAdapter.getItem(i, Item.class));
                    }
                }
                tvCostCart.setText(Utils.formatNumberMoney(ManageCart.getCart().getTotalMoney()) + " đ");
                deleteAllSelectedClothes();
                switchToViewMode();
            }
            break;

            case R.id.action_cancel: {
                cartAdapter.recoverBackup();
                switchToViewMode();
            }
            break;

            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideProgress() {
        loadingDialog.hide();
    }

    @Override
    public void showProgress() {
        loadingDialog.show();
    }

    @Override
    public void deleteAllSelectedClothes() {
        cartAdapter.removeAllSelectedItems();
    }

    @Override
    public void switchToDeleteMode() {
        cartAdapter.removeOnItemClickListener(this);
        cartAdapter.setSelectedMode(true);
        itemDelete.setVisible(false);
        itemSubmit.setVisible(true);
        itemCancel.setVisible(true);
    }

    @Override
    public void switchToViewMode() {
        cartAdapter.addOnItemClickListener(this);
        cartAdapter.setSelectedMode(false);
        itemDelete.setVisible(true);
        itemSubmit.setVisible(false);
        itemCancel.setVisible(false);
    }

    @Override
    public void setEnableRefresh(boolean isEnable) {

    }

    @Override
    public void setEnableToolBar(boolean isEnable) {
        itemDelete.setVisible(isEnable);
    }

    @Override
    public void backToHomeScreen() {
        finish();
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        Item item = cartAdapter.getItem(position, Item.class);
        Intent intent = new Intent(this, ClothesDetailActivity.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, item.getClothes().getId());
        startActivity(intent);
    }

    @Override
    public void clickAddCount(int possition) {
        ManageCart.getCart().plusToCart(cartAdapter.getItem(possition, Item.class));
        cartAdapter.notifyItemChanged(possition);
        tvCostCart.setText(Utils.formatNumberMoney(ManageCart.getCart().getTotalMoney()) + " đ");

    }

    @Override
    public void clickSubCount(int possition) {
        ManageCart.getCart().subToCart(cartAdapter.getItem(possition, Item.class));
        cartAdapter.notifyItemChanged(possition);
        tvCostCart.setText(Utils.formatNumberMoney(ManageCart.getCart().getTotalMoney()) + " đ");
    }
}
