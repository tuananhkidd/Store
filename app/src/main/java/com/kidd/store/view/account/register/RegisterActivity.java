package com.kidd.store.view.account.register;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.Login;
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

import java.util.UUID;

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
            if(getIntent().getStringExtra("key").equals("cancel")){
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
                CustomerRegisterBody body = new CustomerRegisterBody(edt_fullname.getText().toString(),
                        edt_phone.getText().toString(), edt_address.getText().toString());
                presenter.validateUsernameAndPassword(edt_username.getText().toString(), edt_password.getText().toString(), body);
//                if (!setupError()) {
//                    return;
//                }
//                if (db.check_user(edt_username.getText().toString())) {
//                    db.register(u);
//                    progressBar.setVisibility(View.VISIBLE);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent();
//                            intent.putExtra(Constants.USER, u.getUsername());
//                            setResult(Constants.RESULT_CODE_SIGNUP, intent);
//                            finish();
//                        }
//                    }, 2000);
//                } else {
//                    Toast.makeText(this, "Account exist !!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                break;
            }
            case R.id.txt_login: {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            }
        }

    }

    boolean setupError() {
        if (edt_username.getText().toString().isEmpty()) {
            edt_username.setError("Must not empty!");
            return false;
        }
        if (edt_password.getText().toString().isEmpty()) {
            edt_username.setError("Must not empty !");
            return false;
        }

//        if (!edt_confirm_password.getText().toString().equals(edt_password.getText().toString())) {
//            edt_confirm_password.setError("Password not match!");
//            return false;
//        }

        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        u = new User(id, edt_username.getText().toString(), edt_password.getText().toString());

        Log.i("user123", id);
        return true;
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
