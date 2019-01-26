package java.example.android.moodtracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.example.android.moodtracker.RecyclerView.Recycler;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener {

    private static final String TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private ImageView mSmileyFace;
    public String comment;
    private int [] moodList = {
            R.drawable.mood_ecstatic,
            R.drawable.mood_happy,
            R.drawable.mood_neutral,
            R.drawable.mood_kind_of_sad,
            R.drawable.mood_sad};
    private int moodIndex;
    private static final int NUM_LIST_ITEMS = 100;
    private Recycler mAdapter;
    private RecyclerView mNumbersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDetector = new GestureDetectorCompat(this,this);

        mSmileyFace = (ImageView) findViewById(R.id.imageView1);

//        RecyclerView mNumbersList = findViewById(R.id.rv_numbers);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mNumbersList.setLayoutManager(layoutManager);
//
//        mNumbersList.setHasFixedSize(true);
//
//        mAdapter = new Recycler(NUM_LIST_ITEMS);
//
//        mNumbersList.setAdapter(mAdapter);

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

        ImageButton imageButton = (ImageButton) findViewById(R.id.moodHistory);
        final Context context = this;
        imageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent SharedPreferences = new Intent(context, MainActivity.class);
                startActivity(SharedPreferences);
            }
        });
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(TAG,"onDown: ?" + event.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(TAG, "onLongPress: ?" + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        Log.d(TAG, "onScroll: ?" + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(TAG, "onShowPress: ?" + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(TAG, "onSingleTapUp: ?" + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {

        Log.d(TAG, "onFling: started");

        if(moodIndex < 4 && e1.getY() - e2.getY() > 50) {
            moodIndex++;

            mSmileyFace.setImageResource(moodIndex);


            Toast.makeText(MainActivity.this, "You swiped up :D",
                    Toast.LENGTH_LONG). show();

            return true;

        } else if (moodIndex > 0 && e2.getY() - e1.getY() > 50) {

            Log.d(TAG, "onFling: called");
            moodIndex--;

            Toast.makeText(MainActivity.this, "You swiped down :/",
                    Toast.LENGTH_LONG). show();

            return true;
        } else {

            return true;
        }
    }
}

