package udacityscholarship.rada.raul.musicapp;

//todo store position onSaveInstanceState. it's the only one I really need

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private TextView songPositionTextView;
    private TextView artistTextView;
    private TextView titleTextView;
    private TextView genreTextView;
    private TextView yearTextView;

    private int position;
    private Song currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setup();
    }

    /**
     * coordinates the initialization of variables necessary for the activity
     * and populate activity layout
     */
    private void setup() {
        initializeVariables();
        populateLayout(currentSong);
    }

    /**
     * initializes variables necessary for the app
     */
    private void initializeVariables() {
        position = getIntent().getIntExtra("SONG_POSITION", 0);
        currentSong = PlaylistActivity.songs.get(position);
        songPositionTextView = (TextView) findViewById(R.id.song_position_text_view_details);
        artistTextView = (TextView) findViewById(R.id.artist_text_view_details);
        titleTextView = (TextView) findViewById(R.id.title_text_view_details);
        genreTextView = (TextView) findViewById(R.id.genre_text_view_details);
        yearTextView = (TextView) findViewById(R.id.year_text_view_details);
    }

    /**
     * populates activity layout
     */
    private void populateLayout(Song cSong) {
        int index = PlaylistActivity.songs.indexOf(cSong);
        songPositionTextView.setText(String.valueOf(index+1));
        artistTextView.setText(cSong.getSongArtist());
        titleTextView.setText(cSong.getSongTitle());
        genreTextView.setText(cSong.getSongGenre());
        yearTextView.setText(String.valueOf(cSong.getSongYear()));
    }

}
