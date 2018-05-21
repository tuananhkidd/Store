package com.kidd.store.view.account.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kidd.store.MainActivity;
import com.kidd.store.R;
import com.kidd.store.SQLiteHelper.DBManager;
import com.kidd.store.common.Config;
import com.kidd.store.common.Constants;
import com.kidd.store.common.ToastUtils;
import com.kidd.store.common.UserAuth;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.body.FacebookLoginBody;
import com.kidd.store.models.model_chat.UserChat;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.presenter.account.login.LoginPresenter;
import com.kidd.store.presenter.account.login.LoginPresenterImpl;
import com.kidd.store.view.account.login.facebook_login.FaceBookLoginActivity;
import com.kidd.store.view.account.register.RegisterActivity;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    EditText edt_username;
    EditText edt_password;
    Button btn_login;
    TextView txt_signup;
    DBManager db;
    ProgressBar progressBar;
    LoadingDialog loadingDialog;
    LoginPresenter presenter;
    Button loginButton;
    CallbackManager callbackManager;
    ImageView img;
    FacebookLoginBody facebookLoginBody;
    private PayPalConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            presenter = new LoginPresenterImpl(this, this);
            facebookLoginBody = new FacebookLoginBody();
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
            callbackManager = CallbackManager.Factory.create();
            configuration = new PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                    .clientId(Config.CLIENT_ID);


            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    result(loginResult);

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    if (error instanceof FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager loginManager = LoginManager.getInstance();
                            loginManager.logOut();
                            loginManager.logInWithReadPermissions(LoginActivity.this,
                                    Arrays.asList("public_profile", "user_friends", "user_gender", "user_birthday", "email"));
                        }
                    } else {
                        ToastUtils.quickToast(LoginActivity.this, R.string.error_message);
                    }
                }
            });
            initWidget();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // getDataFromIntent();

    }

    private void result(LoginResult loginResult) {
        //chung thuc dang nhap vao facebook
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Profile profile = Profile.getCurrentProfile();
                        try {
                            Log.i("JSON result", "onCompleted: " + object.toString());
                            facebookLoginBody.setAvatarUrl(profile.getProfilePictureUri(100, 100).toString());
                            String dob = object.getString("birthday");
                            String str[] = dob.split("/");
                            String birthday = str[1] + "-" + str[0] + "-" + str[2];
                            Log.i("JSON result", "onCompleted: " + Utils.millisecondsFromDate(birthday));
                            facebookLoginBody.setBirthDay(Utils.millisecondsFromDate(birthday));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            facebookLoginBody.setFullname(object.getString("name"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            facebookLoginBody.setEmail(object.getString("email"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String gender = object.getString("gender");
                            if (gender.equalsIgnoreCase("male"))
                                facebookLoginBody.setGender(true);
                            else facebookLoginBody.setGender(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        facebookLoginBody.setFacebookUserID(profile.getId());
                        presenter.validateFacebookLogin(profile.getId());

                    }
                });
        try {
            Bundle bundle = new Bundle();
            bundle.putString("fields", "name,email,first_name,gender,birthday");
            graphRequest.setParameters(bundle);
            graphRequest.executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().logOut();
    }

    void initWidget() {
        edt_password = findViewById(R.id.edt_pass);
        edt_username = findViewById(R.id.edt_user_name);
        btn_login = findViewById(R.id.btn_login);
        txt_signup = findViewById(R.id.txt_signup);
        progressBar = findViewById(R.id.progress);
        loginButton = findViewById(R.id.login_button);
        img = findViewById(R.id.img_logo);

        txt_signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        loadingDialog = new LoadingDialog(this);
    }

    @Override
    public void goToVerifyFacebookAccount() {
        Intent intent = new Intent(LoginActivity.this, FaceBookLoginActivity.class);
        intent.putExtra(Constants.FACEBOOK, facebookLoginBody);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToHomeScreen() {
        FirebaseFirestore.getInstance().collection("users")
                .document(UserAuth.getUserID(this))
                .update("online", true)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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
            case R.id.login_button: {
                LoginManager.getInstance().logInWithReadPermissions(this,
                        Arrays.asList("public_profile", "user_friends", "email"));

                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_SIGNUP: {
                if (resultCode == Constants.RESULT_CODE_SIGNUP) {
                    edt_username.setText(data.getStringExtra(Constants.USER));
                }
                break;
            }
        }
    }


//    void getDataFromIntent() {
//        if (getIntent().getExtras() != null) {
//            User u = (User) getIntent().getSerializableExtra(Constants.USER);
//            edt_username.setText(u.getUsername());
//        }
//    }


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
    public void backToHomeScreen(HeaderProfile headerProfile, int resultCode) {
        FirebaseFirestore.getInstance().collection("users")
                .document(UserAuth.getUserID(this))
                .update("online", true)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        setResult(resultCode);
        finish();
    }
}
