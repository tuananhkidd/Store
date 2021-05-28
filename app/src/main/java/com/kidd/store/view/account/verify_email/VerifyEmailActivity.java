package com.kidd.store.view.account.verify_email;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.presenter.account.resend_email.VerifyEmailPresenter;
import com.kidd.store.presenter.account.resend_email.VerifyPresenterImpl;

public class VerifyEmailActivity extends AppCompatActivity implements VerifyEmailView, View.OnClickListener {

    LoadingDialog loadingDialog;
    Button btn_accomplish;
    TextView txt_resend;
    VerifyEmailPresenter presenter;
    String username;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        getDataFromIntent();
        initData();
    }

    public void initData() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v->{
            finish();
        });
        loadingDialog = new LoadingDialog(this);
        btn_accomplish = findViewById(R.id.btn_accomplish);
        txt_resend = findViewById(R.id.txt_resend_mail);
        presenter = new VerifyPresenterImpl(this, this);

        btn_accomplish.setOnClickListener(this);
        txt_resend.setOnClickListener(this);

    }

    void getDataFromIntent() {
        if (getIntent().getExtras() != null) {
            username = getIntent().getStringExtra(Constants.USER_NAME);
        }
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accomplish: {
                finish();
                break;
            }
            case R.id.txt_resend_mail: {
                presenter.VerifyEmail(username);
            }
        }
    }


}
