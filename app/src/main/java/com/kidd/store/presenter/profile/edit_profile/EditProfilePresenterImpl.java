package com.kidd.store.presenter.profile.edit_profile;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.models.body.ProfileBody;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.models.response.Profile;
import com.kidd.store.services.event_bus.HeaderProfileEvent;
import com.kidd.store.services.event_bus.ProfileChangeEvent;
import com.kidd.store.view.profile.update_profile.UpdateProfileView;

import org.greenrobot.eventbus.EventBus;

public class EditProfilePresenterImpl implements EditProfilePresenter {
    private Context context;
    private UpdateProfileView editProfileView;
    private EditProfileInterator editProfileInterator;

    public EditProfilePresenterImpl(Context context, UpdateProfileView editProfileView) {
        this.context = context;
        this.editProfileView = editProfileView;
        this.editProfileInterator = new EditProfileInteractorImpl(context);
    }

    @Override
    public void validateProfile(Uri uri,
                                String fullName,
                                String phone,
                                String address,
                                String identityCard,
                                String avatarUrl,
                                int gender,
                                long birthday,
                                String email) {
        if (fullName.isEmpty()) {
            editProfileView.showFullNameError();
            return;
        }
        if (phone.isEmpty()) {
            editProfileView.showPhoneError();
            return;
        }
        if (address.isEmpty()) {
            editProfileView.showAddressError();
            return;
        }
        if (identityCard.isEmpty()) {
            editProfileView.showIDCardError();
            return;
        }
        if (email.isEmpty()) {
            editProfileView.showEmailError();
            return;
        }
        String customerID = Utils.getSharePreferenceValues(context, Constants.CUSTOMER_ID);

        editProfileView.showLoadingDiaolog();
        ProfileBody profileBody = new ProfileBody(fullName, phone, address, identityCard, gender, birthday, email);
        if (uri != null) {
            FirebaseApp.initializeApp(context);
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference sr = firebaseStorage.getReferenceFromUrl("gs://store-803c3.appspot.com/");
            StorageReference storageReference = firebaseStorage.getReference().child("Customer/" + customerID);
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileBody.setAvatarUrl(taskSnapshot.getUploadSessionUri().toString());
                    Log.i("firebaswURI", "onSuccess: " + taskSnapshot.getUploadSessionUri().toString());
                    updateProfile(profileBody, customerID);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("firebaswURI", "onSuccess:" + e.getCause().toString());
                    Toast.makeText(context, "Upload Image Fail!", Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        } else {
            profileBody.setAvatarUrl(avatarUrl);
        }

        updateProfile(profileBody, customerID);


    }

    public void updateProfile(ProfileBody profileBody, String customerID) {
        editProfileInterator.updateProfile(profileBody, new OnEditSuccessListener() {
            @Override
            public void onSuccess(Profile profile) {
                editProfileView.hideLoadingDialog();
                editProfileView.backToProfileScreen();
                EventBus.getDefault().post(new ProfileChangeEvent(profile));
                HeaderProfile headerProfile = new HeaderProfile(customerID, profile.getFullName(),
                        profile.getAvatarUrl(), profile.getEmail());
                EventBus.getDefault().post(new HeaderProfileEvent(headerProfile));
                Utils.saveHeaderProfile(context,headerProfile);
            }

            @Override
            public void onError(String message) {
                editProfileView.hideLoadingDialog();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewDestroy() {
        editProfileInterator.onViewDestroy();
    }
}
