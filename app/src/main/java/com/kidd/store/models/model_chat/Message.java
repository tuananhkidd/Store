package com.kidd.store.models.model_chat;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@IgnoreExtraProperties
public class Message implements Serializable, Parcelable {
    public static final String SEEN_BY = "seenBy";
    private String owner;
    private String message;

    private Date mCreatedDate;

    private List<String> seenBy;

    private boolean expanded;

    public Message(String owner, String message) {
        this.owner = owner;
        this.message = message;
        seenBy= new ArrayList<>();
    }

    public Message() {
    }

    protected Message(Parcel in) {
        owner = in.readString();
        message = in.readString();
        expanded = in.readByte() != 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @ServerTimestamp
    public Date getTimestamp() {
        return mCreatedDate;
    }


    public void setTimestamp(Date createdDate) {
        this.mCreatedDate = createdDate;
    }

    public List<String> getSeenBy() {
        return seenBy;
    }

    public void setSeenBy(List<String> seenBy) {
        this.seenBy = seenBy;
    }

    public boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        return "Message{" +
                "owner='" + owner + '\'' +
                ", message='" + message + '\'' +
                ", mCreatedDate=" + mCreatedDate +
                ", seenBy=" + seenBy +
                ", isExpanded=" + expanded +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message chat = (Message) o;

        return mCreatedDate.equals(chat.mCreatedDate)
                && owner.equals(chat.owner)
                && (message == null ? chat.message == null : message.equals(chat.message));
    }

    @Override
    public int hashCode() {
        int result = owner == null ? 0 : owner.hashCode();
        result = 3 * result + (message == null ? 0 : message.hashCode());
        result = 31 * result + mCreatedDate.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(owner);
        dest.writeString(message);
        dest.writeSerializable(mCreatedDate);
        dest.writeStringList( seenBy);
        dest.writeByte((byte) (expanded ? 1 : 0));
    }
}
