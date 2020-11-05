package com.zinedroid.syromalabar.smartcatechism.Base;

import com.orm.SugarApp;

/**
 * Created by Cecil Paul on 31/8/17.
 */

public class BaseApplication extends SugarApp {

    public static boolean activityVisible; // Variable that will check the
    // current activity state

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }
}
