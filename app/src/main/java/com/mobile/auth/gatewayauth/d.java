package com.mobile.auth.gatewayauth;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.goh.aliphoneauthhack.AliParameter;
import com.mobile.auth.gatewayauth.activity.AuthWebVeiwActivity;
import com.mobile.auth.gatewayauth.manager.SystemManager;
import com.mobile.auth.gatewayauth.manager.compat.ResultCodeProcessor;
import com.mobile.auth.gatewayauth.manager.compat.b;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.mobile.auth.gatewayauth.model.UStruct;
import com.mobile.auth.gatewayauth.utils.ReflectionUtils;
import com.nirvana.tools.core.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class d {
    private static final ConcurrentHashMap<Integer, com.mobile.auth.gatewayauth.d> b = new ConcurrentHashMap(5);
    private Context c;
    private AuthUIControlClickListener d;
    private volatile WeakReference<Activity> e;
    private AuthUIConfig f;
    private LinkedHashMap<String, AuthRegisterViewConfig> g;
    private ArrayList<AuthRegisterXmlConfig> h;
    private ArrayList<Object> i;
    public static final AuthUIConfig a = (new AuthUIConfig.Builder()).create();
    private int j;
    private com.mobile.auth.o.a k;
    private WeakReference<Activity> l;
    private TokenResultListener m;
    private ActivityResultListener n;
    private PhoneNumberAuthHelper o;
    private SystemManager p;
    private ResultCodeProcessor q;
    private com.mobile.auth.gatewayauth.manager.d r;
    private long s;
    private long t;
    private volatile boolean u = false;

    private Application.ActivityLifecycleCallbacks v = new Application.ActivityLifecycleCallbacks() {
        public void onActivityCreated(Activity var1, Bundle var2) {
            try {
                if (var1 instanceof LoginAuthActivity) {
                    Intent var3 = var1.getIntent();
                    if (var3 != null && var3.getIntExtra("ui_manager_id", -1) == com.mobile.auth.gatewayauth.d.a(com.mobile.auth.gatewayauth.d.this)) {
                        com.mobile.auth.gatewayauth.d.a(com.mobile.auth.gatewayauth.d.this, new WeakReference(var1));
                    }
                }
            } catch (Throwable var4) {
                com.mobile.auth.gatewayauth.a.a(var4);
            }

        }

        public void onActivityStarted(Activity var1) {
        }

        public void onActivityResumed(Activity var1) {
            try {
                if (var1 instanceof LoginAuthActivity && ((LoginAuthActivity) var1).getUIManagerID() == com.mobile.auth.gatewayauth.d.a(com.mobile.auth.gatewayauth.d.this)) {
                    com.mobile.auth.gatewayauth.d.a(com.mobile.auth.gatewayauth.d.this, true);
                }
            } catch (Throwable var2) {
                com.mobile.auth.gatewayauth.a.a(var2);
            }

        }

        public void onActivityPaused(Activity var1) {
            try {
                if (var1 instanceof LoginAuthActivity && ((LoginAuthActivity) var1).getUIManagerID() == com.mobile.auth.gatewayauth.d.a(com.mobile.auth.gatewayauth.d.this)) {
                    com.mobile.auth.gatewayauth.d.a(com.mobile.auth.gatewayauth.d.this, false);
                }
            } catch (Throwable var2) {
                com.mobile.auth.gatewayauth.a.a(var2);
            }

        }

        public void onActivityStopped(Activity var1) {
        }

        public void onActivitySaveInstanceState(Activity var1, Bundle var2) {
        }

        public void onActivityDestroyed(Activity var1) {
            try {
                Activity var2 = null;
                if (var1 instanceof LoginAuthActivity && ((LoginAuthActivity) var1).getUIManagerID() == com.mobile.auth.gatewayauth.d.a(com.mobile.auth.gatewayauth.d.this) && com.mobile.auth.gatewayauth.d.b(com.mobile.auth.gatewayauth.d.this) != null && (var2 = (Activity) com.mobile.auth.gatewayauth.d.b(com.mobile.auth.gatewayauth.d.this).get()) != null && var2 == var1) {
                    Application var3 = ReflectionUtils.getApplication();
                    if (var3 != null) {
                        var3.unregisterActivityLifecycleCallbacks(com.mobile.auth.gatewayauth.d.c(com.mobile.auth.gatewayauth.d.this));
                    }

                    com.mobile.auth.gatewayauth.d.a((com.mobile.auth.gatewayauth.d) com.mobile.auth.gatewayauth.d.this, (WeakReference) null);
                }
            } catch (Throwable var4) {
                com.mobile.auth.gatewayauth.a.a(var4);
            }

        }
    };

    static /* synthetic */ WeakReference b(com.mobile.auth.gatewayauth.d dVar) {
        try {
            return dVar.l;
        } catch (Throwable th) {
            return null;
        }
    }

    static /* synthetic */ WeakReference a(com.mobile.auth.gatewayauth.d dVar, WeakReference weakReference) {
        try {
            dVar.l = weakReference;
            return weakReference;
        } catch (Throwable th) {
            return null;
        }
    }

    static /* synthetic */ int a(com.mobile.auth.gatewayauth.d dVar) {
        try {
            return dVar.j;
        } catch (Throwable th) {
            return -1;
        }
    }

    static /* synthetic */ boolean a(com.mobile.auth.gatewayauth.d dVar, boolean z) {
        try {
            dVar.u = z;
            return z;
        } catch (Throwable th) {

            return false;
        }
    }

    public static com.mobile.auth.gatewayauth.d a(int i) {
        try {
            return (com.mobile.auth.gatewayauth.d) b.get(Integer.valueOf(i));
        } catch (Throwable th) {
            return null;
        }
    }


    static /* synthetic */ Application.ActivityLifecycleCallbacks c(com.mobile.auth.gatewayauth.d dVar) {
        try {
            return dVar.v;
        } catch (Throwable th) {
            return null;
        }
    }

    public d(Context var1, com.mobile.auth.gatewayauth.manager.d var2, SystemManager var3, PhoneNumberAuthHelper var4) {
        this.c = var1.getApplicationContext();
        this.r = var2;
        this.k = var2.a();
        this.p = var3;
        this.o = var4;
        this.j = this.hashCode();
    }

    public void a(ResultCodeProcessor var1) {
        try {
            this.q = var1;
        } catch (Throwable var2) {
            com.mobile.auth.gatewayauth.a.a(var2);
        }
    }

    public void a(long var1, String var3, String var4, ResultCodeProcessor var5, com.mobile.auth.gatewayauth.e var6) {
        try {
            this.t = var1;
            Application var7 = ReflectionUtils.getApplication();
            if (var7 != null) {
                var7.registerActivityLifecycleCallbacks(this.v);
            }

            Activity activity = ReflectionUtils.getActivity();
            Intent intent = new Intent(activity, LoginAuthActivity.class);
            intent.putExtra("isAli", true);
            intent.putExtra("number", var3);
            intent.putExtra("vendor", var4);
            intent.putExtra("ui_manager_id", this.j);
            intent.putExtra("startTime", System.currentTimeMillis());
            this.a(var5);
            b.put(this.j, this);

            activity.startActivityForResult(intent, AliParameter.ACTIVITY_REQUEST_CODE);

            if (var6 != null) {
                var6.a(var4, var3);
            }
        } catch (Throwable var15) {
            com.mobile.auth.gatewayauth.a.a(var15);
        }

    }

    public AuthUIConfig f() {
        AuthUIConfig var10000;
        try {
            if (this.f == null) {
                return a;
            }

            var10000 = this.f;
        } catch (Throwable var1) {
            com.mobile.auth.gatewayauth.a.a(var1);
            return null;
        }

        return var10000;
    }

    public void l() {
        try {
            b.remove(this.j);
        } catch (Throwable var1) {
            com.mobile.auth.gatewayauth.a.a(var1);
        }

    }

    public void a(TokenResultListener var1) {
        try {
            this.m = var1;
        } catch (Throwable var2) {
            com.mobile.auth.gatewayauth.a.a(var2);
        }

    }

    public void a(Activity var1) {
        try {
            this.e = new WeakReference(var1);
        } catch (Throwable var2) {
            com.mobile.auth.gatewayauth.a.a(var2);
        }

    }

    public com.mobile.auth.o.a m() {
        try {
            return this.k;
        } catch (Throwable th) {
            return null;
        }
    }

    public void a(long var1) {
        try {
            this.s = var1;
        } catch (Throwable var3) {
            com.mobile.auth.gatewayauth.a.a(var3);
        }

    }

    public static void a(AuthUIConfig authUIConfig, Activity activity) {
        try {
            if (authUIConfig.isStatusBarHidden()) {
                com.mobile.auth.gatewayauth.utils.i.a(activity);
            } else {
                com.mobile.auth.gatewayauth.utils.i.c(activity, authUIConfig.getStatusBarUIFlag());
            }
            com.mobile.auth.gatewayauth.utils.i.a(activity, authUIConfig.getStatusBarColor());
            com.mobile.auth.gatewayauth.utils.i.a(activity, authUIConfig.isLightColor());
            com.mobile.auth.gatewayauth.utils.i.b(activity, authUIConfig.getBottomNavBarColor());
        } catch (Throwable th) {
        }
    }

    public LinkedHashMap<String, AuthRegisterViewConfig> g() {
        try {
            if (this.g == null) {
                this.g = new LinkedHashMap();
            }

            return this.g;
        } catch (Throwable var1) {
            com.mobile.auth.gatewayauth.a.a(var1);
            return null;
        }
    }

    public ArrayList<AuthRegisterXmlConfig> h() {
        try {
            if (this.h == null) {
                this.h = new ArrayList();
            }

            return this.h;
        } catch (Throwable var1) {
            com.mobile.auth.gatewayauth.a.a(var1);
            return null;
        }
    }

    static /* synthetic */ com.mobile.auth.o.a d(com.mobile.auth.gatewayauth.d dVar) {
        try {
            return dVar.k;
        } catch (Throwable th) {
            return null;
        }
    }

    static /* synthetic */ SystemManager g(com.mobile.auth.gatewayauth.d dVar) {
        try {
            return dVar.p;
        } catch (Throwable th) {
            return null;
        }
    }

    static /* synthetic */ com.mobile.auth.gatewayauth.manager.d h(com.mobile.auth.gatewayauth.d dVar) {
        try {
            return dVar.r;
        } catch (Throwable th) {
            return null;
        }
    }

    static /* synthetic */ long j(com.mobile.auth.gatewayauth.d dVar) {
        try {
            return dVar.s;
        } catch (Throwable th) {
            return -1;
        }
    }

    static /* synthetic */ ResultCodeProcessor i(com.mobile.auth.gatewayauth.d dVar) {
        try {
            return dVar.q;
        } catch (Throwable th) {
            return null;
        }
    }

    public void a(String str, long j, boolean z, boolean z2) {
        try {
            final String str2 = str;
            final boolean z3 = z2;
            final boolean z4 = z;
            final long j2 = j;
            com.mobile.auth.gatewayauth.utils.b.a(new Runnable() {
                final /* synthetic */ com.mobile.auth.gatewayauth.d e = com.mobile.auth.gatewayauth.d.this;

                public void run() {
                    try {
                        ResultCodeProcessor i;
                        String str;
                        com.mobile.auth.o.a d = com.mobile.auth.gatewayauth.d.d(this.e);
                        com.mobile.auth.gatewayauth.manager.d h = com.mobile.auth.gatewayauth.d.h(this.e);
                        String l = com.mobile.auth.gatewayauth.d.g(this.e).l(str2);
                        boolean z = false;
                        UStruct.Builder isVertical = UStruct.newUStruct()
                                .isSuccess(z3)
                                .isFullScreen(String.valueOf(!this.e.f().isDialog())).isVertical(String.valueOf(z4));
                        if (this.e.f().isCheckboxHidden() || this.e.f().isPrivacyState()) {
                            z = true;
                        }
                        isVertical = isVertical.isChecked(String.valueOf(z))
                                .isCheckboxHidden(String.valueOf(this.e.f().isCheckboxHidden()))
                                .requestId(com.mobile.auth.gatewayauth.d.h(this.e).f())
                                .sessionId(com.mobile.auth.gatewayauth.d.h(this.e).d()).startTime(j2)
                                .endTime(com.mobile.auth.gatewayauth.d.j(this.e));
                        if (z3) {
                            i = com.mobile.auth.gatewayauth.d.i(this.e);
                            str = Constant.CODE_START_AUTHPAGE_SUCCESS;
                        } else {
                            i = com.mobile.auth.gatewayauth.d.i(this.e);
                            str = Constant.CODE_ERROR_START_AUTHPAGE_FAIL;
                        }
                        d.a(h.b(str2, l, isVertical.authSdkCode(i.convertCode(str)).build(), ""), 1);
                    } catch (Throwable th) {

                    }
                }
            });
        } catch (Throwable th) {

        }
    }

    public void a(AuthUIConfig var1) {
        try {
            this.f = var1;
        } catch (Throwable var2) {
            com.mobile.auth.gatewayauth.a.a(var2);
        }

    }

    public void k() {
        try {
            try {
                if (this.h != null) {
                    if (this.i != null) {
                        this.i.removeAll(this.h);
                    }

                    this.h.clear();
                    this.h = null;
                }
            } catch (Exception var2) {
                com.mobile.auth.gatewayauth.utils.f.c(com.mobile.auth.gatewayauth.utils.b.b(var2));
                com.mobile.auth.gatewayauth.utils.f.c("动态控件清除失败");
            }
        } catch (Throwable var3) {
            com.mobile.auth.gatewayauth.a.a(var3);
        }

    }

    public void a(AuthRegisterXmlConfig var1) {
        try {
            try {
                if (null == this.h) {
                    this.h = new ArrayList();
                }

                this.h.add(var1);
                this.a((Object) var1);
            } catch (Exception var3) {
                com.mobile.auth.gatewayauth.utils.f.c(com.mobile.auth.gatewayauth.utils.b.b(var3));
                com.mobile.auth.gatewayauth.utils.f.c("动态添加控件失败");
            }
        } catch (Throwable var4) {
            com.mobile.auth.gatewayauth.a.a(var4);
        }
    }

    public void a(Object var1) {
        try {
            if (null == this.i) {
                this.i = new ArrayList();
            }

            this.i.add(var1);
        } catch (Throwable var2) {
            com.mobile.auth.gatewayauth.a.a(var2);
        }

    }

    public void j() {
        try {
            try {
                if (this.g != null) {
                    if (this.i != null) {
                        this.i.removeAll(this.g.values());
                    }

                    this.g.clear();
                    this.g = null;
                }
            } catch (Exception var2) {
                com.mobile.auth.gatewayauth.utils.f.c(com.mobile.auth.gatewayauth.utils.b.b(var2));
                com.mobile.auth.gatewayauth.utils.f.c("动态控件清除失败");
            }
        } catch (Throwable var3) {
            com.mobile.auth.gatewayauth.a.a(var3);
        }

    }

    public void a(String var1, boolean var2, boolean var3) {
        try {
            if (this.d != null) {
                JSONObject var4 = new JSONObject();

                try {
                    var4.put("isChecked", var2);
                } catch (JSONException var6) {
                    var6.printStackTrace();
                }

                this.d.onClick("700002", this.c, var4.toString());
            }

            this.a(var1, this.p.h(var1), var3);
        } catch (Throwable var7) {
            com.mobile.auth.gatewayauth.a.a(var7);
        }

    }

    private void a(final String var1, final String var2, final boolean var3) {
        try {
            com.mobile.auth.gatewayauth.utils.b.a(new Runnable() {
                public void run() {
                    try {
                        com.mobile.auth.gatewayauth.d.d(com.mobile.auth.gatewayauth.d.this)
                                .a(com.mobile.auth.gatewayauth.d.h(com.mobile.auth.gatewayauth.d.this)
                                        .b(var1, var2, UStruct.newUStruct().startTime(com.mobile.auth.gatewayauth.d.j(com.mobile.auth.gatewayauth.d.this))
                                                .endTime(System.currentTimeMillis()).requestId(com.mobile.auth.gatewayauth.d.h(com.mobile.auth.gatewayauth.d.this).f())
                                                .sessionId(com.mobile.auth.gatewayauth.d.h(com.mobile.auth.gatewayauth.d.this).d())
                                                .authSdkCode("700002")
                                                .isAuthPageLegal(String.valueOf(var3)).build(), ""), 2);
                    } catch (Throwable var1x) {
                        com.mobile.auth.gatewayauth.a.a(var1x);
                    }

                }
            });
        } catch (Throwable var4) {
            com.mobile.auth.gatewayauth.a.a(var4);
        }

    }

    public ResultCodeProcessor a() {
        try {
            if (this.q == null) {
                this.q = new b();
            }

            return this.q;
        } catch (Throwable var1) {
            com.mobile.auth.gatewayauth.a.a(var1);
            return null;
        }
    }

    public void b(ResultCodeProcessor var1) {
        try {
            this.o.a(this.t, new TokenResultListener() {
                public void onTokenSuccess(String var1) {
                    try {
                        if (com.mobile.auth.gatewayauth.d.f(com.mobile.auth.gatewayauth.d.this) != null) {
                            com.mobile.auth.gatewayauth.d.f(com.mobile.auth.gatewayauth.d.this).onTokenSuccess(var1);
                        }
                    } catch (Throwable var2) {
                        com.mobile.auth.gatewayauth.a.a(var2);
                    }

                }

                public void onTokenFailed(String var1) {
                    try {
                        if (com.mobile.auth.gatewayauth.d.f(com.mobile.auth.gatewayauth.d.this) != null) {
                            com.mobile.auth.gatewayauth.d.f(com.mobile.auth.gatewayauth.d.this).onTokenFailed(var1);
                        }
                    } catch (Throwable var2) {
                        com.mobile.auth.gatewayauth.a.a(var2);
                    }

                }
            }, var1);
        } catch (Throwable var2) {
            com.mobile.auth.gatewayauth.a.a(var2);
        }

    }

    static /* synthetic */ TokenResultListener f(com.mobile.auth.gatewayauth.d dVar) {
        try {
            return dVar.m;
        } catch (Throwable th) {
            return null;
        }
    }

    public void a(String str, String str2, String str3, boolean z) {
        try {
            if (this.d != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(Constant.PROTOCOL_WEBVIEW_NAME, str2);
                jSONObject.put(Constant.PROTOCOL_WEBVIEW_URL, str3);
                this.d.onClick(ResultCode.CODE_ERROR_USER_PROTOCOL_CONTROL, this.c, jSONObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            return;
        }
        if (z) {
            a(str, this.p.k(str), str3);
        }
    }

    private void a(final String str, final String str2, final String str3) {
        try {
            com.mobile.auth.gatewayauth.utils.b.a(new Runnable() {
                final /* synthetic */ com.mobile.auth.gatewayauth.d d = com.mobile.auth.gatewayauth.d.this;

                public void run() {
                    try {
                        d.d(this.d).a(d.h(this.d).b(str, str2, UStruct.newUStruct().startTime(d.j(this.d)).endTime(System.currentTimeMillis()).requestId(d.h(this.d).f()).sessionId(d.h(this.d).d()).authSdkCode(ResultCode.CODE_ERROR_USER_PROTOCOL_CONTROL).carrierUrl(str3).build(), ""), 2);
                    } catch (Throwable th) {

                    }
                }
            });
        } catch (Throwable th) {

        }
    }

    public void a(String str, String str2) {
        try {
            if (this.l != null) {
                Activity activity = (Activity) this.l.get();
                if (activity != null) {
                    Intent intent = new Intent(activity, AuthWebVeiwActivity.class);
                    intent.putExtra(Constant.PROTOCOL_WEBVIEW_URL, str2);
                    intent.putExtra(Constant.PROTOCOL_WEBVIEW_NAME, str);
                    intent.putExtra(Constant.PROTOCOL_WEBVIEW_ORIENTATION, f().getScreenOrientation());
                    intent.putExtra(Constant.LOGIN_ACTIVITY_UI_MANAGER_ID, this.j);
                    activity.startActivity(intent);
                    return;
                }
            }
            this.k.d("LoginAuthActivity实例被释放");
        } catch (Throwable th) {
        }
    }

    public void a(boolean z, String str, String str2) {
        try {
            a(z, str, str2, true);
        } catch (Throwable th) {
        }
    }

    static /* synthetic */ AuthUIConfig e(com.mobile.auth.gatewayauth.d dVar) {
        try {
            return dVar.f;
        } catch (Throwable th) {
            return null;
        }
    }

    private void a(boolean z, String str, String str2, boolean z2) {
        try {
            final boolean z3 = z2;
            final boolean z4 = z;
            final String str3 = str;
            final String str4 = str2;
            com.mobile.auth.gatewayauth.utils.b.a(new com.mobile.auth.gatewayauth.utils.b.a() {
                final /* synthetic */ com.mobile.auth.gatewayauth.d e = com.mobile.auth.gatewayauth.d.this;

                protected void a() {
                    try {
                        if (com.mobile.auth.gatewayauth.d.b(this.e) != null) {
                            Activity activity = (Activity) com.mobile.auth.gatewayauth.d.b(this.e).get();
                            if (activity != null) {
                                activity.finish();
                                if (!(com.mobile.auth.gatewayauth.d.e(this.e).getAuthPageActOut() == null
                                        || com.mobile.auth.gatewayauth.d.e(this.e).getActivityIn() == null)) {
                                    activity.overridePendingTransition(AppUtils.getAnimResID(activity, com.mobile.auth.gatewayauth.d.e(this.e).getAuthPageActOut()),
                                            AppUtils.getAnimResID(activity, com.mobile.auth.gatewayauth.d.e(this.e).getActivityIn()));
                                }
                            }
                        }
                        this.e.l();
                    } catch (Throwable th) {
                    }
                }

                protected void a(Throwable th) {
                    try {
                        com.mobile.auth.gatewayauth.d.d(this.e).d("QuitActivity error!", com.mobile.auth.gatewayauth.utils.b.b(th));
                    } catch (Throwable th2) {
                    }
                }

                protected void b() {
                    try {
                        super.b();
                        if (z3 && com.mobile.auth.gatewayauth.d.f(this.e) != null && z4) {
                            TokenRet convertErrorInfo = this.e.a().convertErrorInfo(str3, str4, com.mobile.auth.gatewayauth.d.g(this.e).c());
                            convertErrorInfo.setVendorName(com.mobile.auth.gatewayauth.d.g(this.e).f());
                            convertErrorInfo.setRequestId(com.mobile.auth.gatewayauth.d.h(this.e).f());
                            com.mobile.auth.gatewayauth.d.f(this.e).onTokenFailed(convertErrorInfo.toJsonString());
                        }
                    } catch (Throwable th) {

                    }
                }
            });
        } catch (Throwable th) {

        }
    }

    public void e() {
        try {
            if (this.m != null) {
                TokenRet tokenRet = new TokenRet();
                tokenRet.setVendorName(this.p.f());
                tokenRet.setCode(ResultCode.CODE_ERROR_LOAD_CUSTOM_VIEWS);
                tokenRet.setMsg(ResultCode.MSG_ERROR_LOAD_CUSTOM_VIEWS);
                this.m.onTokenFailed(tokenRet.toJsonString());
            }
        } catch (Throwable th) {
        }
    }

    public void a(boolean var1) {
        try {
            if (this.d != null) {
                JSONObject var2 = new JSONObject();

                try {
                    var2.put("isChecked", var1);
                } catch (JSONException var4) {
                    var4.printStackTrace();
                }

                this.d.onClick("700003", this.c, var2.toString());
            }
        } catch (Throwable var5) {
            com.mobile.auth.gatewayauth.a.a(var5);
        }

    }
}