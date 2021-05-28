package com.kidd.store.view.account.register;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
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
import com.kidd.store.models.body.CustomerRegisterBody;
import com.kidd.store.presenter.account.register.RegisterPresenter;
import com.kidd.store.presenter.account.register.RegisterPresenterImpl;
import com.kidd.store.view.account.login.LoginActivity;
import com.kidd.store.view.account.verify_email.VerifyEmailActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,
        RegisterView {

    EditText edt_username;
    EditText edt_password;
    EditText edt_phone;
    EditText edt_address;
    EditText edt_fullname;
    Button btn_register;
    TextView txt_login;
    User u;
    LoadingDialog loadingDialog;
    RegisterPresenter presenter;

    DBManager db;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initWidget();

        if(getIntent().getExtras()!=null){
            if(("cancel").equals(getIntent().getStringExtra("key"))){
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }
    }

    void initWidget() {
        edt_password = findViewById(R.id.edt_pass);
        edt_username = findViewById(R.id.edt_user_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_address = findViewById(R.id.edt_address);
        edt_fullname = findViewById(R.id.edt_fullname);
        btn_register = findViewById(R.id.btn_register);
        txt_login = findViewById(R.id.txt_login);
        progressBar = findViewById(R.id.progress);

        btn_register.setOnClickListener(this);
        txt_login.setOnClickListener(this);

        loadingDialog = new LoadingDialog(this);
        presenter = new RegisterPresenterImpl(this, this);

        db = new DBManager(this);
        u = new User();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register: {
                //dong goi đối tượng CustomerRegisterBody mà api cần truyền lên
                CustomerRegisterBody body = new CustomerRegisterBody(edt_fullname.getText().toString(),
                        edt_phone.getText().toString(), edt_address.getText().toString());
                presenter.validateUsernameAndPassword(edt_username.getText().toString(), edt_password.getText().toString(), body);
                break;
            }
            case R.id.txt_login: {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            }
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

    //định nghĩa các hàm kế thừa từ interface ( RegisterView : thông báo các lỗi của dữ liệu đầu vào cho client)
    @Override
    public void showUserNameError() {
        edt_username.setError("Must not empty!");

    }

    @Override
    public void showPasswordError() {
        edt_password.setError("Must not empty!");
    }

    @Override
    public void showFullNameError() {
        edt_fullname.setError("Must not empty!");


    }

    @Override
    public void showAddressError() {
        edt_address.setError("Must not empty!");

    }

    @Override
    public void showPhoneError() {
        edt_phone.setError("Must not empty!");
    }

    @Override
    public void showInvalidUser() {
        edt_username.setError("Invalid email");
    }

    @Override
    public void gotoVerifyActivity(String username) {
        Intent intent = new Intent(this, VerifyEmailActivity.class);
        intent.putExtra(Constants.USER_NAME, username);
        startActivity(intent);
        finish();
    }
}
