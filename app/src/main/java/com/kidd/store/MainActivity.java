package com.kidd.store;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kidd.store.adapter.FragmentAdapter;
import com.kidd.store.common.Constants;
import com.kidd.store.common.UserAuth;
import com.kidd.store.common.Utils;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.model_chat.UserChat;
import com.kidd.store.models.response.HeaderProfile;
import com.kidd.store.services.event_bus.HeaderProfileEvent;
import com.kidd.store.services.event_bus.UserAuthorizationChangedEvent;
import com.kidd.store.view.about.AboutActivity;
import com.kidd.store.view.account.login.LoginActivity;
import com.kidd.store.view.account.password.change_password.ChangePasswordActivity;
import com.kidd.store.view.account.password.reset_password.ForgetPasswordActivity;
import com.kidd.store.view.account.register.RegisterActivity;
import com.kidd.store.view.cart.CartActivity;
import com.kidd.store.view.chat.ChatActivity;
import com.kidd.store.view.feedback.FeedbackActivity;
import com.kidd.store.view.history_order.HistoryOrderActivity;
import com.kidd.store.view.map.MapsActivity;
import com.kidd.store.view.profile.ProfileActivity;
import com.kidd.store.view.rate.RateActivity;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    FragmentAdapter adapter;
    ViewPager viewPager;
    SpaceNavigationView bottomNavigationView;
    Toolbar toolbar;
    LoadingDialog loadingDialog;
    View userHeaderView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadingDialog = new LoadingDialog(this);


        EventBus.getDefault().register(this);

        drawer = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.frmMain);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.initWithSaveInstanceState(savedInstanceState);
        bottomNavigationView.setSpaceOnClickListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.addSpaceItem(new SpaceItem(getString(R.string.Shopping), R.mipmap.ic_bag));
        bottomNavigationView.addSpaceItem(new SpaceItem(getString(R.string.Following), R.mipmap.ic_heart));
        bottomNavigationView.setCentreButtonIcon(R.drawable.ic_location_red);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        adapter = new FragmentAdapter(getSupportFragmentManager(), this);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                bottomNavigationView.setSelectedItemId(FragmentAdapter.getItemID(position));
                bottomNavigationView.changeCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        switchNavigationDrawer(UserAuth.isUserLoggedIn(this));
        HeaderProfile headerProfile = Utils.getHeaderProfile(this);
        if (headerProfile.getFullName() != null) {
            showHeaderProfile(headerProfile);
        }
    }

    @Override
    protected void onDestroy() {
        Log.i("main", "onDestroy: ");
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private SpaceOnClickListener mOnNavigationItemSelectedListener
            = new SpaceOnClickListener() {
        @Override
        public void onCentreButtonClick() {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }
        @Override
        public void onItemClick(int itemIndex, String itemName) {
            switch (itemIndex) {
                case 0: {
                    toolbar.setTitle(R.string.Shopping);
                    viewPager.setCurrentItem(0);
                    break;
                }
                case 1: {
                    toolbar.setTitle(R.string.Following);
                    viewPager.setCurrentItem(1);
                    break;
                }
            }

        }

        @Override
        public void onItemReselected(int itemIndex, String itemName) {

        }
    };

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        }else if(id==R.id.action_search){
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_logout: {
                FirebaseFirestore.getInstance().collection("users")
                        .document(UserAuth.getUserID(this))
                        .update("online", false)
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
                Utils.setSharePreferenceValues(this, Constants.STATUS_LOGIN, Constants.LOGIN_FAIL);
                Utils.setSharePreferenceValues(this, Constants.USER_NAME, null);
                Utils.setSharePreferenceValues(this, Constants.CUSTOMER_ID, null);
                Utils.saveHeaderProfile(this, null);
                UserAuth.saveLogoutState(this);
                EventBus.getDefault().post(new UserAuthorizationChangedEvent());
                break;
            }
            case R.id.nav_login: {
                startActivityForResult(new Intent(this, LoginActivity.class), Constants.REQUEST_CODE_LOGIN);
                break;
            }
            case R.id.nav_register: {
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            }
            case R.id.nav_account: {
                if (Utils.checkNetwork(this)) {
                    startActivity(new Intent(this, ProfileActivity.class));
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.nav_history: {
                if (Utils.checkNetwork(this)) {
                    startActivity(new Intent(this, HistoryOrderActivity.class));
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }case R.id.nav_support_online: {
                UserChat userChat= new UserChat();
                userChat.setEmail("store@gmail.com");
                userChat.setFirstName("Store Clothes");
                getRoomFriendID(userChat);
                break;
            }
            case R.id.nav_feedback: {
                if (Utils.checkNetwork(this)) {
                    startActivity(new Intent(this, FeedbackActivity.class));
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.nav_rate: {
                if (Utils.checkNetwork(this)) {
                    startActivity(new Intent(this, RateActivity.class));
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.nav_about_store: {
                if (Utils.checkNetwork(this)) {
                    startActivity(new Intent(this, AboutActivity.class));
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.nav_share: {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Kidd Store");
                    String sAux = "\nỨng dụng bán hàng online\n\n";
                    sAux = sAux + "http://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=vi \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Chọn cách thức chia sẻ"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.nav_rate_app: {
                Utils.rateApp(this);
                break;
            }
            case R.id.nav_facebook: {
                if (Utils.checkNetwork(this)) {
                    String url = "https://www.facebook.com/TuanAnhKidd";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.nav_change_password: {
                if (Utils.checkNetwork(this)) {
                    startActivity(new Intent(this, ChangePasswordActivity.class));
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.nav_forget_password: {
                if (Utils.checkNetwork(this)) {
                    startActivity(new Intent(this, ForgetPasswordActivity.class));
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getRoomFriendID(UserChat user) {

        FirebaseFirestore.getInstance().collection(Constants.ROOM_ID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        String roomID = null;
                        for (DocumentSnapshot documentChange : documentSnapshots) {

                            Log.i("CCC", documentChange.getId());
                            if (checkRoomID(documentChange.getString("user1"), documentChange.getString("user2"), user.getEmail())) {
                                roomID = documentChange.getId();
                                Log.i("roomID", documentChange.getId());
                            }
                        }
                        if(roomID==null){
                            addRoomFriendID(user);
                        }else{

                            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                            intent.putExtra(Constants.KEY_USER_FRIEND, (Serializable) user);
                            intent.putExtra(Constants.KEY_ROOM_ID, roomID);
                            startActivity(intent);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void addRoomFriendID(UserChat user) {
        Map<String, Object> room = new HashMap<>();
        room.put("user1", UserAuth.getUserID(this));
        room.put("user2", user.getEmail());
        FirebaseFirestore.getInstance().collection(Constants.ROOM_ID)
                .add(room)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {


                            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                            intent.putExtra(Constants.KEY_USER_FRIEND, (Serializable) user);
                            intent.putExtra(Constants.KEY_ROOM_ID, documentReference.getId());
                            startActivity(intent);

                        Log.i("roomIDADD", documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public boolean checkRoomID(String user1, String user2, String emailFriend) {
        if ((UserAuth.getUserID(this).equals(user1) && emailFriend.equals(user2)) || (UserAuth.getUserID(this).equals(user2) && emailFriend.equals(user1))) {
            return true;
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fm : getSupportFragmentManager().getFragments()) {
            fm.onActivityResult(requestCode, resultCode, data);
        }
        switch (requestCode) {
            case Constants.REQUEST_CODE_LOGIN: {
                if (resultCode == RESULT_OK) {
                    switchNavigationDrawer(true);
                    showHeaderProfile(Utils.getHeaderProfile(this));
                    break;
                }
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedUserAuthChangedEvent(UserAuthorizationChangedEvent userAuthorizationChangedEvent) {
        switchNavigationDrawer(UserAuth.isUserLoggedIn(this));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedHeaderUpdateEvent(HeaderProfileEvent headerProfileEvent) {
        switchNavigationDrawer(true);
        Utils.saveHeaderProfile(MainActivity.this, headerProfileEvent.getHeaderProfile());
        showHeaderProfile(headerProfileEvent.getHeaderProfile());
    }


    public void switchNavigationDrawer(boolean isLoggedIn) {
        navigationView.getMenu().clear();
        navigationView.removeHeaderView(userHeaderView);
        if (isLoggedIn) {
            userHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_auth);
            navigationView.inflateMenu(R.menu.menu_navigation_login);
            Utils.setSharePreferenceValues(this, Constants.STATUS_LOGIN, Constants.LOGIN_TRUE);
        } else {
            userHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_no_auth);
            navigationView.inflateMenu(R.menu.menu_navigation_logout);
            Utils.setSharePreferenceValues(this, Constants.STATUS_LOGIN, Constants.LOGIN_FAIL);
        }
    }


    public void showHeaderProfile(HeaderProfile headerProfile) {
        ImageView imageView = userHeaderView.findViewById(R.id.img_avatar);
        TextView txtFullname = userHeaderView.findViewById(R.id.txt_full_name);
        TextView txtEmail = userHeaderView.findViewById(R.id.txt_email);

        // if (headerProfile != null) {
        txtEmail.setText(headerProfile.getEmail());
        txtFullname.setText(headerProfile.getFullName());
        GlideApp.with(this)
                .load(headerProfile.getAvatarUrl())
                .placeholder(R.drawable.avatar_placeholder)
                .into(imageView);
        //}

    }

}
