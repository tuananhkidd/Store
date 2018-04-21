package com.kidd.store.view.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kidd.store.GlideApp;
import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.UserAuth;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.response.Profile;
import com.kidd.store.presenter.profile.ProfilePresenter;
import com.kidd.store.presenter.profile.ProfilePresenterImpl;
import com.kidd.store.services.event_bus.DescriptionChangeEvent;
import com.kidd.store.services.event_bus.UserAuthorizationChangedEvent;
import com.kidd.store.view.profile.update_description.UpdateDescriptionActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements ProfileView, View.OnClickListener {

    LoadingDialog loadingDialog;
    CircleImageView img_avt;
    TextView txt_name;
    TextView txt_birthday;
    TextView txt_gender;
    TextView txt_address;
    TextView txt_email;
    TextView txt_phone;
    TextView txt_card;
    TextView txt_description;
    ImageButton btn_edit_des;
    ImageButton btn_edit_profile;
    Toolbar toolbar;
    Profile profile;

    ProfilePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initWidget();
    }

    void initWidget() {
        loadingDialog = new LoadingDialog(this);
        img_avt = findViewById(R.id.img_avatar);
        txt_name = findViewById(R.id.txt_name);
        txt_birthday = findViewById(R.id.txt_birthday);
        txt_gender = findViewById(R.id.txt_gender);
        txt_address = findViewById(R.id.txt_address);
        txt_email = findViewById(R.id.txt_email);
        txt_phone = findViewById(R.id.txt_phone);
        txt_card = findViewById(R.id.txt_id_card);
        txt_description = findViewById(R.id.txt_description);
        btn_edit_des = findViewById(R.id.btn_edit_description);
        btn_edit_profile = findViewById(R.id.btn_edit_user_profile);
        btn_edit_profile.setOnClickListener(this);
        btn_edit_des.setOnClickListener(this);
        EventBus.getDefault().register(this);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        presenter = new ProfilePresenterImpl(this, this);
        presenter.getProfile(Utils.getSharePreferenceValues(this, Constants.CUSTOMER_ID));

        btn_edit_profile.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(Constants.PROFILE, profile);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this );
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
    public void showProfile(Profile prof) {
        profile = prof;
        GlideApp.with(this)
                .load(profile.getAvatarUrl())
                .placeholder(R.drawable.avatar_placeholder)
                .into(img_avt);

        txt_name.setText(profile.getFullName() == null ? "-" : profile.getFullName());
        txt_birthday.setText(Utils.getDateFromMilliseconds(profile.getBirthday()));
        txt_gender.setText(profile.getGender() == 1 ? "Nam" : "Nữ");
        txt_address.setText(profile.getAddress() == null ? "-" : profile.getAddress());
        txt_phone.setText(profile.getPhone() == null ? "-" : profile.getPhone());
        txt_card.setText(profile.getIdentityCard() == null ? "-" : profile.getIdentityCard());
        txt_description.setText(profile.getDescription() == null ? "-" : profile.getDescription());
        txt_email.setText(profile.getEmail() == null ? "-" : profile.getEmail());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateDescriptionChangedEvent(DescriptionChangeEvent descriptionChangeEvent) {
        txt_description.setText(descriptionChangeEvent.getDescription());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_description: {
                Intent intent = new Intent(this, UpdateDescriptionActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.btn_edit_user_profile: {
                break;
            }
        }
    }
}
