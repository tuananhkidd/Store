package com.kidd.store.view.chat;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kidd.store.GlideApp;
import com.kidd.store.R;
import com.kidd.store.adapter.ChatMessageAdapter;
import com.kidd.store.adapter.EndlessLoadingRecyclerViewAdapter;
import com.kidd.store.adapter.RecyclerViewAdapter;
import com.kidd.store.common.Constants;

import com.kidd.store.common.UserAuth;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.PageList;
import com.kidd.store.models.model_chat.Message;
import com.kidd.store.models.model_chat.UserChat;
import com.kidd.store.models.model_chat.UserMessage;
import com.kidd.store.presenter.chat.ChatPresenter;
import com.kidd.store.presenter.chat.ChatPresenterImpl;
import com.kidd.store.services.event_bus.FriendStateOnlineEvent;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity  implements ChatView, View.OnClickListener,
        RecyclerViewAdapter.OnItemClickListener,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener{
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.img_online)
    CircleImageView imgOnline;

    @BindView(R.id.rc_messages)
    RecyclerView rcMessages;
    @BindView(R.id.edt_message)
    EditText edtMessage;

    @BindView(R.id.btn_send)
    ImageButton btnSend;
    @BindView(R.id.ln_input)
    RelativeLayout rlChat;

    ChatMessageAdapter messageAdapter;
    private String roomID;
    private UserChat own, userFriend;
    private List<UserMessage> userMessages;
    private ChatPresenter chatPresenter;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        loadingDialog= new LoadingDialog(this);
        own = new UserChat();
        userFriend = (UserChat) getIntent().getSerializableExtra(Constants.KEY_USER_FRIEND);
        roomID = getIntent().getStringExtra(Constants.KEY_ROOM_ID);
        chatPresenter= new ChatPresenterImpl(this, this,roomID);

        initToolBar();
        initData(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            initToolBarUserFriend(userFriend);
        }
    }
    private void initToolBarUserFriend(UserChat userFriend){
        GlideApp.with(this)
                .load(userFriend.getAvatarUrl())
                .placeholder(R.drawable.logoapp)
                .into(imgAvatar);
        txtName.setText(userFriend.getFirstName());

//        txtEmail.setText(userFriend.isOnline() ? getString(R.string.toolbal_state_online) : getString(R.string.toolbal_state_offline));
//        imgOnline.setVisibility(userFriend.isOnline() ? View.VISIBLE : View.GONE);
    }

    protected void initData(Bundle savedInstanceState) {
        chatPresenter.registerOnMessageAddedListener();
        userMessages= new ArrayList<>();
        btnSend.setOnClickListener(this);

        rlChat.setOnClickListener(this);
        edtMessage.setOnClickListener(this);
        String userone= UserAuth.getUserID(this);
        messageAdapter = new ChatMessageAdapter(getApplicationContext(), own, userFriend);
        messageAdapter.addOnItemClickListener(this);
        messageAdapter.setLoadingMoreListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcMessages.setLayoutManager(linearLayoutManager);
        rcMessages.addItemDecoration(new DividerItemDecoration(getApplicationContext(), linearLayoutManager.getOrientation()));
        rcMessages.setHasFixedSize(true);
        rcMessages.setAdapter(messageAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
            break;

            case R.id.menu_setting: {

            }
            break;

            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatPresenter.unregisterOnMessageAddedListener();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFriendStateOnlineEvent(FriendStateOnlineEvent friendStateOnlineEvent) {
        if(friendStateOnlineEvent!=null){
            if(friendStateOnlineEvent.getUser()!=null && friendStateOnlineEvent.getUser().getEmail().equals(userFriend.getEmail())) {
                initToolBarUserFriend(friendStateOnlineEvent.getUser());
            }else if(friendStateOnlineEvent.getUser()!=null && friendStateOnlineEvent.getUser().getEmail().equals(UserAuth.getUserID(this))){
                if(!friendStateOnlineEvent.getUser().isOnline()){
                    chatPresenter.unregisterOnMessageAddedListener();
                }else{
                    chatPresenter.registerOnMessageAddedListener();
                }
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send: {
                chatPresenter.validateSendingMessage(edtMessage.getText().toString());
//                Message objectMessage = new Message(UserAuth.getUserID(this), edtMessage.getText());
//                //  objectMessage.setCreatedDate(new Date());
//
//                FirebaseFirestore.getInstance().collection(Constants.COUPLE_ROOMS_COLLECTION)
//                        .document(roomID).collection(Constants.MESSAGES_COLLECTIONS)
//                        .add(objectMessage)
//                        //    .set(messageMap, SetOptions.merge())
//                        //             .addOnSuccessListener(success -> listener.onRequestSuccess())
//                        .addOnFailureListener(this,error -> Log.i("erro","listener"));
            }
            break;


            case R.id.edt_message:{
                Toast.makeText(this, "123456", Toast.LENGTH_SHORT).show();
                //     goToSeenByState();
            }
            break;
            default: {
                break;
            }
        }
    }
    //    private void goToSeenByState() {
//        for (UserMessage userMessage: userMessages) {
//            if(!userMessage.getMessage().getOwner().equals((UserAuth.getUserID(this)))){
//                userMessage.getMessage().getSeenBy().add(UserAuth.getUserID(this));
//                FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION)
//                        .document(userMessage.getId())
//                        .update(Message.SEEN_BY, userMessage.getMessage().getSeenBy())
//                        .addOnFailureListener(e -> Log.i("seenBy", e.toString()));
//            }
//        }
//
//    }
    @Override
    public void addMessage(UserMessage userMessag) {
        if (userMessag != null) {
            messageAdapter.addModel(userMessag.getMessage(), true);
        }
        userMessages.add(userMessag);
        edtMessage.setText(null);
    }

    @Override
    public void modifiedMessage(UserMessage userMessage) {
        for (int i = 0; i < userMessages.size(); i++) {
            if(userMessages.get(i).getId().equals(userMessage.getId())){
                messageAdapter.updateModel(i, userMessage.getMessage());
            }
        }
    }

    @Override
    public void onMessageSeen() {

    }

    @Override
    public void showLoadMoreProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadMoreProgress() {
        loadingDialog.hide();
    }

    @Override
    public void enableLoadMore(boolean enable) {

    }

    @Override
    public void enableRefreshing(boolean enable) {

    }

    @Override
    public void showRefreshingProgress() {

    }

    @Override
    public void hideRefreshingProgress() {

    }

    @Override
    public void refreshMessages(PageList<Message> messagePageList) {

    }


    @Override
    public void onLoadMore() {

    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {

    }
}
