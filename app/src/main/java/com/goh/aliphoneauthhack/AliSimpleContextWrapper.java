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
import android.os.UserHandle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.goh.aliphoneauthhack.utils.SignatureUtil;

import java.lang.reflect.Method;
import java.util.List;

public class AliSimpleContextWrapper extends ContextWrapper {

    Context mRealContext;

    public AliSimpleContextWrapper(Context base) {
        super(base);
        mRealContext = base;
    }

    @Override
    public String getPackageName() {
        return AliParameter.AUTH_PACKAGE_NAME;
    }

    @Override
    public PackageManager getPackageManager() {
        return new MyPackageManager(super.getPackageManager(), super.getPackageName());
    }

    @Override
    public Context getApplicationContext() {
        return new MyApplication(mRealContext);
    }

    private static class MyApplication extends Application {
        public MyApplication(Context realContext) {
            try {
                Method method = Application.class.getDeclaredMethod("attach", Context.class);
                method.setAccessible(true);
                method.invoke(this, realContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public PackageManager getPackageManager() {
            return new MyPackageManager(super.getPackageManager(), super.getPackageName());
        }
    }

    private static class MyPackageManager extends PackageManager {
        private final PackageManager packageManager;
        private final String realPackageName;
        private Signature[] sSignature;

        public MyPackageManager(PackageManager packageManager, String realPackageName) {
            this.packageManager = packageManager;
            this.realPackageName = realPackageName;
        }

        @Override
        public PackageInfo getPackageInfo(@NonNull String packageName, int flags) throws NameNotFoundException {
            PackageInfo packageInfo = packageManager.getPackageInfo(realPackageName, flags);
            packageInfo.signatures = getSignature();
            packageInfo.packageName = AliParameter.AUTH_PACKAGE_NAME;
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
        public PackageInfo getPackageInfo(@NonNull VersionedPackage versionedPackage, int flags) throws NameNotFoundException {
            return null;
        }

        @Override
        public String[] currentToCanonicalPackageNames(@NonNull String[] packageNames) {
            return new String[0];
        }

        @Override
        public String[] canonicalToCurrentPackageNames(@NonNull String[] packageNames) {
            return new String[0];
        }

        @Nullable
        @Override
        public Intent getLaunchIntentForPackage(@NonNull String packageName) {
            return null;
        }

        @Nullable
        @Override
        public Intent getLeanbackLaunchIntentForPackage(@NonNull String packageName) {
            return null;
        }

        @Override
        public int[] getPackageGids(@NonNull String packageName) throws NameNotFoundException {
            return new int[0];
        }

        @Override
        public int[] getPackageGids(@NonNull String packageName, int flags) throws NameNotFoundException {
            return new int[0];
        }

        @Override
        public int getPackageUid(@NonNull String packageName, int flags) throws NameNotFoundException {
            return 0;
        }

        @Override
        public PermissionInfo getPermissionInfo(@NonNull String permissionName, int flags) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public List<PermissionInfo> queryPermissionsByGroup(@NonNull String permissionGroup, int flags) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public PermissionGroupInfo getPermissionGroupInfo(@NonNull String permissionName, int flags) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
            return null;
        }

        @NonNull
        @Override
        public ApplicationInfo getApplicationInfo(@NonNull String packageName, int flags) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public ActivityInfo getActivityInfo(@NonNull ComponentName component, int flags) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public ActivityInfo getReceiverInfo(@NonNull ComponentName component, int flags) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public ServiceInfo getServiceInfo(@NonNull ComponentName component, int flags) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public ProviderInfo getProviderInfo(@NonNull ComponentName component, int flags) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public List<PackageInfo> getInstalledPackages(int flags) {
            return null;
        }

        @NonNull
        @Override
        public List<PackageInfo> getPackagesHoldingPermissions(@NonNull String[] permissions, int flags) {
            return null;
        }

        @Override
        public int checkPermission(@NonNull String permissionName, @NonNull String packageName) {
            return 0;
        }

        @Override
        public boolean isPermissionRevokedByPolicy(@NonNull String permissionName, @NonNull String packageName) {
            return false;
        }

        @Override
        public boolean addPermission(@NonNull PermissionInfo info) {
            return false;
        }

        @Override
        public boolean addPermissionAsync(@NonNull PermissionInfo info) {
            return false;
        }

        @Override
        public void removePermission(@NonNull String permissionName) {
        }

        @Override
        public int checkSignatures(@NonNull String packageName1, @NonNull String packageName2) {
            return 0;
        }

        @Override
        public int checkSignatures(int uid1, int uid2) {
            return 0;
        }

        @Nullable
        @Override
        public String[] getPackagesForUid(int uid) {
            return new String[0];
        }

        @Nullable
        @Override
        public String getNameForUid(int uid) {
            return null;
        }

        @NonNull
        @Override
        public List<ApplicationInfo> getInstalledApplications(int flags) {
            return null;
        }

        @Override
        public boolean isInstantApp() {
            return false;
        }

        @Override
        public boolean isInstantApp(@NonNull String packageName) {
            return false;
        }

        @Override
        public int getInstantAppCookieMaxBytes() {
            return 0;
        }

        @NonNull
        @Override
        public byte[] getInstantAppCookie() {
            return new byte[0];
        }

        @Override
        public void clearInstantAppCookie() {
        }

        @Override
        public void updateInstantAppCookie(@Nullable byte[] cookie) {
        }

        @Nullable
        @Override
        public String[] getSystemSharedLibraryNames() {
            return new String[0];
        }

        @NonNull
        @Override
        public List<SharedLibraryInfo> getSharedLibraries(int flags) {
            return null;
        }

        @Nullable
        @Override
        public ChangedPackages getChangedPackages(int sequenceNumber) {
            return null;
        }

        @NonNull
        @Override
        public FeatureInfo[] getSystemAvailableFeatures() {
            return new FeatureInfo[0];
        }

        @Override
        public boolean hasSystemFeature(@NonNull String featureName) {
            return false;
        }

        @Override
        public boolean hasSystemFeature(@NonNull String featureName, int version) {
            return false;
        }

        @Nullable
        @Override
        public ResolveInfo resolveActivity(@NonNull Intent intent, int flags) {
            return null;
        }

        @NonNull
        @Override
        public List<ResolveInfo> queryIntentActivities(@NonNull Intent intent, int flags) {
            return null;
        }

        @NonNull
        @Override
        public List<ResolveInfo> queryIntentActivityOptions(@Nullable ComponentName caller, @Nullable Intent[] specifics, @NonNull Intent intent, int flags) {
            return null;
        }

        @NonNull
        @Override
        public List<ResolveInfo> queryBroadcastReceivers(@NonNull Intent intent, int flags) {
            return null;
        }

        @Nullable
        @Override
        public ResolveInfo resolveService(@NonNull Intent intent, int flags) {
            return null;
        }

        @NonNull
        @Override
        public List<ResolveInfo> queryIntentServices(@NonNull Intent intent, int flags) {
            return null;
        }

        @NonNull
        @Override
        public List<ResolveInfo> queryIntentContentProviders(@NonNull Intent intent, int flags) {
            return null;
        }

        @Nullable
        @Override
        public ProviderInfo resolveContentProvider(@NonNull String authority, int flags) {
            return null;
        }

        @NonNull
        @Override
        public List<ProviderInfo> queryContentProviders(@Nullable String processName, int uid, int flags) {
            return null;
        }

        @NonNull
        @Override
        public InstrumentationInfo getInstrumentationInfo(@NonNull ComponentName className, int flags) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public List<InstrumentationInfo> queryInstrumentation(@NonNull String targetPackage, int flags) {
            return null;
        }

        @Nullable
        @Override
        public Drawable getDrawable(@NonNull String packageName, int resid, @Nullable ApplicationInfo appInfo) {
            return null;
        }

        @NonNull
        @Override
        public Drawable getActivityIcon(@NonNull ComponentName activityName) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public Drawable getActivityIcon(@NonNull Intent intent) throws NameNotFoundException {
            return null;
        }

        @Nullable
        @Override
        public Drawable getActivityBanner(@NonNull ComponentName activityName) throws NameNotFoundException {
            return null;
        }

        @Nullable
        @Override
        public Drawable getActivityBanner(@NonNull Intent intent) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public Drawable getDefaultActivityIcon() {
            return null;
        }

        @NonNull
        @Override
        public Drawable getApplicationIcon(@NonNull ApplicationInfo info) {
            return null;
        }

        @NonNull
        @Override
        public Drawable getApplicationIcon(@NonNull String packageName) throws NameNotFoundException {
            return null;
        }

        @Nullable
        @Override
        public Drawable getApplicationBanner(@NonNull ApplicationInfo info) {
            return null;
        }

        @Nullable
        @Override
        public Drawable getApplicationBanner(@NonNull String packageName) throws NameNotFoundException {
            return null;
        }

        @Nullable
        @Override
        public Drawable getActivityLogo(@NonNull ComponentName activityName) throws NameNotFoundException {
            return null;
        }

        @Nullable
        @Override
        public Drawable getActivityLogo(@NonNull Intent intent) throws NameNotFoundException {
            return null;
        }

        @Nullable
        @Override
        public Drawable getApplicationLogo(@NonNull ApplicationInfo info) {
            return null;
        }

        @Nullable
        @Override
        public Drawable getApplicationLogo(@NonNull String packageName) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public Drawable getUserBadgedIcon(@NonNull Drawable drawable, @NonNull UserHandle user) {
            return null;
        }

        @NonNull
        @Override
        public Drawable getUserBadgedDrawableForDensity(@NonNull Drawable drawable, @NonNull UserHandle user, @Nullable Rect badgeLocation, int badgeDensity) {
            return null;
        }

        @NonNull
        @Override
        public CharSequence getUserBadgedLabel(@NonNull CharSequence label, @NonNull UserHandle user) {
            return null;
        }

        @Nullable
        @Override
        public CharSequence getText(@NonNull String packageName, int resid, @Nullable ApplicationInfo appInfo) {
            return null;
        }

        @Nullable
        @Override
        public XmlResourceParser getXml(@NonNull String packageName, int resid, @Nullable ApplicationInfo appInfo) {
            return null;
        }

        @NonNull
        @Override
        public CharSequence getApplicationLabel(@NonNull ApplicationInfo info) {
            return null;
        }

        @NonNull
        @Override
        public Resources getResourcesForActivity(@NonNull ComponentName activityName) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public Resources getResourcesForApplication(@NonNull ApplicationInfo app) throws NameNotFoundException {
            return null;
        }

        @NonNull
        @Override
        public Resources getResourcesForApplication(@NonNull String packageName) throws NameNotFoundException {
            return null;
        }

        @Override
        public void verifyPendingInstall(int id, int verificationCode) {

        }

        @Override
        public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) {

        }

        @Override
        public void setInstallerPackageName(@NonNull String targetPackage, @Nullable String installerPackageName) {

        }

        @Nullable
        @Override
        public String getInstallerPackageName(@NonNull String packageName) {
            return null;
        }

        @Override
        public void addPackageToPreferred(@NonNull String packageName) {
        }

        @Override
        public void removePackageFromPreferred(@NonNull String packageName) {
        }

        @NonNull
        @Override
        public List<PackageInfo> getPreferredPackages(int flags) {
            return null;
        }

        @Override
        public void addPreferredActivity(@NonNull IntentFilter filter, int match, @Nullable ComponentName[] set, @NonNull ComponentName activity) {
        }

        @Override
        public void clearPackagePreferredActivities(@NonNull String packageName) {
        }

        @Override
        public int getPreferredActivities(@NonNull List<IntentFilter> outFilters, @NonNull List<ComponentName> outActivities, @Nullable String packageName) {
            return 0;
        }

        @Override
        public void setComponentEnabledSetting(@NonNull ComponentName componentName, int newState, int flags) {
        }

        @Override
        public int getComponentEnabledSetting(@NonNull ComponentName componentName) {
            return 0;
        }

        @Override
        public void setApplicationEnabledSetting(@NonNull String packageName, int newState, int flags) {
        }

        @Override
        public int getApplicationEnabledSetting(@NonNull String packageName) {
            return 0;
        }

        @Override
        public boolean isSafeMode() {
            return false;
        }

        @Override
        public void setApplicationCategoryHint(@NonNull String packageName, int categoryHint) {
        }

        @NonNull
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
