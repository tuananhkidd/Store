package com.kidd.store.view.rate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kidd.store.R;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.body.RateBody;
import com.kidd.store.presenter.rate.RatePresenter;
import com.kidd.store.presenter.rate.RatePresenterImpl;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RateActivity extends AppCompatActivity implements RateView {
    MaterialRatingBar ratingBar;
    EditText edt_cmt;
    Button btn_rate;
    LoadingDialog loadingDialog;
    RatePresenter presenter;
    RateBody rateBody;
    Toolbar toolbar;

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
        loadingDialog = new LoadingDialog(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        presenter = new RatePresenterImpl(this, this);
        btn_rate.setOnClickListener(v -> {
            if (Utils.checkNetwork(this)) {
                rateBody = new RateBody((int) ratingBar.getRating(), edt_cmt.getText().toString());
                presenter.validateCmt(edt_cmt.getText().toString(), rateBody);
            } else {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                return;
            }
        });
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
}
