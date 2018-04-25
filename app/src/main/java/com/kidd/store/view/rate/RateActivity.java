package com.kidd.store.view.rate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.body.RateBody;
import com.kidd.store.presenter.rate.RatePresenter;
import com.kidd.store.presenter.rate.RatePresenterImpl;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RateActivity extends AppCompatActivity implements RateView, View.OnClickListener {
    MaterialRatingBar ratingBar;
    EditText edt_cmt;
    Button btn_rate;
    LinearLayout ll_parent_reason;
    RelativeLayout ll_parent_sale;
    RelativeLayout ll_parent_service;
    ImageView img_select_service;
    ImageView img_select_sale;
    LoadingDialog loadingDialog;
    RatePresenter presenter;
    RateBody rateBody;
    Toolbar toolbar;
    String reason = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        initWidget();
    }

    public void initWidget() {
        ratingBar = findViewById(R.id.rate);
        edt_cmt = findViewById(R.id.edt_cmt);
        btn_rate = findViewById(R.id.btn_rate);
        toolbar = findViewById(R.id.tool_bar);
        ll_parent_reason = findViewById(R.id.llParentReason);
        ll_parent_sale = findViewById(R.id.ll_parent_sale);
        ll_parent_service = findViewById(R.id.ll_parent_service);
        img_select_sale = findViewById(R.id.img_select_sale);
        img_select_service = findViewById(R.id.img_select_service);
        loadingDialog = new LoadingDialog(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        ratingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                if (ratingBar.getRating() < 3) {
                    ll_parent_reason.setVisibility(View.VISIBLE);
                } else {
                    ll_parent_reason.setVisibility(View.GONE);
                    reason = "";
                    img_select_sale.setVisibility(View.GONE);
                    img_select_service.setVisibility(View.GONE);
                }
            }
        });

        presenter = new RatePresenterImpl(this, this);
        btn_rate.setOnClickListener(this);
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
    public void showCommentError() {
        edt_cmt.setError(getString(R.string.must_not_empty));
    }

    @Override
    public void backToHomeScreen() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_parent_sale: {
                Toast.makeText(this, "hihi", Toast.LENGTH_SHORT).show();
                img_select_sale.setVisibility(View.VISIBLE);
                reason += Constants.SALE + ",";
                break;
            }
            case R.id.ll_parent_service: {
                Toast.makeText(this, "hihi111", Toast.LENGTH_SHORT).show();

                img_select_service.setVisibility(View.VISIBLE);
                reason += Constants.SERVICE + ",";
                break;
            }
            case R.id.llParentReason: {
                Toast.makeText(this, "hehe", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_rate: {
                if (Utils.checkNetwork(this)) {
                    rateBody = new RateBody((int) ratingBar.getRating(), edt_cmt.getText().toString(), reason);
                    presenter.validateCmt(edt_cmt.getText().toString(), rateBody);
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            }
        }
    }
}
