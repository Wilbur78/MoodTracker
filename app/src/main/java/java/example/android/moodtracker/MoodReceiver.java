package java.example.android.moodtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

//BroadcastReceiver to detect when the scheduleAlarm method in MainActivity.java
//is called and carry out the following method
public class MoodReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        //Gets the current day value from Shared Preferences, increments the dayIndex from that value,
        //and saves the changes back to Shared Preferences to be reapplied throughout the application
        int currentDay = mSharedPrefs.getInt(SharedPrefsKeys.KEY_CURRENT_DAY, 1);
        currentDay++;
        mSharedPrefs.edit().putInt(SharedPrefsKeys.KEY_CURRENT_DAY, currentDay).apply();
    }
}
