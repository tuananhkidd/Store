package com.kidd.store.view.account.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kidd.store.R;
import com.kidd.store.SQLiteHelper.DBManager;
import com.kidd.store.common.Constants;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.User;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.presenter.account.login.LoginPresenter;
import com.kidd.store.presenter.account.login.LoginPresenterImpl;
import com.kidd.store.view.account.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    EditText edt_username;
    EditText edt_password;
    Button btn_login;
    TextView txt_signup;
    DBManager db;
    ProgressBar progressBar;
    LoadingDialog loadingDialog;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget();
        getDataFromIntent();
    }

    void initWidget() {
        edt_password = findViewById(R.id.edt_pass);
        edt_username = findViewById(R.id.edt_user_name);
        btn_login = findViewById(R.id.btn_login);
        txt_signup = findViewById(R.id.txt_signup);
        progressBar = findViewById(R.id.progress);

        txt_signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        loadingDialog = new LoadingDialog(this);
        presenter = new LoginPresenterImpl(this,this);

        db = new DBManager(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: {
                presenter.validateUsernameAndPassword(edt_username.getText().toString(), edt_password.getText().toString());
                break;
            }
            case R.id.txt_signup: {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_SIGNUP);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_SIGNUP: {
                if (resultCode == Constants.RESULT_CODE_SIGNUP) {
                    edt_username.setText(data.getStringExtra(Constants.USER));
                }
                break;
            }
        }
    }

    void setupError() {
        if (edt_username.getText().toString().isEmpty()) {
            edt_username.setError("Wrong Info !");
            return;
        }
        if (edt_password.getText().toString().isEmpty()) {
            edt_username.setError("Wrong Info !");
            return;
        }
    }

    void getDataFromIntent() {
        if (getIntent().getExtras() != null) {
            User u = (User) getIntent().getSerializableExtra(Constants.USER);
            edt_username.setText(u.getUsername());
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
    public void showUserNameError() {
        edt_username.setError("Wrong Info !");
    }

    @Override
    public void showPasswordError() {
        edt_password.setError("Wrong Info !");
    }

    @Override
    public void backToHomeScreen(HeaderProfile headerProfile,int resultCode) {
        Intent intent = new Intent();
        intent.putExtra(Constants.HEADER_PROFILE,headerProfile);
        setResult(resultCode,intent);
        finish();
    }
}
