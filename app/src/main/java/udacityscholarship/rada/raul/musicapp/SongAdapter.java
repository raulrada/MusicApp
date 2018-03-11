//credit goes to https://github.com/udacity/ud839_CustomAdapter_Example/blob/master/app/src/main/java/com/example/android/flavor/AndroidFlavorAdapter.java

package udacityscholarship.rada.raul.musicapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * SongAdapter is an ArrayAdapter providing the layout for a ListView containing information
 * about Song objects included in an ArrayList
 */
public class SongAdapter extends ArrayAdapter<Song> {

    private Context c; //necessary to startActivity based on Intent

    /**
     * custom constructor.
     *
     * @param context current context, used to inflate the layout file.
     * @param songs   list of Song objects used to populate the ListView
     */
    public SongAdapter(Activity context, ArrayList<Song> songs) {
        //initialize the ArrayAdapter's internal storage for the context and the list.
        //second argument is not used by my custom adapter, since I inflate layout manually.
        super(context, 0, songs);
        this.c = context;
    }

    /**
     * provides a view for a ListView
     * @param position The position in the list of data that should be displayed in the list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the ListView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;

        //if existing view is not being reused, inflate a new view
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //find TextViews & Button in list_item.xml
        TextView songPositionTextView = (TextView) listViewItem.findViewById(R.id.song_position);
        TextView songTitleTextView = (TextView) listViewItem.findViewById(R.id.song_title);
        TextView songArtistTextView = (TextView) listViewItem.findViewById(R.id.song_artist_name);
        ImageButton playButton = (ImageButton) listViewItem.findViewById(R.id.play_button_playlist);

        Song currentSong = getItem(position);

        //get data from fields of current Song object and write it in TextViews
        songPositionTextView.setText(String.valueOf(position+1)); //adjust for starting index of 0
        songTitleTextView.setText(currentSong.getSongTitle());
        songArtistTextView.setText(currentSong.getSongArtist());

        playButton.setOnClickListener(new View.OnClickListener() {
            /**
             * onClickListener for buttons in ListView
             * loosely inspired from https://stackoverflow.com/questions/40862154/how-to-create-listview-items-button-in-each-row
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent startPlay = new Intent(c, DetailsActivity.class);
                c.startActivity(startPlay);
            }
        });

        //return the list item layout to be displayed in ListView
        return listViewItem;
    }
}
