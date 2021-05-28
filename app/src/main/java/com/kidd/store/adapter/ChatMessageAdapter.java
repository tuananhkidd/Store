package com.kidd.store.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.kidd.store.GlideApp;
import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.UserAuth;
import com.kidd.store.common.Utils;import com.kidd.store.models.model_chat.Message;
import com.kidd.store.models.model_chat.UserChat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TranThanhTung on 20/02/2018.
 */

public class ChatMessageAdapter extends EndlessLoadingRecyclerViewAdapter {
    public static final int VIEW_TYPE_FRIEND_MESSAGE = 2;

    private UserChat owner;
    private UserChat friend;

    public ChatMessageAdapter(Context context, UserChat owner, UserChat friend) {
        super(context, false);
        this.owner = owner;
        this.friend = friend;
    }

    @Override
    protected RecyclerView.ViewHolder initLoadingViewHolder(ViewGroup parent) {
        View itemView = getInflater().inflate(R.layout.item_load_more, parent, false);
        return new LoadingViewHolder(itemView);
    }

    @Override
    protected void bindLoadingViewHolder(LoadingViewHolder loadingViewHolder, int position) {

    }

    @Override
    protected RecyclerView.ViewHolder solvedOnCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder result;
        switch (viewType) {
            case VIEW_TYPE_LOADING: {
                result = initLoadingViewHolder(parent);
            }
            break;

            case VIEW_TYPE_FRIEND_MESSAGE:{
                result = initFriendMessageViewHolder(parent);
            }
            break;

            default: {
                result = initNormalViewHolder(parent);
            }
            break;
        }
        return result;
    }

    private RecyclerView.ViewHolder initFriendMessageViewHolder(ViewGroup parent) {
        View itemView = getInflater().inflate(R.layout.item_friend_message, parent, false);
        return new FriendMessageViewHolder(itemView);
    }

    @Override
    protected RecyclerView.ViewHolder initNormalViewHolder(ViewGroup parent) {
        View itemView = getInflater().inflate(R.layout.item_owned_message, parent, false);
        return new OwnedMessageViewHolder(itemView);
    }

    @Override
    protected void solvedOnBindViewHolder(RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        switch (viewType) {
            case VIEW_TYPE_LOADING: {
                bindLoadingViewHolder((LoadingViewHolder) viewHolder, position);
            }
            break;

            case VIEW_TYPE_FRIEND_MESSAGE:{
                bindFriendMessageViewHolder((FriendMessageViewHolder) viewHolder, position);
            }
            break;

            default:{
                bindNormalViewHolder((NormalViewHolder) viewHolder, position);
            }
            break;
        }
    }

    private void bindFriendMessageViewHolder(FriendMessageViewHolder holder, int position) {
        Message message = getItem(position, Message.class);

        GlideApp.with(getContext())
                .load(friend.getAvatarUrl())
                .placeholder(R.drawable.logoapp)
                .into(holder.imgAvatar);

        holder.txtMessage.setText(message.getMessage());
        holder.txtTime.setText(Utils.getDateFromMilliseconds(message.getTimestamp().getTime()));

        if(message.getExpanded()) {
            holder.expandView(false);
        } else {
            holder.collapseView(false);
        }
    }

    @Override
    public void addModel(Object model, boolean isScroll) {
        Message message=(Message) model;
        if(UserAuth.getUserID(getContext()).equals(message.getOwner())) {
            addModel(model, VIEW_TYPE_NORMAL, isScroll, true);
        }else{
            addModel(model, VIEW_TYPE_FRIEND_MESSAGE, isScroll);
        }
    }

    @Override
    public void updateModel(int index, Object model) {
        Message message=(Message) model;
        if(UserAuth.getUserID(getContext()).equals(message.getOwner())) {
            updateModel(index, model, VIEW_TYPE_NORMAL);
        }else{
            updateModel(index, model, VIEW_TYPE_FRIEND_MESSAGE);
        }
    }

    @Override
    protected void bindNormalViewHolder(NormalViewHolder holder, int position) {
        Message message = getItem(position, Message.class);

        OwnedMessageViewHolder ownedMessageViewHolder = (OwnedMessageViewHolder) holder;

        ownedMessageViewHolder.txtMessage.setText(message.getMessage());
        if(message.getTimestamp()!=null) {
            ownedMessageViewHolder.txtTime.setText(Utils.getDateFromMilliseconds(message.getTimestamp().getTime()));
        }
        List<String> seenBy = message.getSeenBy();
        if(seenBy != null && seenBy.contains(friend.getEmail())) {
            ownedMessageViewHolder.txtSeen.setText(getContext().getString(R.string.seen_by)+" " + friend.getFirstName());
            if(position == getItemCount() - 1) {
                ownedMessageViewHolder.txtSeen.setVisibility(View.VISIBLE);
            }
        } else {
            ownedMessageViewHolder.txtSeen.setText("");
            ownedMessageViewHolder.txtSeen.setVisibility(View.GONE);
        }

        if(message.getExpanded()) {
            ownedMessageViewHolder.expandView(false);
        } else {
            ownedMessageViewHolder.collapseView(false);
        }
    }

    class OwnedMessageViewHolder extends NormalViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_message)
        TextView txtMessage;
        @BindView(R.id.txt_seen)
        TextView txtSeen;
        @BindView(R.id.txt_time)
        TextView txtTime;

        OwnedMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(txtSeen.getText().length() != 0) {
                Message message = getItem(getAdapterPosition(), Message.class);
                if(message.getExpanded()) {
                    collapseView(true);
                    message.setExpanded(false);
                } else {
                    expandView(true);
                    message.setExpanded(true);
                }
            }
        }

        void expandView(boolean animate) {
            if(animate) {
                TransitionManager.beginDelayedTransition((ViewGroup) itemView);
            }
            txtTime.setVisibility(View.VISIBLE);
            txtSeen.setVisibility(View.VISIBLE);
        }

        void collapseView(boolean animate) {
            if(animate) {
                TransitionManager.beginDelayedTransition((ViewGroup) itemView);
            }
            txtTime.setVisibility(View.GONE);
            txtSeen.setVisibility(View.GONE);
        }
    }

    class FriendMessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.txt_message)
        TextView txtMessage;
        @BindView(R.id.txt_time)
        TextView txtTime;

        FriendMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Message message = getItem(getAdapterPosition(), Message.class);
            if(message.getExpanded()) {
                collapseView(true);
                message.setExpanded(false);
            } else {
                expandView(true);
                message.setExpanded(true);
            }
        }

        void expandView(boolean animate) {
            if(animate) {
                TransitionManager.beginDelayedTransition((ViewGroup) itemView);
            }
            txtTime.setVisibility(View.VISIBLE);
        }

        void collapseView(boolean animate) {
            if(animate) {
                TransitionManager.beginDelayedTransition((ViewGroup) itemView);
            }
            txtTime.setVisibility(View.GONE);
        }
    }
}
