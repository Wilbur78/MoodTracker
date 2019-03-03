package java.example.android.moodtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.preference.PreferenceManager;
import  com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static java.example.android.moodtracker.MainActivity.moodIndex;

public class MoodHistory extends AppCompatActivity {

    public static final int NUMBER_ITEM = 8;
    public static RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public int mPreferences;
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private static final String MOOD_DATA = "MOOD_DATA";

    public static ArrayList<ListMoodItem> mListMoodItem = new ArrayList<>();

    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        mRecyclerView = findViewById(R.id.recycler_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView.setHasFixedSize(true);

        preferences.getInt("Mood Index Value", moodIndex);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(mPreferences);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static void saveData(Context activity, ListMoodItem moodItem) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(moodItem);
        editor.putString(MOOD_DATA, json);
        editor.apply();
    }

    public static void loadData(Context activity, ListMoodItem moodItem) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MOOD_DATA, null);
        Type type = new TypeToken<ArrayList<ListMoodItem>>() {
        }.getType();
        mListMoodItem = gson.fromJson(json, type);

        if (mListMoodItem == null) {
            mListMoodItem = new ArrayList<>();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public class MyAdapter extends
            RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        int preferences;

        class MyViewHolder extends
                RecyclerView.ViewHolder {
            LinearLayout mLinearLayout;

            MyViewHolder(LinearLayout v) {
                super(v);
                mLinearLayout = v;
            }
        }

        MyAdapter(int preferences) {
            preferences = mPreferences;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.mLinearLayout.setBackgroundColor(getResources().getColor(R.color.warm_grey));
        }

        @Override
        public int getItemCount() {
            return mPreferences;
        }
    }
}
