package memes.dank.com.hackwestern2;

import com.parse.ParseObject;

/**
 * Created by rohitsharma on 2015-11-28.
 */
public class Song {

    //for the song class
    public static final String CLASS_SONG = "Song";
    public static final String SONG_PLAYS = "Plays";
    public static final String SONG_NAME = "Name";
    public static final String SONG_ARTIST = "Artist";
    public static final String SONG_URI = "Uri";

    private final int mPlays;
    private final String mName;
    private final String mArtist;
    private final String mUri;


    public Song (ParseObject song) {
        mPlays = (int)song.get(Song.SONG_PLAYS);
        mName = song.getString(Song.SONG_NAME);
        mArtist = song.getString(Song.SONG_ARTIST);
        mUri = song.getString(Song.SONG_URI);
    }

    public int getPlays() {
        return mPlays;
    }

    public String getName() {
        return mName;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getUri() {
        return mUri;
    }
}
