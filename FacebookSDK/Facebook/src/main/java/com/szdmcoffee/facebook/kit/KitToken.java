/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.facebook.kit;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.PhoneNumber;

import java.util.Date;

@SuppressWarnings("unchecked")
public class KitToken implements Parcelable {
    private final String accountId;
    private final String applicationId;
    private final Date lastRefresh;
    private final String token;
    private final long tokenRefreshIntervalInSeconds;
    private final String email;
    private final String id;
    private final PhoneNumber phoneNumber;
    public static final Creator<KitToken> CREATOR = new Creator() {
        public KitToken createFromParcel(Parcel source) {
            return new KitToken(source);
        }

        public KitToken[] newArray(int size) {
            return new KitToken[size];
        }
    };

    public String getApplicationId() {
        return applicationId;
    }

    public String getAccountId() {
        return accountId;
    }

    public KitToken(Parcel source) {
        this.accountId = source.readString();
        this.applicationId = source.readString();
        this.lastRefresh = new Date(source.readLong());
        this.token = source.readString();
        this.tokenRefreshIntervalInSeconds = source.readLong();
        this.email = source.readString();
        this.id = source.readString();
        this.phoneNumber = source.readParcelable(PhoneNumber.class.getClassLoader());
    }

    public KitToken(AccessToken token, Account account) {
        this.accountId = token.getAccountId();
        this.applicationId = token.getApplicationId();
        this.lastRefresh = token.getLastRefresh();
        this.token = token.getToken();
        this.tokenRefreshIntervalInSeconds = token.getTokenRefreshIntervalSeconds();
        this.email = account.getEmail();
        this.id = account.getId();
        this.phoneNumber = account.getPhoneNumber();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountId);
        dest.writeString(this.applicationId);
        dest.writeLong(this.lastRefresh.getTime());
        dest.writeString(this.token);
        dest.writeLong(this.tokenRefreshIntervalInSeconds);
        dest.writeString(this.email);
        dest.writeString(this.id);
        dest.writeParcelable(this.phoneNumber, flags);
    }
}
