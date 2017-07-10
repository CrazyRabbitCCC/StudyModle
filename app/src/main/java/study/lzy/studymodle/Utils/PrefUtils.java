package study.lzy.studymodle.Utils;
// @author: lzy  time: 2016/11/15.


import android.content.Context;
import android.content.SharedPreferences;

import study.lzy.studymodle.R;

public class PrefUtils {

    private static final String PREFS_NAME = "study.lzy.studymodle.test";
    private static final String PREF_PREFIX_KEY = "image_widget_";
    public static void savePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    public static String loadPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    public  static void deletePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }
}
