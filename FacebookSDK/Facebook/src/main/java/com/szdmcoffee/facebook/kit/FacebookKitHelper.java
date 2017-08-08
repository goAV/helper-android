/*
 * Copyright (c) 2017.
 * By chinaume@163.com
 */

package com.szdmcoffee.facebook.kit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.szdmcoffee.facebook.Constants;
import com.szdmcoffee.facebook.R;

public class FacebookKitHelper {
    private final static int APP_REQUEST_CODE = 1001;

    private FacebookKitHelper() {
    }

    private static class FacebookKit {
        private static FacebookKitHelper kitHelper = new FacebookKitHelper();
    }


    public static FacebookKitHelper instance() {
        return FacebookKit.kitHelper;
    }

    public void startSMSRegister(Activity activity) {
        startRegister(activity, LoginType.PHONE);
    }


    public void startEMailRegister(Activity activity) {
        startRegister(activity, LoginType.EMAIL);
    }


    public void startRegister(Activity activity, LoginType type) {
        startRegister(activity, type, AccountKitActivity.ResponseType.TOKEN);
    }

    public void logout() {
        AccountKit.logOut();
    }

    public boolean getCurrentAccessToken(OnFacebookKitCallBack callBack) {
        AccessToken token = AccountKit.getCurrentAccessToken();
        if (token != null) {
            getCurrentAccount(token, callBack);
            return true;
        }
        return false;
    }

    public void startRegister(Activity activity, LoginType type, AccountKitActivity.ResponseType responseType) {
        final Intent intent = new Intent(activity, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        type,
                        //token 为缓存获取授权信息
                        responseType// or .ResponseType.TOKEN
                );
        configurationBuilder.setDefaultCountryCode(getLocalContryCodeIos(activity));
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build()
        );
        ActivityCompat.startActivityForResult(activity, intent, APP_REQUEST_CODE, null);
    }

    public String getLocalContryCodeIos(Activity activity) {
        String[] arrays = activity.getResources().getStringArray(R.array.com_accountkit_phone_country_codes);
        String id = ((TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE)).getSimCountryIso().toUpperCase();
        if (!TextUtils.isEmpty(id)) {
            Log.d(Constants.tag("kit"), id);
            if (arrays != null && arrays.length > 0) {
                for (int i = 0; i < arrays.length; i++) {
                    String[] split = arrays[i].split(":");
                    if (split[1].equals(id)) {
                        return split[0];
                    }
                }
            }
        }
        return "86";
    }

    private void getCurrentAccount(final AccessToken token, final OnFacebookKitCallBack callBack) {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                String accountKitId = account.getId();
                PhoneNumber phoneNumber = account.getPhoneNumber();
                String phoneNumberString = phoneNumber.toString();
//                        String email = account.getEmail();
                if (callBack != null) {
                    callBack.onSuccess(new KitToken(token, account));
                }
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                callBack.onFaile(accountKitError);
            }
        });
    }

    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data, final OnFacebookKitCallBack callBack) {
        if (requestCode == APP_REQUEST_CODE && resultCode == Activity.RESULT_OK && callBack != null) {
            AccountKitLoginResult loginResult = /*data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);*/
                    AccountKit.loginResultWithIntent(data);
            if (loginResult == null) {
                callBack.onCancle();
                return;
            }
            String toastMessage = "";
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                callBack.onFaile(loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
                callBack.onCancle();
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                }
                getCurrentAccount(loginResult.getAccessToken(), callBack);
            }

            Log.d(Constants.tag("kit"), toastMessage);
        }
    }


    public interface OnFacebookKitCallBack {
        void onSuccess(KitToken loginResult);

        void onCancle();

        void onFaile(AccountKitError error);
    }


}
