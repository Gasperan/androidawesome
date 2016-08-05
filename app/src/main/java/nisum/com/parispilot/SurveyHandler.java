package nisum.com.parispilot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by nisum on 8/4/16.
 */
public class SurveyHandler {
    private SurveyHandler(){}

    private static SharedPreferences getPreferences(Activity activity){
        return activity.getSharedPreferences("nisum.com.parispilot", Context.MODE_PRIVATE);
    }

    public static void handleSurvey(Activity activity){
        SharedPreferences pref = getPreferences(activity);
        if(pref.getBoolean("surveyAnswered", false)){
            activity.finish();
        } else {
            Intent survey = new Intent(activity, SurveyActivity.class);
            activity.startActivity(survey);
            activity.finish();
        }
    }

    public static void setSurveyAnswered(boolean surveyAnswered, Activity activity) throws IllegalAccessException {
        SharedPreferences pref = getPreferences(activity);
        pref.edit().putBoolean("surveyAnswered", surveyAnswered).commit();
    }
}
