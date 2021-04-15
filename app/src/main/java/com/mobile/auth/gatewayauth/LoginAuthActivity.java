package com.mobile.auth.gatewayauth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goh.aliphoneauthhack.R;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.ui.b;
import com.mobile.auth.gatewayauth.utils.i;
import com.mobile.auth.o.a;
import com.nirvana.tools.core.AppUtils;

import java.util.ArrayList;
import java.util.List;

@AuthNumber
public class LoginAuthActivity extends Activity {
    public static final String EXIST = "exist";
    public static final String STOP_LOADING = "stop_loading";
    private RelativeLayout mMainRelativeLayout;
    private RelativeLayout mTitleRL;
    private RelativeLayout mTitleDYVRL;
    private RelativeLayout mBodyRL;
    private RelativeLayout mBodyDYVRL;
    private ImageView mLogoIV;
    private RelativeLayout mNumberRL;
    private RelativeLayout mNumberDYVRL;
    private TextView mMaskNumberTV;
    private RelativeLayout mLoginRL;
    private TextView mLoginTV;
    private RelativeLayout mProtocolRL;
    private TextView mProtocolTV;
    private TextView mSwitchTV;
    private TextView mSloganTV;
    private CheckBox mProtocolSelectCB;
    private com.mobile.auth.w.a mProgressDialog;
    private String mNumberPhone;
    private int mUIManagerID;
    private String mAccessCode;
    private String mSlogan;
    private String mVendorProtocol;
    private String mProtocol;
    private String mVendorClick;
    private long startTime;
    private d mUIManager;
    private AuthUIConfig mUIConfig;
    private boolean mIsDialog = false;
    private com.mobile.auth.o.a mPnsLogger;
    private List<com.mobile.auth.gatewayauth.ui.b> mProtocolConfigs = new ArrayList<>(3);
    private String mVendorKey;

    static RelativeLayout access$000(LoginAuthActivity loginAuthActivity) {
        return loginAuthActivity.mLoginRL;
    }

    static /* synthetic */ String access$100(LoginAuthActivity loginAuthActivity) {
        return loginAuthActivity.mVendorKey;
    }

    static /* synthetic */ d access$200(LoginAuthActivity loginAuthActivity) {
        return loginAuthActivity.mUIManager;
    }

    static /* synthetic */ void access$300(LoginAuthActivity loginAuthActivity, boolean z, String str, String str2) {
        loginAuthActivity.finishAuthPage(z, str, str2);
    }

    static /* synthetic */ boolean access$400(LoginAuthActivity loginAuthActivity) {
        return true;
    }

    static /* synthetic */ AuthUIConfig access$500(LoginAuthActivity loginAuthActivity) {
        return loginAuthActivity.mUIConfig;
    }

    static /* synthetic */ CheckBox access$600(LoginAuthActivity loginAuthActivity) {
        return loginAuthActivity.mProtocolSelectCB;
    }

    static /* synthetic */ a access$700(LoginAuthActivity loginAuthActivity) {
        return loginAuthActivity.mPnsLogger;
    }

    private boolean checkAuthPageUILegal() {
        try {
            return (i.a(this.mProtocolTV) || i.a(this.mLoginTV) || i.a(this.mMaskNumberTV) || this.mLoginTV == null || i.a(this.mLoginTV.getCurrentTextColor()) || this.mProtocolTV == null || i.a(this.mProtocolTV.getCurrentTextColor()) || this.mMaskNumberTV == null || i.a(this.mMaskNumberTV.getCurrentTextColor())) ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private SpannableString dealProtocol(String str, List<com.mobile.auth.gatewayauth.ui.b> list) {
        try {
            SpannableString spannableString = new SpannableString(str);
            ClickableSpan vendorProtocol = getVendorProtocol(this.mVendorProtocol, this.mVendorClick, this.mUIConfig.getProtocolOneColor());
            int indexOf = str.indexOf(this.mVendorProtocol) + this.mVendorProtocol.length();
            int i = indexOf;
            for (b next : list) {
                ClickableSpan protocol = getProtocol(next.b(), next.c(), next.d());
                i = str.indexOf(next.b(), i);
                indexOf = next.b().length() + i;
                spannableString.setSpan(protocol, i, indexOf, 34);
                i = indexOf;
            }
            spannableString.setSpan(vendorProtocol, str.indexOf(this.mVendorProtocol), str.indexOf(this.mVendorProtocol) + this.mVendorProtocol.length(), 34);
            return spannableString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void finishAuthPage(boolean z, String str, String str2) {
        try {
            if (this.mUIManager != null) {
                this.mUIManager.a(z, str, str2);
            } else {
                this.mPnsLogger.d("Exception finish!");
                finish();
            }
            if (this.mUIConfig.getAuthPageActOut() != null && this.mUIConfig.getActivityIn() != null) {
                overridePendingTransition(AppUtils.getAnimResID(this, this.mUIConfig.getAuthPageActOut()), AppUtils.getAnimResID(this, this.mUIConfig.getActivityIn()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private native ClickableSpan getProtocol(String str, String str2, int i);

    private ClickableSpan getVendorProtocol(final String str, final String str2, final int i) {
        return new MyClickableSpan(this, str, str2, i);
    }

    @SafeProtector
    private native void init();

    @SafeProtector
    private native RelativeLayout initBodyView();

    @SafeProtector
    private native void initDynamicView();

    @SafeProtector
    private void initIntentData() {
        Intent intent = getIntent();
        this.mNumberPhone = intent.getStringExtra(Constant.LOGIN_ACTIVITY_NUMBER);
        this.mVendorKey = intent.getStringExtra(Constant.LOGIN_ACTIVITY_VENDOR_KEY);
        this.mAccessCode = intent.getStringExtra(Constant.LOGIN_ACTIVITY_ACCESSCODE);
        this.startTime = intent.getLongExtra(Constant.START_TIME, 0);
        this.mUIManagerID = intent.getIntExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, 0);
    }

    @SafeProtector
    private native RelativeLayout initLoginRL();

    @SafeProtector
    private native ImageView initLogoView();

    @SafeProtector
    private native void initMaskNumberDynamicView();

    @SafeProtector
    private native RelativeLayout initNumberView();

    @SafeProtector
    private native RelativeLayout initProtocolView();

    @SafeProtector
    private native TextView initSloganView();

    @SafeProtector
    private native TextView initSwitchView();

    @SafeProtector
    private native RelativeLayout initTitleView();

    @SafeProtector
    public native void initView();

    @SafeProtector
    private native void initXMLDynamicView();

    @SafeProtector
    private native void removeDynamicView();

    @SafeProtector
    private native void removeNumberView();

    private void setBackground(View view, Drawable drawable) {
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDialogBackGroundAlpha(float f) {
        try {
            getWindow().setDimAmount(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xmlLoadErrorCB() {
        try {
            if (this.mUIManager != null) {
                this.mUIManager.e();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getUIManagerID() {
        try {
            return this.mUIManagerID;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void hideLoadingDialog() {
        try {
            if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
                this.mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SafeProtector
    public native void onActivityResult(int i, int i2, Intent intent);

    public void onBackPressed() {
        try {
            super.onBackPressed();
            finishAuthPage(true, Constant.CODE_ERROR_USER_CANCEL, Constant.MSG_ERROR_USER_CANCEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public native void onCreate(Bundle bundle);

    public void onDestroy() {
        try {
            hideLoadingDialog();
            removeDynamicView();
            removeNumberView();
            if (this.mPnsLogger != null) {
                this.mPnsLogger.a();
            }
            this.mUIManager = null;
            this.mUIConfig = null;
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();

        /* 自定义界面要用这里 ----- START */
        Window window = getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        mMainRelativeLayout.setVisibility(View.GONE);

        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_login_auth, null);
        viewGroup.addView(view);
        TextView tvPhone = (TextView) view.findViewById(R.id.tv_phone);
        tvPhone.setText(mNumberPhone);
        view.findViewById(R.id.btn_auth).setOnClickListener(v -> {
            onLogin();
        });
        /* 自定义界面要用这里 ----- END */
    }

    public void onLogin() {
        boolean access$400 = LoginAuthActivity.access$400(this);
        LoginAuthActivity.access$200(this).a(LoginAuthActivity.access$100(this), true, access$400);
        showLoadingDialog();
        LoginAuthActivity.access$700(this).a("LoginAuthActivity", "; PhoneNumberAuthHelper2 = ", String.valueOf(LoginAuthActivity.access$200(this)));
        LoginAuthActivity.access$200(this).b(LoginAuthActivity.access$200(this).a());
    }

    public void showLoadingDialog() {
        LoginAuthActivity.access$000(LoginAuthActivity.this).setClickable(true);

        access$200(this).a((TokenResultListener) new MyTokenResultListener(this));

    }
}
