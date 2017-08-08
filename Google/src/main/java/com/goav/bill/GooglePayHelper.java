/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.goav.bill;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.annotation.StringRes;
import android.util.Log;

import com.android.vending.billing.IInAppBillingService;
import com.goav.bill.util.IabHelper;
import com.goav.bill.util.IabResult;
import com.goav.bill.util.Inventory;
import com.goav.bill.util.Purchase;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.AppIndexApi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static com.goav.bill.GooglePayHelper.PayState.query;
import static com.goav.bill.GooglePayHelper.PayState.support;
import static com.google.android.gms.common.ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED;
import static com.google.android.gms.common.ConnectionResult.SIGN_IN_FAILED;
import static com.google.android.gms.common.ConnectionResult.SIGN_IN_REQUIRED;
import static com.google.android.gms.common.ConnectionResult.TIMEOUT;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class GooglePayHelper implements
        IabHelper.OnIabSetupFinishedListener,
        IabHelper.QueryInventoryFinishedListener,
        IabHelper.OnIabPurchaseFinishedListener,
        IabHelper.OnConsumeFinishedListener {
    // SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    private static final int PAY_VERSION = 3;
    private static final int REQUEST_CODE = 1001;
    private static final int GOOGLE_PLAY_SERVICE_UPDATE_CODE = 1002;
    private static final String PAY_TYPE = "inapp";
    private final OnGooglePayResuleCallBack pay;
    private final boolean isCanPlay;

    private IInAppBillingService mIInAppBillingService;
    private ServiceConnection mServiceConnection;
    private Activity activity;
    private IabHelper helper;
    private boolean isCantIAB;
    private AlertDialog wrongDialog;


    public GooglePayHelper(final Activity activity, OnGooglePayResuleCallBack pay) {
        this.activity = activity;
        helper = new IabHelper(activity, pay.publicKey());
        this.pay = pay;
        helper.enableDebugLogging(true);

        GoogleApiAvailability googlePay = GoogleApiAvailability.getInstance();
        int state = googlePay.isGooglePlayServicesAvailable(activity);
        if (state == ConnectionResult.SUCCESS) {
            isCanPlay = true;
            GoogleApiClient mClient = new GoogleApiClient.Builder(activity)
                    .addApi(AppIndex.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {

                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            getWrongDialog(R.string.google_pay_tip_3).show();
                            isCantIAB = true;
                        }
                    }).build();
            mClient.connect();
        } else {
            isCanPlay = false;
            switch (state) {
                case SERVICE_VERSION_UPDATE_REQUIRED:
                case TIMEOUT:
                    error(activity, googlePay, state);
//                    getWrongDialog(R.string.google_pay_tip_4);
                    break;
            }
        }
    }

    private void error(final Activity activity, GoogleApiAvailability googlePay, int state) {
        Dialog dialog = googlePay.getErrorDialog(activity, state, GOOGLE_PLAY_SERVICE_UPDATE_CODE);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (activity != null)
                    activity.finish();
            }
        });
        dialog.show();
    }

    public void requestQuery() {
        if (isCanPlay) {
            helper.startSetup(this);
        }
    }

    private AlertDialog getWrongDialog(@StringRes int strRes) {
        if (wrongDialog == null) {
            wrongDialog = new AlertDialog.Builder(this.activity)
                    .setTitle(R.string.google_pay_wrong)
                    .setMessage(strRes)
                    .setPositiveButton(R.string.google_pay_tip_2, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
        }
        return wrongDialog;
    }

    private void cantSupport() {
        getWrongDialog(R.string.google_pay_tip_1).show();
    }

    private void cantConnect() {
        getWrongDialog(R.string.google_pay_tip_3).show();
    }

    /**
     * 预备支付
     *
     * @param purchase
     */
    public void requestPayFor(String sku, String extraData) {
        if (!isCanPlay) {
            cantSupport();
            return;
        }

        if (this.isCantIAB) {
            cantConnect();
            return;
        }
        try {
            helper.launchPurchaseFlow(
                    this.activity,
                    sku,
                    REQUEST_CODE,
                    this,
                    extraData
            );
        } catch (Exception e) {
            pay.onFail(new GooglePayException(support, new IabResult(-1, "Billing launchPurchase Unavailable")));
        }
    }

    /**
     * 支付结果回调
     */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            return helper.handleActivityResult(requestCode, resultCode, data);
        }
        return false;
    }

    public void onDestroy() {
        if (helper != null)
            helper.dispose();
    }

    @Override
    public void onIabSetupFinished(IabResult result) {
        //链接成功，
        if (result.isSuccess()) {
            //开始查询
            helper.queryInventoryAsync(this);
            this.isCantIAB = false;
        } else {
            this.isCantIAB = true;
            pay.onFail(createEx(support, result));
        }
    }

    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        //查询结果
        if (result.isFailure()) {
            pay.onFail(createEx(query, result));
            return;
        }

        List<Purchase> pur = inv.getAllPurchases();
        for (Purchase purchase : pur) {
            if (purchase != null && pay.verifyDeveloperPayload(purchase)) {
                Log.d("GooglePayHelper", "p=" + purchase.getOrderId() + " 2:" + purchase.getSku());
                helper.consumeAsync(purchase, this);
            }
        }
        pay.onSuccess(query, null);
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        //购买
        if (result.isSuccess() && pay.verifyDeveloperPayload(info)) {
            pay.onSuccess(PayState.pay, info);
            helper.consumeAsync(info, this);//消费
        } else {
//            购买失败
            pay.onFail(createEx(PayState.pay, result));
        }
    }

    @Override
    public void onConsumeFinished(Purchase purchase, IabResult result) {
        //消耗完成调用 接口
        if (result.isSuccess()) {
            pay.onSuccess(PayState.flush, purchase);//去服务器验证
        } else {
            pay.onFail(createEx(PayState.flush, result));
        }
    }

    @NonNull
    private GooglePayException createEx(@PayState String type, IabResult result) {
        return new GooglePayException(type, result);
    }


    public interface OnGooglePayResuleCallBack {
        String publicKey();

        void onSuccess(@PayState String state, Purchase purchase);

        void onFail(GooglePayException e);

        //监测是否有效
        boolean verifyDeveloperPayload(Purchase purchase);
    }

    @Retention(SOURCE)
    @Keep
    public
    @StringDef
    @interface PayState {
        String support = "support";
        String query = "query";
        String pay = "pay";
        String flush = "flush";
    }
}
