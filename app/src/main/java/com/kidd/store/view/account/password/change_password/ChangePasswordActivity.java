package com.kidd.store.view.account.password.change_password;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.kidd.store.R;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.presenter.account.password.change_passwrod.ChangPasswordPresenterImpl;
import com.kidd.store.presenter.account.password.change_passwrod.ChangePasswordPresenter;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordView {
    EditText edt_oldpass;
    EditText edt_newpass;
    EditText edt_confirmpass;
    Toolbar toolbar;
    Button btn_change;
    LoadingDialog loadingDialog;
    ChangePasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_password);
        initWidget();
    }

    private void initWidget() {
        edt_confirmpass = findViewById(R.id.edt_confirm_pass);
        edt_newpass = findViewById(R.id.edt_new_pass);
        edt_oldpass = findViewById(R.id.edt_old_pass);
        btn_change = findViewById(R.id.btn_change);
        loadingDialog = new LoadingDialog(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        presenter = new ChangPasswordPresenterImpl(this, this);

        btn_change.setOnClickListener(v -> {
            presenter.validateFeild(edt_oldpass.getText().toString(),
                    edt_newpass.getText().toString(), edt_confirmpass.getText().toString());
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
    public void showOldPassError() {
        edt_oldpass.setError(getString(R.string.must_not_empty));
    }

    @Override
    public void showNewPassError() {
        edt_newpass.setError(getString(R.string.must_not_empty));

    }

    @Override
    public void showConfirmPassError() {
        edt_confirmpass.setError(getString(R.string.must_not_empty));

    }

    @Override
    public void showConfirmPasNotMatch() {
        edt_confirmpass.setError(getString(R.string.not_match));
    }

    @Override
    public void backToHomeScreen() {
        finish();
    }
}
