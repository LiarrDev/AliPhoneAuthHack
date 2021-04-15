package com.goh.aliphoneauthhack.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

public class SignatureUtil {
    public static Signature[] getSignatures(PackageManager manager, String packageName) {
        try {
            PackageInfo packageInfo = manager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
