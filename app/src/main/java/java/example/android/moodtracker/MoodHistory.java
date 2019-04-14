package java.example.android.moodtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

//Initiating functionality of the RecyclerView/Adapter in the History screen of the app (mood_history.xml)
public class MoodHistory extends AppCompatActivity {

    private RecyclerView rvMoods;
    private MoodAdapter moodAdapter;

    private SharedPreferences mSharedPrefs;
    private int currentDay;

    //Values which will correspond to the positions of the moods and colors array in the UtilValues class.
    //Saved in Shared Preferences as that days mood along with its comment and then added to the adapter
    private ArrayList<Integer> moods = new ArrayList<>();
    private ArrayList<String> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        currentDay = mSharedPrefs.getInt(SharedPrefsKeys.KEY_CURRENT_DAY, 1);

        //Identifying the xml file ID for the RecyclerView and its layout manager (linear, in this case)
        rvMoods = findViewById(R.id.recyclerview_mood_list);
        rvMoods.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, true));

        // 'i' being the beginning value and 'dayIndex' being the number of items represented in the RecyclerView
        //based on the current day of the week
        for (int i = 0; i < currentDay; i++) {
            moods.add(mSharedPrefs.getInt("KEY_MOOD" + i, 3));
            //Adds that days mood and comment as a new item to the adapter
            comments.add(mSharedPrefs.getString("KEY_COMMENT" + i, ""));
        }

        //Creating a new instance of the adapter class and setting it as the adapter for the specified RecyclerView
        moodAdapter = new MoodAdapter(this, moods, comments);
        rvMoods.setAdapter(moodAdapter);
    }
}
