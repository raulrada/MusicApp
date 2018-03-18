package udacityscholarship.rada.raul.musicapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private TextView songPositionTextView;
    private TextView artistTextView;
    private TextView titleTextView;
    private TextView genreTextView;
    private TextView yearTextView;
    private ImageButton playImageButton;
    private ImageButton rewindImageButton;
    private ImageButton backFirstImageButton;
    private ImageButton forwardImageButton;
    private ImageButton forwardLastImageButton;
    private ImageButton pauseImageButton;
    private Button backToPlaylistButton;
    private String spinnerSelection;

    private int position;
    private int [] userOrder;
    private boolean isPlaying;
    private String isRandomized;
    private Song currentSong;

    private static final String KEY_POSITION = "current_position";
    private static final String KEY_IS_PLAYING = "is_playing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setup();

        //When user presses the play button for a song in PlaylistActivity, and launches DetailsActivity,
        //the song is playing
        if (savedInstanceState == null){
            isPlaying = true;
            playVisibility(isPlaying);
        }

        playImageButton.setOnClickListener(new View.OnClickListener() {
            /**
             * OnClickListener for play ImageButton, to determine that current song is playing
             * when clicked, the play button becomes invisible and the pause button, visible
             * @param v
             */
            @Override
            public void onClick(View v){
                isPlaying = true;
                playVisibility(isPlaying);
            }
        });

        pauseImageButton.setOnClickListener(new View.OnClickListener() {
            /**
             * OnClickListener for pause ImageButton, to determine that current song is not playing
             * when clicked, the pause button becomes invisible and the play button, visible
             * @param v
             */
            @Override
            public void onClick(View v) {
                isPlaying = false;
                playVisibility(isPlaying);
            }
        });

        rewindImageButton.setOnClickListener(new View.OnClickListener() {
            /**
             * OnClickListener for rewind ImageButton. Moves to previous song if there is a
             * previous song, and updates the details on screen for previous song.
             * The song about which details are displayed starts playing (the play ImageButton is
             * hidden and the pause ImageButton is displayed in its stead).
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (position > 0){
                    position--;
                    currentSong = PlaylistActivity.songs.get(position);
                    populateLayout(currentSong);
                    isPlaying = true;
                    playVisibility(isPlaying);
                }
            }
        });

        forwardImageButton.setOnClickListener(new View.OnClickListener() {
            /**
             * OnClickListener for forward ImageButton. Moves to next song if there is a
             * previous song, and updates the details on screen for next song.
             * The song about which details are displayed starts playing (the play ImageButton is
             * hidden and the pause ImageButton is displayed in its stead).
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (position < (PlaylistActivity.songs.size()-1)){
                    position++;
                    currentSong = PlaylistActivity.songs.get(position);
                    populateLayout(currentSong);
                    isPlaying = true;
                    playVisibility(isPlaying);
                }
            }
        });

        backFirstImageButton.setOnClickListener(new View.OnClickListener() {
            /**
             * OnClickListener for back to first song ImageButton. Moves to first song,
             * and updates the details on screen for first song.
             * The song about which details are displayed starts playing (the play ImageButton is
             * hidden and the pause ImageButton is displayed in its stead).
             * @param v
             */
            @Override
            public void onClick(View v) {
                //if not already at the first song, consider first song starts playing after
                //pressing the backFirstImageButton. Otherwise simply keep the current play/pause status.
                if(position != 0){
                    isPlaying = true;
                    playVisibility(isPlaying);
                }
                position = 0;
                currentSong = PlaylistActivity.songs.get(position);
                populateLayout(currentSong);
            }
        });

        forwardLastImageButton.setOnClickListener(new View.OnClickListener() {
            /**
             * OnClickListener for forward to last song ImageButton. Moves to last song,
             * and updates the details on screen for last song.
             * The song about which details are displayed starts playing (the play ImageButton is
             * hidden and the pause ImageButton is displayed in its stead).
             * @param v
             */
            @Override
            public void onClick(View v) {
                //if not already at the last song, consider last song starts playing after
                //pressing the forwardLastImageButton. Otherwise simply keep the current play/pause status.
                if(position != (PlaylistActivity.songs.size()-1)){
                    isPlaying = true;
                    playVisibility(isPlaying);
                }
                position = PlaylistActivity.songs.size()-1;
                currentSong = PlaylistActivity.songs.get(position);
                populateLayout(currentSong);
            }
        });

        backToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startPlaylistActivity = new Intent(DetailsActivity.this, PlaylistActivity.class);
                startPlaylistActivity.putExtra("USER_PREFERRED_ORDER", userOrder);
                startPlaylistActivity.putExtra("SPINNER_SELECTION", spinnerSelection);
                startPlaylistActivity.putExtra("IS_RANDOMIZED", isRandomized);
                startActivity(startPlaylistActivity);
            }
        });

    }

    /**
     * when song is playing, make play button invisible and pause button visible,
     * and vice versa when song is not playing
     * @param isPlaying boolean variable which is true when song is playing
     */
    public void playVisibility(boolean isPlaying) {
        if(isPlaying){
            playImageButton.setVisibility(View.INVISIBLE);
            pauseImageButton.setVisibility(View.VISIBLE);
        }
        else{
            playImageButton.setVisibility(View.VISIBLE);
            pauseImageButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * coordinates the initialization of variables necessary for the activity
     * and populate activity layout
     */
    public void setup() {
        initializeVariables();
        populateLayout(currentSong);
    }

    /**
     * initializes variables necessary for the app
     */
    public void initializeVariables() {
        position = getIntent().getIntExtra("SONG_POSITION", 0);
        userOrder = getIntent().getIntArrayExtra("USER_PREFERRED_ORDER");
        spinnerSelection = getIntent().getStringExtra("SPINNER_SELECTION");
        isRandomized = getIntent().getStringExtra("IS_RANDOMIZED");
        Log.v("raulrrr", "isRandomized: "+isRandomized);
        currentSong = PlaylistActivity.songs.get(position);
        songPositionTextView = (TextView) findViewById(R.id.song_position_text_view_details);
        artistTextView = (TextView) findViewById(R.id.artist_text_view_details);
        titleTextView = (TextView) findViewById(R.id.title_text_view_details);
        genreTextView = (TextView) findViewById(R.id.genre_text_view_details);
        yearTextView = (TextView) findViewById(R.id.year_text_view_details);
        playImageButton = (ImageButton) findViewById(R.id.play_image_button);
        rewindImageButton = (ImageButton) findViewById(R.id.rewind_image_button);
        backFirstImageButton = (ImageButton) findViewById(R.id.back_first_image_button);
        forwardImageButton = (ImageButton) findViewById(R.id.forward_image_button);
        forwardLastImageButton = (ImageButton) findViewById(R.id.forward_last_image_button);
        pauseImageButton = (ImageButton)findViewById(R.id.pause_image_button);
        backToPlaylistButton = (Button) findViewById(R.id.back_playlist_button);
    }

    /**
     * populates activity layout
     */
    public void populateLayout(Song cSong) {
        int index = PlaylistActivity.songs.indexOf(cSong);
        songPositionTextView.setText(String.valueOf(index+1));
        artistTextView.setText(cSong.getSongArtist());
        titleTextView.setText(cSong.getSongTitle());
        genreTextView.setText(cSong.getSongGenre());
        yearTextView.setText(String.valueOf(cSong.getSongYear()));
    }

    /**
     * save value of selected variables for preserving state of displayed layout.
     * @param savedInstanceState bundle object
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(KEY_POSITION, position);
        savedInstanceState.putBoolean(KEY_IS_PLAYING, isPlaying);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * restore value of selected variables and update displayed layout.
     * @param savedInstanceState bundle object
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt(KEY_POSITION);
        isPlaying = savedInstanceState.getBoolean(KEY_IS_PLAYING);
        playVisibility(isPlaying);
        currentSong = PlaylistActivity.songs.get(position);
        populateLayout(currentSong);
    }

}
