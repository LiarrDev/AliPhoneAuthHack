package com.mobile.auth.gatewayauth;

import android.app.Activity;
import android.util.Log;

import com.goh.aliphoneauthhack.utils.LogUtil;
import com.mobile.auth.gatewayauth.model.TokenRet;

public class MyTokenResultListener implements TokenResultListener {

    private final LoginAuthActivity mLoginAuthActivity;

    public MyTokenResultListener(LoginAuthActivity activity) {
        this.mLoginAuthActivity = activity;
    }

    @Override
    public void onTokenSuccess(String value) {
        LogUtil.i("onTokenSuccess: " + value);
        TokenRet tokenRet = TokenRet.fromJson(value);

        if (tokenRet.getCode().equals(ResultCode.CODE_SUCCESS)) {
            mLoginAuthActivity.setResult(Activity.RESULT_OK);
            mLoginAuthActivity.finish();
        }
    }

    @Override
    public void onTokenFailed(String value) {
        LogUtil.e("onTokenFailed: " + value);
        TokenRet tokenRet = TokenRet.fromJson(value);
        LogUtil.e("onTokenFailed TokenRet Msg: " + tokenRet.getMsg());
    }
}
