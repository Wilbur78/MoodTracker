package java.example.android.moodtracker;

import android.content.SharedPreferences;

public class SharedPrefsKeys {

    //String constants which denote the curret mood of the user and the accompanying
    // comment which the user left that day.
    public static final String KEY_CURRENT_DAY = "KEY_CURRENT_DAY";

    public static final String KEY_CURRENT_MOOD = "KEY_CURRENT_MOOD";
    public static final String KEY_CURRENT_COMMENT = "KEY_CURRENT_COMMENT";

    //A string constant for each day of the week to record
    // that days mood and represent it elsewhere in the app
    public static final String KEY_MOOD0 = "KEY_MOOD0";
    public static final String KEY_MOOD1 = "KEY_MOOD1";
    public static final String KEY_MOOD2 = "KEY_MOOD2";
    public static final String KEY_MOOD3 = "KEY_MOOD3";
    public static final String KEY_MOOD4 = "KEY_MOOD4";
    public static final String KEY_MOOD5 = "KEY_MOOD5";
    public static final String KEY_MOOD6 = "KEY_MOOD6";

    //Likewise, this is a list of string constants which denote
    // the comment a user has left on each day of the week
    public static final String KEY_COMMENT0 = "KEY_COMMENT0";
    public static final String KEY_COMMENT1 = "KEY_COMMENT0";
    public static final String KEY_COMMENT2 = "KEY_COMMENT0";
    public static final String KEY_COMMENT3 = "KEY_COMMENT0";
    public static final String KEY_COMMENT4 = "KEY_COMMENT0";
    public static final String KEY_COMMENT5 = "KEY_COMMENT0";
    public static final String KEY_COMMENT6 = "KEY_COMMENT0";

    //This method will be called each time the user changes his/her mood so that the value
    // will be stored in preferences and displayed in the history view of the app after the
    // dayIndex has been increased
    public static void saveCurrentMood(int moodIndex, int dayIndex, SharedPreferences prefs) {
        prefs.edit().putInt(KEY_CURRENT_MOOD,moodIndex).apply();
        switch (dayIndex) {
            case 1:
                prefs.edit().putInt("KEY_MOOD0", moodIndex).apply();
                break;
            case 2:
                prefs.edit().putInt("KEY_MOOD1", moodIndex).apply();
                break;
            case 3:
                prefs.edit().putInt("KEY_MOOD2", moodIndex).apply();
                break;
            case 4:
                prefs.edit().putInt("KEY_MOOD3", moodIndex).apply();
                break;
            case 5:
                prefs.edit().putInt("KEY_MOO4", moodIndex).apply();
                break;
            case 6:
                prefs.edit().putInt("KEY_MOOD5", moodIndex).apply();
                break;
            case 7:
                prefs.edit().putInt("KEY_MOOD6", moodIndex).apply();
                break;
                default:
                    prefs.edit().putInt(KEY_MOOD0, prefs.getInt(KEY_MOOD1, 3)).apply();
                    prefs.edit().putInt(KEY_MOOD1, prefs.getInt(KEY_MOOD2, 3)).apply();
                    prefs.edit().putInt(KEY_MOOD2, prefs.getInt(KEY_MOOD3, 3)).apply();
                    prefs.edit().putInt(KEY_MOOD3, prefs.getInt(KEY_MOOD4, 3)).apply();
                    prefs.edit().putInt(KEY_MOOD4, prefs.getInt(KEY_MOOD5, 3)).apply();
                    prefs.edit().putInt(KEY_MOOD5, prefs.getInt(KEY_MOOD6, 3)).apply();
                    prefs.edit().putInt(KEY_MOOD6, moodIndex).apply();
        }
    }

    //Similarly, this method will be used to save the users comment at the time of the
    // dayIndex incrementing and then displayed the next day from preferences into the history view
    public static void saveCurrentComment(String comment, int dayIndex, SharedPreferences prefs) {
        prefs.edit().putString(KEY_CURRENT_COMMENT, comment).apply();
        switch (dayIndex) {
            case 1:
                prefs.edit().putString("KEY_COMMENT0", comment).apply();
                break;
            case 2:
                prefs.edit().putString("KEY_COMMENT1", comment).apply();
                break;
            case 3:
                prefs.edit().putString("KEY_COMMENT2", comment).apply();
                break;
            case 4:
                prefs.edit().putString("KEY_COMMENT3", comment).apply();
                break;
            case 5:
                prefs.edit().putString("KEY_COMMENT4", comment).apply();
                break;
            case 6:
                prefs.edit().putString("KEY_COMMENT5", comment).apply();
                break;
            case 7:
                prefs.edit().putString("KEY_COMMENT6", comment).apply();
                break;
            default:
                prefs.edit().putString(KEY_COMMENT0, prefs.getString(KEY_COMMENT1, "")).apply();
                prefs.edit().putString(KEY_COMMENT1, prefs.getString(KEY_COMMENT2, "")).apply();
                prefs.edit().putString(KEY_COMMENT2, prefs.getString(KEY_COMMENT3, "")).apply();
                prefs.edit().putString(KEY_COMMENT3, prefs.getString(KEY_COMMENT4, "")).apply();
                prefs.edit().putString(KEY_COMMENT4, prefs.getString(KEY_COMMENT5, "")).apply();
                prefs.edit().putString(KEY_COMMENT5, prefs.getString(KEY_COMMENT6, "")).apply();
                prefs.edit().putString(KEY_COMMENT6, comment).apply();
        }
    }
}
