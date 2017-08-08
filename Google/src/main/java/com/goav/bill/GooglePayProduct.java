/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.bill;

import android.os.Parcel;
import android.os.Parcelable;

public class GooglePayProduct implements Parcelable {

    /**
     * {
            "orderId":"GPA.1234-5678-9012-34567",
            "packageName":"com.example.app",
            "productId":"exampleSku",
            "purchaseTime":1345678900000,
            "purchaseState":0,
            "developerPayload":"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ",
            "purchaseToken":"opaque-token-up-to-1000-characters"
    }*/
    /**
     * premiumUpgrade,gas
     */
    private String productId;
    private String price;
    private String orderId;
    private String packageName;
    private long purchaseTime;
    private int purchaseState;
    private String developerPayload;
    private String purchaseToken;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.price);
        dest.writeString(this.orderId);
        dest.writeString(this.packageName);
        dest.writeLong(this.purchaseTime);
        dest.writeInt(this.purchaseState);
        dest.writeString(this.developerPayload);
        dest.writeString(this.purchaseToken);
    }

    public GooglePayProduct() {
    }

    protected GooglePayProduct(Parcel in) {
        this.productId = in.readString();
        this.price = in.readString();
        this.orderId = in.readString();
        this.packageName = in.readString();
        this.purchaseTime = in.readLong();
        this.purchaseState = in.readInt();
        this.developerPayload = in.readString();
        this.purchaseToken = in.readString();
    }

    public static final Creator<GooglePayProduct> CREATOR = new Creator<GooglePayProduct>() {
        @Override
        public GooglePayProduct createFromParcel(Parcel source) {
            return new GooglePayProduct(source);
        }

        @Override
        public GooglePayProduct[] newArray(int size) {
            return new GooglePayProduct[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public String getPrice() {
        return price;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPackageName() {
        return packageName;
    }

    public long getPurchaseTime() {
        return purchaseTime;
    }

    public int getPurchaseState() {
        return purchaseState;
    }

    public String getDeveloperPayload() {
        return developerPayload;
    }

    public String getPurchaseToken() {
        return purchaseToken;
    }
}
