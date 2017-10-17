package com.blogspot.frybitsinc.shakeshack_unlock;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by spong on 2017-10-17.
 */

public class SharedPreference {
    private static final String SP_NAME = "ohao";
    private static SharedPreferences mSharedPreference = null;

    public static final String UNLOCK_MODE = "unlock_mode";
    public static final String PIN = "pin";
    public static final String PATTERN = "pattern";
    public static final String FINGERPRINT = "fingerprint";
    public static final String GESTURE = "gesture";
    public static final Boolean LOCK_STATE = null;

    public static void init(Context context) {
        mSharedPreference = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
    
    public static void setBoolean(String key, boolean value) {
        if(key == null) {
            return;
        }
        if(mSharedPreference == null) {
            return;
        }
        SharedPreferences.Editor edit = mSharedPreference.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean getBoolean(String key) {
        if(key == null) {
            return false;
        }
        if(mSharedPreference == null) {
            return false;
        }
        return mSharedPreference.getBoolean(key, false);
    }

    public static void setString(String key, String value) {
        if(key == null) {
            return;
        }
        if(mSharedPreference == null) {
            return;
        }
        SharedPreferences.Editor edit = mSharedPreference.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getString(String key) {
        if(key == null) {
            return null;
        }
        if(mSharedPreference == null) {
            return null;
        }
        return mSharedPreference.getString(key, null);
    }
}
