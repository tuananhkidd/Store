package com.kidd.store.view.profile.update_description;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.kidd.store.R;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.presenter.profile.update_description.UpdateDescriptionPresenter;
import com.kidd.store.presenter.profile.update_description.UpdateDescriptionPresenterImpl;

public class UpdateDescriptionActivity extends AppCompatActivity implements UpdateDescriptionView {
    EditText edt_des;
    Button btn_update;
    Toolbar toolbar;
    LoadingDialog loadingDialog;
    UpdateDescriptionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_description);

        initWidget();
    }

    void initWidget() {
        edt_des = findViewById(R.id.edt_des);
        btn_update = findViewById(R.id.btn_update);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        loadingDialog = new LoadingDialog(this);
        presenter = new UpdateDescriptionPresenterImpl(this, this);
        btn_update.setOnClickListener(v -> {
            presenter.validateDescription(edt_des.getText().toString());
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
    public void showDescriptionError() {
        edt_des.setError(getString(R.string.must_not_empty));
    }

    @Override
    public void backToProfileScreen() {
//        Intent intent = new Intent();
//        intent.putExtra(Constants.DES, des);
//        setResult(Constants.RESULT_CODE_UPDATE_DESCRIPTION);
        finish();
    }
}
