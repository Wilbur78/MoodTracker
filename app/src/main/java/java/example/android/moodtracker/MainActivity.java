package java.example.android.moodtracker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
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

import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private static final String TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private ImageView mSmileyFace;
    private RelativeLayout mRelativeLayout;
    private ImageButton mHistory;
    public static String comment;
    public static final int [] [] moodList = {
            {R.drawable.mood_sad,
            R.drawable.mood_kind_of_sad,
            R.drawable.mood_neutral,
            R.drawable.mood_happy,
            R.drawable.mood_ecstatic},
            {R.color.faded_red,
            R.color.warm_grey,
            R.color.cornflower_blue_65,
            R.color.light_sage,
            R.color.banana_yellow}};
    public static int moodIndex = 3;
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSmileyFace = findViewById(R.id.imageView1);
        mRelativeLayout = findViewById(R.id.main_menu_relative_layout);
        mHistory = findViewById(R.id.moodHistory);
        mDetector = new GestureDetectorCompat(this, this);


        mSettings = getApplicationContext().getSharedPreferences("Preference", MODE_PRIVATE);

        mSettings.registerOnSharedPreferenceChangeListener(
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        mSettings.getInt("Preference", moodIndex);
                    }
                }
        );

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
                                comment = editText.getText().toString();
                                dialog.dismiss();

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
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, moodHistory.class);
                startActivity(intent);
            }
        });
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

    private void prefEdit() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt("Mood Index Value", moodIndex);
        editor.apply();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: started");

        if (moodIndex < 4 && e1.getY() - e2.getY() > 50) {
            moodIndex++;
            mSmileyFace.setImageResource(moodList[1][moodIndex]);
            mRelativeLayout.setBackgroundColor(getResources().getColor(moodList[0][moodIndex]));

            prefEdit();

            Toast.makeText(MainActivity.this, "You swiped up :D",
                    Toast.LENGTH_LONG).show(); return true;

        } else if (moodIndex > 0 && e2.getY() - e1.getY() > 50) {

            Log.d(TAG, "onFling: called");
            moodIndex--;
            mSmileyFace.setImageResource(moodList[1][moodIndex]);
            mRelativeLayout.setBackgroundColor(getResources().getColor(moodList[0][moodIndex]));

            prefEdit();

            Toast.makeText(MainActivity.this, "You swiped down :/",
                    Toast.LENGTH_LONG).show(); return true;

        } else {
            return true;
        }
    }

    public void setComment() {
        comment = "";
    }
}

