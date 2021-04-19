package com.goh.aliphoneauthhack;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ChangedPackages;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.Signature;
import android.content.pm.VersionedPackage;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.UserHandle;

import com.goh.aliphoneauthhack.utils.SignatureUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.List;

/**
 * 该 Context 类用于篡改阿里云本机号码一键登录 SDK 获取包名的方式
 */
public class AliContextWrapper extends ContextWrapper {

    private final Context mRealContext;
    private final String mAuthPackageName;

    public AliContextWrapper(Context realContext, String authPackageName) {
        super(realContext);
        this.mRealContext = realContext;
        this.mAuthPackageName = authPackageName;
    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }

    @Override
    public String getPackageName() {
        return mAuthPackageName;
    }

    public String getRealPackageName() {
        return super.getPackageName();
    }

    @Override
    public Context getApplicationContext() {
        return new MyApplication(mRealContext, mAuthPackageName);
    }

    @Override
    public PackageManager getPackageManager() {
        return new MyPackageManager(super.getPackageManager(), getRealPackageName(), mAuthPackageName);
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        ApplicationInfo applicationInfo = super.getApplicationInfo();
        applicationInfo.packageName = mAuthPackageName;
        return applicationInfo;
    }

    private static class MyApplication extends Application {
        private final String authPackageName;

        public MyApplication(Context realContext, String authPackageName) {
            this.authPackageName = authPackageName;
            try {
                Method method = Application.class.getDeclaredMethod("attach", Context.class);
                method.setAccessible(true);
                method.invoke(this, realContext);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public String getPackageName() {
            return authPackageName;
        }

        @Override
        public ApplicationInfo getApplicationInfo() {
            ApplicationInfo applicationInfo = super.getApplicationInfo();
            applicationInfo.packageName = authPackageName;
            return applicationInfo;
        }

        @Override
        public PackageManager getPackageManager() {
            return new MyPackageManager(super.getPackageManager(), super.getPackageName(), authPackageName);
        }

        @Override
        public Context getApplicationContext() {
            return this;
        }
    }

    private static class MyPackageManager extends PackageManager {

        private static Signature[] sSignature;

        private final PackageManager packageManager;
        private final String realPackageName;
        private final String authPackageName;

        public MyPackageManager(PackageManager manager, String realPackageName, String authPackageName) {
            this.packageManager = manager;
            this.realPackageName = realPackageName;
            this.authPackageName = authPackageName;
        }

        @Override
        public PackageInfo getPackageInfo(String packageName, int flags) throws NameNotFoundException {
            PackageInfo packageInfo = packageManager.getPackageInfo(realPackageName, flags);
            packageInfo.signatures = getSignature();
            packageInfo.packageName = authPackageName;
            return packageInfo;
        }

        private Signature[] getSignature() {
            if (sSignature != null) {
                return sSignature;
            }
            sSignature = SignatureUtil.getSignatures(packageManager, realPackageName);
            return sSignature;
        }

        @Override
        public PackageInfo getPackageInfo(VersionedPackage versionedPackage, int flags) throws NameNotFoundException {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return packageManager.getPackageInfo(versionedPackage, flags);
            }
            return null;
        }

        @Override
        public String[] currentToCanonicalPackageNames(String[] names) {
            return packageManager.currentToCanonicalPackageNames(names);
        }

        @Override
        public String[] canonicalToCurrentPackageNames(String[] names) {
            return packageManager.canonicalToCurrentPackageNames(names);
        }

        @Override
        public Intent getLaunchIntentForPackage(String packageName) {
            return packageManager.getLaunchIntentForPackage(packageName);
        }

        @Override
        public Intent getLeanbackLaunchIntentForPackage(String packageName) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return packageManager.getLeanbackLaunchIntentForPackage(packageName);
            }
            return null;
        }

        @Override
        public int[] getPackageGids(String packageName) throws NameNotFoundException {
            return packageManager.getPackageGids(packageName);
        }

        @Override
        public int[] getPackageGids(String packageName, int flags) throws NameNotFoundException {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return packageManager.getPackageGids(packageName, flags);
            }
            return null;
        }

        @Override
        public int getPackageUid(String packageName, int flags) throws NameNotFoundException {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return packageManager.getPackageUid(packageName, flags);
            }
            return 1;
        }

        @Override
        public PermissionInfo getPermissionInfo(String name, int flags) throws NameNotFoundException {
            return packageManager.getPermissionInfo(name, flags);
        }

        @Override
        public List<PermissionInfo> queryPermissionsByGroup(String group, int flags) throws NameNotFoundException {
            return packageManager.queryPermissionsByGroup(group, flags);
        }

        @Override
        public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws NameNotFoundException {
            return packageManager.getPermissionGroupInfo(name, flags);
        }

        @Override
        public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
            return packageManager.getAllPermissionGroups(flags);
        }

        @Override
        public ApplicationInfo getApplicationInfo(String packageName, int flags) throws NameNotFoundException {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(realPackageName, flags);
            applicationInfo.packageName = authPackageName;
            return applicationInfo;
        }

        @Override
        public ActivityInfo getActivityInfo(ComponentName component, int flags) throws NameNotFoundException {
            return packageManager.getActivityInfo(component, flags);
        }

        @Override
        public ActivityInfo getReceiverInfo(ComponentName component, int flags) throws NameNotFoundException {
            return packageManager.getReceiverInfo(component, flags);
        }

        @Override
        public ServiceInfo getServiceInfo(ComponentName component, int flags) throws NameNotFoundException {
            return null;
        }

        @Override
        public ProviderInfo getProviderInfo(ComponentName component, int flags) throws NameNotFoundException {
            return null;
        }

        @Override
        public List<PackageInfo> getInstalledPackages(int flags) {
            return null;
        }

        @Override
        public List<PackageInfo> getPackagesHoldingPermissions(String[] permissions, int flags) {
            return null;
        }

        @Override
        public int checkPermission(String permName, String pkgName) {
            if (authPackageName.equals(pkgName)) {
                return packageManager.checkPermission(permName, realPackageName);
            }
            return packageManager.checkPermission(permName, pkgName);
        }

        @Override
        public boolean isPermissionRevokedByPolicy(String permName, String pkgName) {
            return false;
        }

        @Override
        public boolean addPermission(PermissionInfo info) {
            return false;
        }

        @Override
        public boolean addPermissionAsync(PermissionInfo info) {
            return false;
        }

        @Override
        public void removePermission(String name) {
        }

        @Override
        public int checkSignatures(String pkg1, String pkg2) {
            return 0;
        }

        @Override
        public int checkSignatures(int uid1, int uid2) {
            return 0;
        }

        @Override
        public String[] getPackagesForUid(int uid) {
            return new String[0];
        }

        @Override
        public String getNameForUid(int uid) {
            return null;
        }

        @Override
        public List<ApplicationInfo> getInstalledApplications(int flags) {
            return null;
        }

        @Override
        public boolean isInstantApp() {
            return false;
        }

        @Override
        public boolean isInstantApp(String packageName) {
            return false;
        }

        @Override
        public int getInstantAppCookieMaxBytes() {
            return 0;
        }

        @Override
        public byte[] getInstantAppCookie() {
            return new byte[0];
        }

        @Override
        public void clearInstantAppCookie() {
        }

        @Override
        public void updateInstantAppCookie(byte[] cookie) {
        }

        @Override
        public String[] getSystemSharedLibraryNames() {
            return new String[0];
        }

        @Override
        public List<SharedLibraryInfo> getSharedLibraries(int flags) {
            return null;
        }

        @Override
        public ChangedPackages getChangedPackages(int sequenceNumber) {
            return null;
        }

        @Override
        public FeatureInfo[] getSystemAvailableFeatures() {
            return new FeatureInfo[0];
        }

        @Override
        public boolean hasSystemFeature(String name) {
            return false;
        }

        @Override
        public boolean hasSystemFeature(String name, int version) {
            return false;
        }

        @Override
        public ResolveInfo resolveActivity(Intent intent, int flags) {
            return null;
        }

        @Override
        public List<ResolveInfo> queryIntentActivities(Intent intent, int flags) {
            return null;
        }

        @Override
        public List<ResolveInfo> queryIntentActivityOptions(ComponentName caller, Intent[] specifics, Intent intent, int flags) {
            return null;
        }

        @Override
        public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags) {
            return null;
        }

        @Override
        public ResolveInfo resolveService(Intent intent, int flags) {
            return null;
        }

        @Override
        public List<ResolveInfo> queryIntentServices(Intent intent, int flags) {
            return null;
        }

        @Override
        public List<ResolveInfo> queryIntentContentProviders(Intent intent, int flags) {
            return null;
        }

        @Override
        public ProviderInfo resolveContentProvider(String name, int flags) {
            return null;
        }

        @Override
        public List<ProviderInfo> queryContentProviders(String processName, int uid, int flags) {
            return null;
        }

        @Override
        public InstrumentationInfo getInstrumentationInfo(ComponentName className, int flags) throws NameNotFoundException {
            return null;
        }

        @Override
        public List<InstrumentationInfo> queryInstrumentation(String targetPackage, int flags) {
            return null;
        }

        @Override
        public Drawable getDrawable(String packageName, int resid, ApplicationInfo appInfo) {
            return null;
        }

        @Override
        public Drawable getActivityIcon(ComponentName activityName) throws NameNotFoundException {
            return null;
        }

        @Override
        public Drawable getActivityIcon(Intent intent) throws NameNotFoundException {
            return null;
        }

        @Override
        public Drawable getActivityBanner(ComponentName activityName) throws NameNotFoundException {
            return null;
        }

        @Override
        public Drawable getActivityBanner(Intent intent) throws NameNotFoundException {
            return null;
        }

        @Override
        public Drawable getDefaultActivityIcon() {
            return null;
        }

        @Override
        public Drawable getApplicationIcon(ApplicationInfo info) {
            return null;
        }

        @Override
        public Drawable getApplicationIcon(String packageName) throws NameNotFoundException {
            return null;
        }

        @Override
        public Drawable getApplicationBanner(ApplicationInfo info) {
            return null;
        }

        @Override
        public Drawable getApplicationBanner(String packageName) throws NameNotFoundException {
            return null;
        }

        @Override
        public Drawable getActivityLogo(ComponentName activityName) throws NameNotFoundException {
            return null;
        }

        @Override
        public Drawable getActivityLogo(Intent intent) throws NameNotFoundException {
            return null;
        }

        @Override
        public Drawable getApplicationLogo(ApplicationInfo info) {
            return null;
        }

        @Override
        public Drawable getApplicationLogo(String packageName) throws NameNotFoundException {
            return null;
        }

        @Override
        public Drawable getUserBadgedIcon(Drawable icon, UserHandle user) {
            return null;
        }

        @Override
        public Drawable getUserBadgedDrawableForDensity(Drawable drawable, UserHandle user, Rect badgeLocation, int badgeDensity) {
            return null;
        }

        @Override
        public CharSequence getUserBadgedLabel(CharSequence label, UserHandle user) {
            return null;
        }

        @Override
        public CharSequence getText(String packageName, int resid, ApplicationInfo appInfo) {
            return null;
        }

        @Override
        public XmlResourceParser getXml(String packageName, int resid, ApplicationInfo appInfo) {
            return null;
        }

        @Override
        public CharSequence getApplicationLabel(ApplicationInfo info) {
            try {
                return packageManager.getApplicationLabel(packageManager.getApplicationInfo(realPackageName, PackageManager.GET_META_DATA)).toString();
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Resources getResourcesForActivity(ComponentName activityName) throws NameNotFoundException {
            return null;
        }

        @Override
        public Resources getResourcesForApplication(ApplicationInfo app) throws NameNotFoundException {
            return null;
        }

        @Override
        public Resources getResourcesForApplication(String appPackageName) throws NameNotFoundException {
            return null;
        }

        @Override
        public void verifyPendingInstall(int id, int verificationCode) {
        }

        @Override
        public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) {
        }

        @Override
        public void setInstallerPackageName(String targetPackage, String installerPackageName) {
        }

        @Override
        public String getInstallerPackageName(String packageName) {
            return null;
        }

        @Override
        public void addPackageToPreferred(String packageName) {
        }

        @Override
        public void removePackageFromPreferred(String packageName) {
        }

        @Override
        public List<PackageInfo> getPreferredPackages(int flags) {
            return null;
        }

        @Override
        public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
        }

        @Override
        public void clearPackagePreferredActivities(String packageName) {
        }

        @Override
        public int getPreferredActivities(List<IntentFilter> outFilters, List<ComponentName> outActivities, String packageName) {
            return 0;
        }

        @Override
        public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags) {
        }

        @Override
        public int getComponentEnabledSetting(ComponentName componentName) {
            return 0;
        }

        @Override
        public void setApplicationEnabledSetting(String packageName, int newState, int flags) {
        }

        @Override
        public int getApplicationEnabledSetting(String packageName) {
            return 0;
        }

        @Override
        public boolean isSafeMode() {
            return false;
        }

        @Override
        public void setApplicationCategoryHint(String packageName, int categoryHint) {
        }

        @Override
        public PackageInstaller getPackageInstaller() {
            return null;
        }

        @Override
        public boolean canRequestPackageInstalls() {
            return false;
        }
    }
}
