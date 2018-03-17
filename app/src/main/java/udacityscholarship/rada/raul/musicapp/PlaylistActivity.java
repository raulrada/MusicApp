package udacityscholarship.rada.raul.musicapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Random;

public class PlaylistActivity extends AppCompatActivity {

    public static ArrayList<Song> songs; //static to make it available in other classes,
                                         // without instantiating PlaylistActivity

    private ArrayList<Song> rawSongsList; //list of songs, not necessarily in the order in which
                                          //the user wants to see them

    public static int [] userOrder; //array keeping track of the order in which the songs in rawSongsList
                              //should be displayed based on user preferences

    private static final int SUSPICIOUS_MINDS_RELEASE_DATE = 1969;
    private static final int DON_T_RELEASE_DATE = 1957;
    private static final int JUST_PRETEND_RELEASE_DATE = 1970;
    private static final int BURNING_LOVE_RELEASE_DATE = 1973;
    private static final int CASI_HUMANOS_RELEASE_DATE = 2017;
    private static final int CASTLE_RELEASE_DATE = 2017;
    private static final int HURTS_RELEASE_DATE = 2016;
    private static final int ALWAYS_RELEASE_DATE = 1994;
    private static final int MODERN_TIMES_RELEASE_DATE = 2004;
    private static final int DEJA_SER_RELEASE_DATE = 2016;
    private static final int WE_COME_1_RELEASE_DATE = 2001;
    private static final int STILL_DRE_RELEASE_DATE = 2001;

    public static final String KEY_USER_ORDER = "userOrderKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        setup();
    }

    /**
     * coordinates the initialization of variables necessary for the activity
     * and populate activity layout
     */
    public void setup(){
        getSupportActionBar().setTitle(R.string.available_songs); //set title for activity
        generateRawSongs();

        //if PlayList activity is launched through intent from DetailsActivity, restore pre-existing
        //order. Otherwise (i.e., on first run of PlaylistActivity), initialize the songs order.
        if (getIntent().getIntArrayExtra("USER_PREFERRED_ORDER") != null){
            userOrder = getIntent().getIntArrayExtra("USER_PREFERRED_ORDER");
        }
        else {
            initializeSongsOrder();
        }

        generateSongs();
        populateList();
    }

    /**
     * initializes the userOrder array. The initial values reflect the default order of songs, being
     * the order in which the songs are included in rawSongsList.
     */
    private void initializeSongsOrder() {
        userOrder = new int[rawSongsList.size()];
        for (int i = 0; i < rawSongsList.size(); i++){
            userOrder[i] = i;
        }
        userOrder = shuffleArray(userOrder);
    }

    /**
     * display list of available songs in ListView powered by custom SongAdapter
     */
    private void populateList() {
        ListView lv = (ListView) findViewById(R.id.songs_list_view);
        SongAdapter songAdapter = new SongAdapter(this, songs);
        lv.setAdapter(songAdapter);
    }

    /**
     * create ArrayList of raw songs
     */
    private void generateRawSongs() {
        rawSongsList = new ArrayList<Song>();
        rawSongsList.add(new Song(getString(R.string.suspicious_minds), getString(R.string.elvis), getString(R.string.rock_roll), SUSPICIOUS_MINDS_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.dont), getString(R.string.elvis), getString(R.string.rock_roll), DON_T_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.just_pretend), getString(R.string.elvis), getString(R.string.rock_roll), JUST_PRETEND_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.burning_love), getString(R.string.elvis), getString(R.string.rock_roll), BURNING_LOVE_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.casi_humanos), getString(R.string.dvicio), getString(R.string.rock), CASI_HUMANOS_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.castle), getString(R.string.ed_sheeran), getString(R.string.dance), CASTLE_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.hurts), getString(R.string.emeli), getString(R.string.dance), HURTS_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.always), getString(R.string.erasure), getString(R.string.dance), ALWAYS_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.modern_times), getString(R.string.j_five), getString(R.string.dance), MODERN_TIMES_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.deja_ser), getString(R.string.afgo), getString(R.string.house), DEJA_SER_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.we_come_1), getString(R.string.faithless), getString(R.string.house), WE_COME_1_RELEASE_DATE));
        rawSongsList.add(new Song(getString(R.string.still_dre), getString(R.string.dr_dre), getString(R.string.hip_hop), STILL_DRE_RELEASE_DATE));
    }

    /**
     * create ArrayList with Song objects, in the order chosen by user, using as source the rawSongsList
     */
    private void generateSongs() {
        songs = new ArrayList<Song>();
        for (int i = 0; i < rawSongsList.size(); i++){
            songs.add(i, rawSongsList.get(userOrder[i]));
        }
    }

    /**
     * Shuffles elements in provided int array based on the Fisher - Yates shuffle
     * @param values array whose elements shall be shuffled
     * @return array of shuffled elements
     */
    public int [] shuffleArray(int [] values) {
        Random rand = new Random();
        for (int i = values.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = values[i];
            values[i] = values[index];
            values[index] = temp;
        }
        return values;
    }

    /**
     * save value of selected variables for preserving state of displayed layout.
     * @param savedInstanceState bundle object
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putIntArray(KEY_USER_ORDER, userOrder);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * restore value of selected variables and update displayed layout.
     * @param savedInstanceState bundle object
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userOrder = savedInstanceState.getIntArray(KEY_USER_ORDER);
    }

}
