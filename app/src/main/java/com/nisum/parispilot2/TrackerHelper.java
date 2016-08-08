package com.nisum.parispilot2;

import android.app.Activity;
import android.app.Application;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by nisum on 8/5/16.
 */
public class TrackerHelper {
    private static Tracker tracker = null;

    private TrackerHelper(){}

    public static void initTracker(Application application){
        if(tracker == null)
        tracker = AnalyticsApplication.getDefaultTracker(application);
    }

    public static void nameScreen(Activity activity){
        if(tracker != null){
            tracker.setScreenName(activity.getClass().getName());
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    public static void sendEvent(String category, String action){
        tracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).build());
    }
}
