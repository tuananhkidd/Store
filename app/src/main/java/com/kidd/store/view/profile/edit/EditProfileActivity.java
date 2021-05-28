package com.kidd.store.view.profile.edit;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.models.response.Profile;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {
    EditText edt_fullName;
    EditText edt_birthDay;
    AppCompatSpinner spinner;
    EditText edt_address;
    EditText edt_email;
    EditText edt_phone;
    EditText edt_card;
    int gender;

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
            Profile profile = (Profile) intent.getSerializableExtra(Constants.PROFILE);
            edt_fullName.setText(profile.getFullName() == null ? "-" : profile.getFullName());
            edt_birthDay.setText(Utils.getDateFromMilliseconds(profile.getBirthday()));
            edt_address.setText(profile.getAddress() == null ? "-" : profile.getAddress());
            edt_phone.setText(profile.getPhone() == null ? "-" : profile.getPhone());
            edt_card.setText(profile.getIdentityCard() == null ? "-" : profile.getIdentityCard());
            edt_email.setText(profile.getEmail() == null ? "-" : profile.getEmail());
        }

        List<String> lsGenger = new ArrayList<>();
        lsGenger.add("Nữ");
        lsGenger.add("Nam");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lsGenger);
        spinner.setAdapter(adapter);

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
        edt_fullName = findViewById(R.id.edt_full_name);
        edt_birthDay = findViewById(R.id.edt_birthday);
        spinner = findViewById(R.id.spinner_gender);
        edt_address = findViewById(R.id.edt_address);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        edt_card = findViewById(R.id.edt_id_card);

    }
}
