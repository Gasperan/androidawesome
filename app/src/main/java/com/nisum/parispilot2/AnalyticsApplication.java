package com.nisum.parispilot2;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;


public class AnalyticsApplication {
    private static Tracker tracker;

    private AnalyticsApplication(){}

    synchronized static public Tracker getDefaultTracker(Application context){
        if(null == tracker){
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            tracker = analytics.newTracker(R.xml.global_tracker);
        }
        return tracker;
    }

}
