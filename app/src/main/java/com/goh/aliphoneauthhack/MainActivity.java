package com.goh.aliphoneauthhack;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.goh.aliphoneauthhack.utils.LogUtil;
import com.goh.aliphoneauthhack.utils.PermissionUtil;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    private PhoneNumberAuthHelper mPhoneNumberAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            PermissionUtil.checkAndRequestPermissions(this, 10001,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE);
        }
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) {
            login();
        }
    }

    private void login() {
        AliApplication application = new AliApplication(this);
        mPhoneNumberAuthHelper = PhoneNumberAuthHelper.getInstance(application, new TokenResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                LogUtil.i("TokenResultListener onTokenSuccess: " + s);
                TokenRet tokenRet = TokenRet.fromJson(s);
                switch (tokenRet.getCode()) {
                    case ResultCode.CODE_ERROR_ENV_CHECK_SUCCESS:
                        LogUtil.i("支持使用本机号码一键登录");
                        mPhoneNumberAuthHelper.getLoginToken(application, 5000);
                        break;
                    case ResultCode.CODE_START_AUTHPAGE_SUCCESS:
                        LogUtil.i("唤起授权页成功");
                        break;
                    case ResultCode.CODE_SUCCESS:
                        LogUtil.i("实际上并不会走这里回调");
                        break;
                }
            }

            @Override
            public void onTokenFailed(String s) {
                LogUtil.e("TokenResultListener onTokenFail: " + s);
                TokenRet tokenRet = TokenRet.fromJson(s);
                boolean allowPhoneAuthLogin = false;
                if (ResultCode.CODE_ERROR_USER_CANCEL.equals(tokenRet.getCode())) {     // 用户手动取消登录，允许从原始登录框切回来
                    allowPhoneAuthLogin = true;
                    LogUtil.i("用户取消登录");
                }
            }
        });
        mPhoneNumberAuthHelper.setAuthSDKInfo(AliParameter.AUTH_KEY);
        mPhoneNumberAuthHelper.getReporter().setLoggerEnable(true);
        mPhoneNumberAuthHelper.checkEnvAvailable(PhoneNumberAuthHelper.SERVICE_TYPE_AUTH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AliParameter.ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            LogUtil.i("登录完成");
            Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        }
    }
}