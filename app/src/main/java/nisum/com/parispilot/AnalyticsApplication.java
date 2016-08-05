package nisum.com.parispilot;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by nisum on 8/1/16.
 */
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
