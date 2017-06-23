package com.bk.photogobuy.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.bk.photogobuy.utils.SharedPrefUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Hado on 30-Jul-16.
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    private static SharedPrefUtils sharedPrefUtils;

    private static Context mContext;

    public BaseApplication() {
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPrefUtils = new SharedPrefUtils(getApplicationContext());
        mContext = getApplicationContext();

        //printHashKey();
    }

    public static SharedPrefUtils getSharedPreferences() {
        return sharedPrefUtils;
    }

    public static Context getContext() {
        return mContext;
    }

    public void printHashKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.bk.girltrollsv",
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

}
