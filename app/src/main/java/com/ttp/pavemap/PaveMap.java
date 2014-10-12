package com.ttp.pavemap;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Thai on 10/11/2014.
 */
public class PaveMap extends Application {
    public static PaveMap instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public long getLastTimeCallVSKapi() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(instance);
        return pref.getLong(Constant.PREF_LAST_TIME_CALL_API, System.currentTimeMillis());
    }

    public void setLastTImeCallVSKapi(long time) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(instance);
        pref.edit().putLong(Constant.PREF_LAST_TIME_CALL_API, time).commit();
    }

    public static void showToast(int stringId) {
        showToast(instance.getResources().getString(stringId));
    }

    public  static void showToast(String string){
        Toast toast = Toast.makeText(instance, string, Toast.LENGTH_SHORT);
        toast.show();
    }
}
