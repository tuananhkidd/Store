package com.kidd.store.view.feedback;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kidd.store.R;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.presenter.feedback.FeedbackPresenter;
import com.kidd.store.presenter.feedback.FeedbackPresenterImpl;

public class FeedbackActivity extends AppCompatActivity implements FeedbackView {

    EditText edt_feedback;
    Button btn_send;
    Toolbar toolbar;
    LoadingDialog loadingDialog;
    FeedbackPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initWidget();
    }

    public void initWidget() {
        presenter = new FeedbackPresenterImpl(this, this);

        edt_feedback = findViewById(R.id.edt_feedback);
        btn_send = findViewById(R.id.btn_Send);
        toolbar = findViewById(R.id.toolbar);
        loadingDialog = new LoadingDialog(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.validateFeedbackInput(edt_feedback.getText().toString());
            }
        });
    }

    @Override
    public void showFeedbackInputError() {
        edt_feedback.setError("Must not empty");
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
    public void backToHome() {
        finish();
    }
}
