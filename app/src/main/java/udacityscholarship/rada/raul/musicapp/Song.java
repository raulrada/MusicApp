package udacityscholarship.rada.raul.musicapp;

/**
 * Represents a single song.
 * Each object has 4 properties: song title, song artist, song genre and song release year.
 */

public class Song {

    //song title
    private String mSongTitle;

    //song artist
    private String mSongArtist;

    //song genre
    private String mSongGenre;

    //song release year
    private int mSongYear;

    /**
     * constructor for Song object
     * @param title title of the song
     * @param artist artist performing the song
     * @param genre genre of the song
     * @param year when the song was released
     */
    public Song (String title, String artist, String genre, int year){
        mSongTitle = title;
        mSongArtist = artist;
        mSongGenre = genre;
        mSongYear = year;
    }

    /**
     * get song title
     * @return song title
     */
    public String getSongTitle(){
        return mSongTitle;
    }

    /**
     * get artist singing the song
     * @return artist name
     */
    public String getSongArtist(){
        return mSongArtist;
    }

    /**
     * get song genre
     * @return genre of the song
     */
    public String getSongGenre(){
        return mSongGenre;
    }

    /**
     * get the year when the song was released
     * @return year the song was released
     */
    public int getSongYear(){
        return mSongYear;
    }

}
