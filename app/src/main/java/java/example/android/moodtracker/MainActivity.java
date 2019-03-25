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

public class MainActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private static final String TAG = "Gestures";
    private GestureDetector mDetector;
    private ImageView mSmileyFace;
    private RelativeLayout mRelativeLayout;
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private MediaPlayer mediaPlayer;

    private SharedPreferences mSharedPrefs;
    private String todaysComment;
    private int moodIndex;
    private int currentDay;

    public static final int [] [] moodList = {
            {R.color.faded_red,
                    R.color.warm_grey,
                    R.color.cornflower_blue_65,
                    R.color.light_sage,
                    R.color.banana_yellow},
            {R.drawable.mood_sad,
            R.drawable.mood_kind_of_sad,
            R.drawable.mood_neutral,
            R.drawable.mood_happy,
            R.drawable.mood_ecstatic}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSmileyFace = findViewById(R.id.imageView1);
        mRelativeLayout = findViewById(R.id.main_menu_relative_layout);
        ImageButton mHistory = findViewById(R.id.moodHistory);

        mDetector = new GestureDetector (this, this);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        currentDay = mSharedPrefs.getInt(SharedPrefsKeys.KEY_CURRENT_DAY, 1);
        moodIndex = mSharedPrefs.getInt(SharedPrefsKeys.KEY_CURRENT_MOOD, 3);
        todaysComment = mSharedPrefs.getString(SharedPrefsKeys.KEY_CURRENT_COMMENT, "");


        ImageButton btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new OnClickListener() {
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

        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MoodHistory.class);
                startActivity(intent);
            }
        });
    }


    public void getSound(int sound) {
        mediaPlayer = MediaPlayer.create(this, sound);
        mediaPlayer.start();
    }

    private void  scheduleAlarm(Context context) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, 1);

        Intent alarmIntent = new Intent(context, MoodReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        mAlarmManager.setInexactRepeating(AlarmManager
                        .RTC_WAKEUP,
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
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: started");

        if (moodIndex < 4 && e1.getY() - e2.getY() > 50) {
            moodIndex++;

            mSmileyFace.setImageResource(moodList[1][moodIndex]);
            mRelativeLayout.setBackgroundColor(getResources().getColor(moodList[0][moodIndex]));

            SharedPrefsKeys.saveCurrentMood(moodIndex, currentDay, mSharedPrefs);
//            getSound();

            Toast.makeText(MainActivity.this, "You swiped up :D",
                    Toast.LENGTH_SHORT).show(); return true;

        } else if (moodIndex > 0 && e2.getY() - e1.getY() > 50) {

            Log.d(TAG, "onFling: called");
            moodIndex--;

            mSmileyFace.setImageResource(moodList[1][moodIndex]);
            mRelativeLayout.setBackgroundColor(getResources().getColor(moodList[0][moodIndex]));

            SharedPrefsKeys.saveCurrentMood(moodIndex, currentDay, mSharedPrefs);
//            getSound();

            Toast.makeText(MainActivity.this, "You swiped down :/",
                    Toast.LENGTH_SHORT).show(); return true;


        } else {

            return true;

        }
    }
}

