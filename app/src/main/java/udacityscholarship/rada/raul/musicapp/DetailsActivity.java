package udacityscholarship.rada.raul.musicapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private TextView songPositionTextView;
    private int position;
    private Song currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setup();

//        ArrayList<Song> t = new ArrayList<>();
//        t = PlaylistActivity.songs;
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
    }

    /**
     * populates activity layout
     */
    private void populateLayout(Song cSong) {
        songPositionTextView.setText(String.valueOf(PlaylistActivity.songs.indexOf(cSong)+1));
    }

}
