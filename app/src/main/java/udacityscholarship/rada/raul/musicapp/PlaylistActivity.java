package udacityscholarship.rada.raul.musicapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PlaylistActivity extends AppCompatActivity {

    public static ArrayList<Song> songs; //static to make it available in other classes,
                                         // without instantiating PlaylistActivity

    private ArrayList<Song> rawSongsList; //list of songs, not necessarily in the order in which
                                          //the user wants to see them.

    public static int [] userOrder; //array keeping track of the order in which the songs in rawSongsList
                                    //should be displayed based on user preferences.
                                    //it is static to make available in other classes,
                                    //without instantiating PlaylistActivity

    public static String spinnerSelection; //static to make it available in other classes,
                                           // without instantiating PlaylistActivity

    public static String isRandomized; //keeps track of whether list of songs is already randomized, to
                                       //avoid a new randomization on screen rotation.
                                       //due to Intent putExtra / getBooleanExtra implementation
                                       //limitations, boolean is not a suitable type.

    private Spinner sortSpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private String [] spinnerOptions;

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

    private static final String KEY_USER_ORDER = "userOrderKey";
    private static final String KEY_IS_RANDOMIZED = "isRandomizedKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        setup();

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelection = sortSpinner.getSelectedItem().toString();
                chooseOrder();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //auto-generated stub
            }
        });
    }

    /**
     * coordinates the initialization of variables necessary for the activity
     * and populate activity layout
     */
    public void setup(){
        getSupportActionBar().setTitle(R.string.available_songs); //set title for activity
        isRandomized = "no";

        //if PlayList activity is launched through intent from DetailsActivity, restore pre-existing
        //status of isRandomized. Otherwise (i.e., on first run of PlaylistActivity), initialize isRandomized.
        if (getIntent().getStringExtra("IS_RANDOMIZED") != null){
            isRandomized = getIntent().getStringExtra("IS_RANDOMIZED");
        }
        else {
            isRandomized = "no";
        }
        Log.v("raulrrr", "isRandomized: "+isRandomized);

        setupSpinner();

        //if PlayList activity is launched through intent from DetailsActivity, restore pre-existing
        //spinner selection.
        if (getIntent().getStringExtra("SPINNER_SELECTION") != null){
            spinnerSelection = getIntent().getStringExtra("SPINNER_SELECTION");
            sortSpinner.setSelection(Arrays.asList(spinnerOptions).indexOf(spinnerSelection));
        }

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
     * setup the spinner allowing the user to select the order in which the available songs are
     * presented in the playlist
     */
    private void setupSpinner() {
        spinnerOptions = getResources().getStringArray(R.array.options_array);
        sortSpinner = (Spinner) findViewById(R.id.sort_spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.options_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(spinnerAdapter);
        spinnerSelection = sortSpinner.getSelectedItem().toString();
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
    }

    /**
     *chooses the order in which the songs are displayed in the ListView
     */
    private void chooseOrder() {
        //switch structure difficult to implement, due to requiring constants as cases.
        //strings in spinner_options.xml (which powers the spinner) may be changed in future.
        if (spinnerOptions[0].equalsIgnoreCase(sortSpinner.getSelectedItem().toString())){
            isRandomized = "no";
            initializeSongsOrder();
            generateSongs();
            populateList();
        }

        if (spinnerOptions[1].equalsIgnoreCase(sortSpinner.getSelectedItem().toString())){
            //avoid a new randomization on screen rotation, if song list is already randomized
            if (isRandomized.equalsIgnoreCase("no")) userOrder = shuffleArray(userOrder);
            generateSongs();
            populateList();
            isRandomized = "yes";
        }
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
        savedInstanceState.putString(KEY_IS_RANDOMIZED, isRandomized);
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
        isRandomized = savedInstanceState.getString(KEY_IS_RANDOMIZED);
    }

}
