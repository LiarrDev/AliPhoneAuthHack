package com.mobile.auth.gatewayauth;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class MyClickableSpan extends ClickableSpan {

    final /* synthetic */ LoginAuthActivity d;
    String str, str2;
    int i;

    public MyClickableSpan(LoginAuthActivity d, String str, String str2, int i) {
        this.d = d;
        this.str = str;
        this.str2 = str2;
        this.i = i;
    }

    @Override
    public void onClick(View view) {
        try {
            LoginAuthActivity.access$200(this.d).a(LoginAuthActivity.access$100(this.d), str, str2, true);
            LoginAuthActivity.access$200(this.d).a(str, str2);
        } catch (Throwable th) {
        }
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        try {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(false);
            textPaint.setColor(i);
        } catch (Throwable th) {
        }
    }
}
