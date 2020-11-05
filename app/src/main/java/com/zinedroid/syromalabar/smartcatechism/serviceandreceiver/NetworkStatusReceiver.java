package com.zinedroid.syromalabar.smartcatechism.serviceandreceiver;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.zinedroid.syromalabar.smartcatechism.Base.BaseApplication;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseReceiver;

import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;

import com.zinedroid.syromalabar.smartcatechism.webservice.API;

import org.json.JSONObject;


/**
 * Created by Cecil Paul on 15/11/17.
 */

public class NetworkStatusReceiver extends BaseReceiver {
    Intent i;
    API api;
    JSONObject mChapterJsonObject, mParagraphJsonObject, mDiscussionJsonObject, mActivityJsonObject = null;

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        try {

            boolean isVisible = BaseApplication.isActivityVisible();// Check if
            // activity
            // is
            // visible
            // or not
            Log.i("Activity is Visible ", "Is activity visible : " + isVisible);

            // If it is visible then trigger the task else do nothing

            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();

            // Check internet connection and accrding to state change the
            // text of activity by calling method
            if (networkInfo != null && networkInfo.isConnected()) {

                if (getSharedPreference(AppConstants.APIKeys.CLASS_ID)!="DEFAULT") {
                    i = new Intent(context, OfflineService.class);
                    context.startService(i);
                }
            } else {
//                    new MainActivity().changeTextStatus(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
