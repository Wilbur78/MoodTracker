package java.example.android.moodtracker;

//This class contains a set of integer array groups which each hold a set of images,
// background colors, and media to go along with the users current mood as per the moodIndex
public class UtilValues {

    //An array list of moods to be accessed throughout out the app.
    // When called, the method takes an Int parameter to indentify
    // which drawable will be represented on-screen
    public static int [] moodImagesArray = {R.drawable.mood_sad,
            R.drawable.mood_kinda_sad,
            R.drawable.mood_neutral,
            R.drawable.mood_happy,
            R.drawable.mood_ecstatic};

    //An array list of colors to be displayed along with the corresponding mood
    //which is currently in use from the above Int array
    public static int [] moodColorsArray = {R.color.faded_red,
            R.color.warm_grey,
            R.color.cornflower_blue,
            R.color.light_sage,
            R.color.banana_yellow};

    //An array list of sounds/media to be played when the mood is changed
    //to a one in the same Int array position as the sound files listed below
    public static int [] moodMediaArray = {R.raw.note_sad,
            R.raw.note_disappointed, R.raw.note_normal,
            R.raw.note_happy,
            R.raw.note_super_happy};
}
