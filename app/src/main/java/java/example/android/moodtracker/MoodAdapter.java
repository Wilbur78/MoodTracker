package java.example.android.moodtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//This class accompanies the actual RecyclerView class and outlines the adapter's functionality
public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MoodViewHolder> {

    //Instantiating the context and the int/string array lists (mood, comment) to correspond with the data
    // represented in the RecyclerView
    private Context mContext;
    private ArrayList<Integer> mMoodsArray;
    private ArrayList<String> mCommentsArray;

    MoodAdapter(Context context, ArrayList<Integer> moods, ArrayList<String> comments) {
        //Calling the values instantiated above and specifying the context thereof so they can be applied
        // in the new adapter when created
        this.mContext = context;
        this.mMoodsArray = moods;
        this.mCommentsArray = comments;
    }

    @NonNull
    @Override
    public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_display, viewGroup, false);
        return new MoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoodViewHolder mvh, int daysAgo) {
        switch (daysAgo) {
            //Accompanying text to be displayed within each layout specifying when that item was
            //added: 'today', 'yesterday', 'five days ago', etc.
            case 0: String today = "Today";
                mvh.daysAgoText.setText(today);
                break;
            case 1: String yesterday = "Yesterday";
                mvh.daysAgoText.setText(yesterday);
                break;
            default: String daysAgoText = daysAgo + " days ago";
                mvh.daysAgoText.setText(daysAgoText);
        }

        int mood = mMoodsArray.get(daysAgo);
        float weight;

        LinearLayout.LayoutParams leftWeight = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams rightWeight = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);

        switch (mood) {
            //Defines what portion of the layout attributed to each adapter item will be filled
            //from the left of the screen to the right (indicated by the following float values)
            case 0: weight = 0.2f;
                break;
            case 1: weight = 0.4f;
                break;
            case 2: weight = 0.6f;
                break;
            case 3: weight = 0.8f;
                break;
            case 4: weight = 1.0f;
                break;
            default: weight = 0.8f;
        }

        //Actual setting of the float value/weighting of the left/right linear layout
        //parameters specified in the above method
        leftWeight.weight = weight;
        rightWeight.weight = 1.0f - weight;
        mvh.frameLayoutLeft.setLayoutParams(leftWeight);
        mvh.frameLayoutRight.setLayoutParams(rightWeight);
        mvh.frameLayoutLeft.setBackgroundResource(UtilValues.moodColorsArray[mood]);

        //If the 'comment' value is neither NULL nor empty, the comment button in the viewholder
        //is made visible, which (in the OnClick) displays a toast method containing the users
        //comment for that day; otherwise the button remains invisible
        final String savedComment = mCommentsArray.get(daysAgo);
        if (savedComment != null && !savedComment.isEmpty()) {
            mvh.commentButton.setVisibility(View.VISIBLE);
            mvh.commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, savedComment, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            mvh.commentButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        //Necessary override of the getItemCount method which returns the number of items
        //currently in the RecyclerView
        return mMoodsArray.size();
    }

    class MoodViewHolder extends RecyclerView.ViewHolder {

        //Creating the ViewHolder class and defining its components to be referenced
        //throughout the rest of the file
        private FrameLayout frameLayoutLeft;
        private FrameLayout frameLayoutRight;
        private ImageButton commentButton;
        private TextView daysAgoText;

        MoodViewHolder(View displayItem) {
            super(displayItem);

            //Specifying the ID's of the above instantiated elements of the ViewHolder
            frameLayoutLeft = displayItem.findViewById(R.id.frame_layout_left);
            frameLayoutRight = displayItem.findViewById(R.id.frame_layout_right);
            commentButton = displayItem.findViewById(R.id.btn_show_comment);
            daysAgoText = displayItem.findViewById(R.id.textview_days_ago);
        }
    }
}
