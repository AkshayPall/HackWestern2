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


    public Song (ParseObject song) {
        int plays = (int)song.get(Song.SONG_PLAYS);
        String name = song.getString(Song.SONG_NAME);
        String artist = song.getString(Song.SONG_ARTIST);
        String Uri = song.getString(Song.SONG_URI);
    }
}
