package com.nisum.parispilot2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;

/**
 * Created by nisum on 8/4/16.
 */
public class SurveyHandler {
    private static Activity activity;

    private SurveyHandler(){}


    private static SharedPreferences getPreferences(Activity activity){
        return activity.getSharedPreferences("nisum.com.parispilot", Context.MODE_PRIVATE);
    }

    public static void handleSurvey(Activity activity){
        SharedPreferences pref = getPreferences(activity);
        SurveyHandler.activity = activity;
        if(pref.getBoolean("surveyAnswered", false)){
            activity.finish();
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch(i){
                        case DialogInterface.BUTTON_POSITIVE:
                            Intent survey = new Intent(SurveyHandler.activity, SurveyActivity.class);
                            SurveyHandler.activity.startActivity(survey);
                            SurveyHandler.activity.finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            SurveyHandler.activity.finish();
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(R.string.show_survey).setPositiveButton("Sí", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    public static void setSurveyAnswered(boolean surveyAnswered, Activity activity) throws IllegalAccessException {
        SharedPreferences pref = getPreferences(activity);
        pref.edit().putBoolean("surveyAnswered", surveyAnswered).commit();
    }
}
