package java.example.android.moodtracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.Calendar;
import static android.view.View.OnClickListener;

//This file represents the functionality of the opening screen of the application, where the user will:
//select moods, add comments, and access their weekly mood history

public class MainActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private static final String TAG = "Gestures";

    private ImageView mSmileyFace;
    private RelativeLayout mRelativeLayout;

    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;

    private GestureDetector mDetector;
    private MediaPlayer mediaPlayer;

    private SharedPreferences mSharedPrefs;
    private String todaysComment;
    private int moodIndex;
    private int currentDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Calling the above instantiated elements of the starting page/activity_main.xml
        mSmileyFace = findViewById(R.id.imageView1);
        mRelativeLayout = findViewById(R.id.main_menu_relative_layout);
        ImageButton btnComment = findViewById(R.id.btnComment);
        ImageButton btnHistory = findViewById(R.id.moodHistory);

        //Gesture detector set for this screen to be referenced below along with the necessary methods
        mDetector = new GestureDetector (this, this);
        //Gets default preferences to initiate their implementation into the app as personalizeable in accessible
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Retrieving each index, for the current day, mood, and comment respectively, from preferences to be
        //saved whenever it is changed during the course of a day
        currentDay = mSharedPrefs.getInt(SharedPrefsKeys.KEY_CURRENT_DAY, 1);
        moodIndex = mSharedPrefs.getInt(SharedPrefsKeys.KEY_CURRENT_MOOD, 3);
        todaysComment = mSharedPrefs.getString(SharedPrefsKeys.KEY_CURRENT_COMMENT, "");

        scheduleAlarm();

        btnComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final EditText editText = new EditText(MainActivity.this);

                builder.setMessage("How are you feeling right now?").setView(editText)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                todaysComment = editText.getText().toString();
                                dialog.dismiss();
                                //After the user types a comment and presses "Submit", the "saveCurrentComment" method is called
                                //from the SharedPrefsKey class (see said class for method description)
                                SharedPrefsKeys.saveCurrentComment(editText.getText().toString(),
                                        currentDay, mSharedPrefs);

                                Toast.makeText(MainActivity.this,
                                        "Submission Saved",
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                builder.setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        Toast.makeText(MainActivity.this,
                                "Comment cancelled.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                        .create().show();
            }
        });

//Button which when pressed brings the user to his history activity, which displays his mood
        //over the last week along with their respective comments
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MoodHistory.class);
                startActivity(intent);
            }
        });
    }


    public void getSound(int sound) {
        mediaPlayer = MediaPlayer.create(this, UtilValues.moodMediaArray[moodIndex]);
        mediaPlayer.start();
    }

    private void  scheduleAlarm() {
        //Schedules alarm to be called at midnight each night
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, 1);

        Intent alarmIntent = new Intent(this, MoodReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //AlarmManager is triggered when the time specified in the scheduleAlarm method is reached

        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                mPendingIntent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //Method is unused in this project, but is necessary to add to avoid errors with relation to
        //GestureDetector compatibility
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //Method is unused in this project, but is necessary to add to avoid errors with relation to
        //GestureDetector compatibility
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //Method is unused in this project, but is necessary to add to avoid errors with relation to
        //GestureDetector compatibility
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //Method is unused in this project, but is necessary to add to avoid errors with relation to
        //GestureDetector compatibility
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //Method is unused in this project, but is necessary to add to avoid errors with relation to
        //GestureDetector compatibility
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: started");
        if (moodIndex < 4 && e1.getY() - e2.getY() > 50) {
            moodIndex++;

            mSmileyFace.setImageResource(UtilValues.moodImagesArray[moodIndex]);
            mRelativeLayout.setBackgroundColor(getResources().getColor(UtilValues.moodColorsArray[moodIndex]));
            SharedPrefsKeys.saveCurrentMood(moodIndex, currentDay, mSharedPrefs);
            getSound(moodIndex);
            //SharedPrefsKey class saveCurrentMood called to save the moodIndex in Shared Preferences
            // along with the current day. The UI is updated to the respective image and color and
            //a toast is displayed acknowledging the direction the user swiped
            Toast.makeText(MainActivity.this, "You swiped up :D",
                    Toast.LENGTH_SHORT).show(); return true;

        } else if (moodIndex > 0 && e2.getY() - e1.getY() > 50) {
            Log.d(TAG, "onFling: called");
            moodIndex--;

            mSmileyFace.setImageResource(UtilValues.moodImagesArray[moodIndex]);
            mRelativeLayout.setBackgroundColor(getResources().getColor(UtilValues.moodColorsArray[moodIndex]));
            SharedPrefsKeys.saveCurrentMood(moodIndex, currentDay, mSharedPrefs);
            getSound(moodIndex);
            //SharedPrefsKey class saveCurrentMood called to save the moodIndex in Shared Preferences
            // along with the current day. The UI is updated to the respective image and color and
            //a toast is displayed acknowledging the direction the user swiped
            Toast.makeText(MainActivity.this, "You swiped down :/",
                    Toast.LENGTH_SHORT).show(); return true;

        } else {

            return true;
        }
    }
}

