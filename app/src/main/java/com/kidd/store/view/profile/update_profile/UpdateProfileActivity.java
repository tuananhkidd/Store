package com.kidd.store.view.profile.update_profile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kidd.store.GlideApp;
import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.response.Profile;
import com.kidd.store.presenter.profile.edit_profile.EditProfilePresenter;
import com.kidd.store.presenter.profile.edit_profile.EditProfilePresenterImpl;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener, UpdateProfileView {
    private static final int REQUEST_CODE_PICK_AVATAR = 1;
    EditText edt_fullName;
    CircleImageView img_avatar;
    EditText edt_birthDay;
    ImageButton btn_avt;
    AppCompatSpinner spinner;
    EditText edt_address;
    EditText edt_email;
    EditText edt_phone;
    EditText edt_card;
    Toolbar toolbar;
    int gender;
    LoadingDialog loadingDialog;
    Uri uri = null;
    EditProfilePresenter presenter;
    Profile profile;
    long birthday = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        initWidget();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            profile = (Profile) intent.getSerializableExtra(Constants.PROFILE);
            edt_fullName.setText(profile.getFullName() == null ? null : profile.getFullName());
            edt_birthDay.setText(Utils.getDateFromMilliseconds(profile.getBirthday()));
            edt_address.setText(profile.getAddress() == null ? null : profile.getAddress());
            edt_phone.setText(profile.getPhone() == null ? null : profile.getPhone());
            edt_card.setText(profile.getIdentityCard() == null ? null : profile.getIdentityCard());
            edt_email.setText(profile.getEmail() == null ? null : profile.getEmail());

            GlideApp.with(this)
                    .load(profile.getAvatarUrl())
                    .placeholder(R.drawable.avatar_placeholder)
                    .into(img_avatar);
        }

        presenter = new EditProfilePresenterImpl(this, this);

        List<String> lsGenger = new ArrayList<>();
        lsGenger.add("Nữ");
        lsGenger.add("Nam");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lsGenger);
        spinner.setAdapter(adapter);

        spinner.setSelection(profile.getGender() == 1 ? 1 : 0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void initWidget() {
        img_avatar = findViewById(R.id.img_avatar);
        btn_avt = findViewById(R.id.btn_avatar);
        edt_fullName = findViewById(R.id.edt_full_name);
        edt_birthDay = findViewById(R.id.edt_birthday);
        spinner = findViewById(R.id.spinner_gender);
        edt_address = findViewById(R.id.edt_address);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_card = findViewById(R.id.edt_id_card);
        toolbar = findViewById(R.id.toolbar);
        edt_birthDay.setOnClickListener(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        loadingDialog = new LoadingDialog(this);
        btn_avt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_avatar: {
                FishBun.with(this)
                        .setImageAdapter(new GlideAdapter())
                        .setMaxCount(1)
                        .setMinCount(1)
                        .setActionBarColor(getResources().getColor(R.color.colorPrimary),
                                getResources().getColor(R.color.colorPrimaryDark),
                                false)
                        .setActionBarTitleColor(getResources().getColor(android.R.color.white))
                        .setButtonInAlbumActivity(false)
                        .setCamera(true)
                        .exceptGif(true)
                        .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back))
                        .setOkButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_select))
                        .setAllViewTitle(getResources().getString(R.string.selected))
                        .setActionBarTitle(getResources().getString(R.string.pick_avatar))
                        .textOnNothingSelected(getResources().getString(R.string.must_pick_one_image))
                        .setRequestCode(REQUEST_CODE_PICK_AVATAR)
                        .startAlbum();
                break;
            }
            case R.id.edt_birthday: {
                Utils.dialogShowDate(this, "Chọn ngày sinh", new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.format("%02d", dayOfMonth)
                                + "-" +
                                String.format("%02d", (monthOfYear + 1))
                                + "-" +
                                year;
                        birthday = Utils.millisecondsFromDate(date);
                        edt_birthDay.setText(date);
                    }
                });
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICK_AVATAR: {
                if (Activity.RESULT_OK == resultCode) {
                    ArrayList<Parcelable> path = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    uri = Uri.parse(path.get(0).toString());
                    Log.i("img1", "onActivityResult: " + path.get(0).toString());
                    GlideApp.with(this)
                            .load(path.get(0))
                            .placeholder(R.drawable.avatar_placeholder)
                            .into(img_avatar);
                }
                break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_done: {
                if (birthday != -1) {
                    profile.setBirthday(birthday);
                }
                presenter.validateProfile(uri, edt_fullName.getText().toString(),
                        edt_phone.getText().toString(),
                        edt_address.getText().toString(),
                        edt_card.getText().toString(), profile.getAvatarUrl(),
                        gender, profile.getBirthday(), edt_email.getText().toString());
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoadingDiaolog() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.hide();
    }

    @Override
    public void showFullNameError() {
        edt_fullName.setError(getString(R.string.must_not_empty));
    }

    @Override
    public void showAddressError() {
        edt_address.setError(getString(R.string.must_not_empty));

    }

    @Override
    public void showEmailError() {
        edt_email.setError(getString(R.string.must_not_empty));

    }

    @Override
    public void showPhoneError() {
        edt_phone.setError(getString(R.string.must_not_empty));

    }

    @Override
    public void showIDCardError() {
        edt_card.setError(getString(R.string.must_not_empty));
    }

    @Override
    public void backToProfileScreen() {
        finish();
    }
}
