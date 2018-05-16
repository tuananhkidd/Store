package com.kidd.store.view.account.login.facebook_login;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.kidd.store.R;
import com.kidd.store.adapter.SpinnerGenderAdapter;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.body.FacebookLoginBody;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.presenter.account.login.facebook_login.FacebookLoginPresenter;
import com.kidd.store.presenter.account.login.facebook_login.FacebookLoginPresenterImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class FaceBookLoginActivity extends AppCompatActivity implements FacebookLoginView{
    TextView edt_birthday;
    EditText edt_email;
    AppCompatSpinner spinner;
    SpinnerGenderAdapter adapter;
    Button btn_register;
    List<String> lsGender;
    boolean gen;
    FacebookLoginBody facebookLoginBody;
    LoadingDialog loadingDialog;
    FacebookLoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_book_login);

        getDataFromIntent();
        initWidget();

        loadingDialog = new LoadingDialog(this);
        presenter = new FacebookLoginPresenterImpl(this,this);
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        if(facebookLoginBody.isGender()){
            spinner.setSelection(0);
        }else {
            spinner.setSelection(1);
        }

        if(facebookLoginBody.getBirthDay() == -1){
            edt_birthday.setText(day+"/"+month+"/"+year);
        }else {
            edt_birthday.setText(Utils.getDateFromMilliseconds(facebookLoginBody.getBirthDay()));
        }

        edt_birthday.setOnClickListener(v->{
            setUpTimerPickerDialog(day,month,year);
        });

        btn_register.setOnClickListener(v->{
            facebookLoginBody.setEmail(edt_email.getText().toString());
            presenter.facebookLogin(facebookLoginBody);
        });


    }

    void getDataFromIntent(){
        facebookLoginBody = (FacebookLoginBody) getIntent().getSerializableExtra(Constants.FACEBOOK);
    }

    private void initWidget() {
        edt_birthday = findViewById(R.id.edt_birthday);
        edt_email = findViewById(R.id.edt_email);
        spinner = findViewById(R.id.spinner_gender);
        btn_register= findViewById(R.id.btn_register);


        lsGender= new ArrayList<>();
        lsGender.add("Nam");
        lsGender.add("Nữ");

        adapter = new SpinnerGenderAdapter(this,R.layout.item_spinner,lsGender);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                facebookLoginBody.setGender(i ==0 ? true:false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_email.setText(facebookLoginBody.getEmail() == null ? null : facebookLoginBody.getEmail());
        edt_birthday.setText(facebookLoginBody.getBirthDay() == -1 ? null : Utils.getDateFromMilliseconds(facebookLoginBody.getBirthDay()));
    }

    void setUpTimerPickerDialog(int day,int month,int year){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                edt_birthday.setText(d+"/"+(m+1)+"/"+y);
                facebookLoginBody.setBirthDay(Utils.millisecondsFromDate(d+"-"+(m+1)+"-"+y));
            }
        }, year, month, day);
        datePickerDialog.show();
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
    public void showEmailError() {
        edt_email.setError(getString(R.string.must_not_empty));
    }

    @Override
    public void showEmailInvalid() {
        edt_email.setError(getString(R.string.invalid_email));
    }

    @Override
    public void backToHomeScreen(HeaderProfile headerProfile, int resultCode) {
        setResult(resultCode);
        finish();
    }
}
