package com.zinedroid.syromalabar.smartcatechism.Base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Cecil Paul on 15/11/17.
 */

public class BaseReceiver extends BroadcastReceiver {
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
    }
    public void setSharedPreference(String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(AppConstants.SHARED_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getSharedPreference(String key) {
        SharedPreferences prefs = context.getSharedPreferences(AppConstants.SHARED_KEY, MODE_PRIVATE);
        return prefs.getString(key, "DEFAULT");
    }
}
