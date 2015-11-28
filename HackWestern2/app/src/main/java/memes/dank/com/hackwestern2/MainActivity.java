package memes.dank.com.hackwestern2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_POINTS = "Points";

    //for the song class
    private static final String CLASS_SONG = "Song";
    private static final String SONG_PLAYS = "Plays";
    private static final String SONG_NAME = "Name";
    private static final String SONG_ARTIST = "Artist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "rEopROdu5tjjipAGOzcM40l9FO89tO0CyTTrJa6v", "xG5LPIGLxpeacda1audJUZwI1dP2naXjt6JsKfHl");

        ParseUser.logInInBackground("John Cena", "password", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                //TODO: query points, song plays

                //logging parse data

                Log.wtf("User Name", ""+ParseUser.getCurrentUser().getUsername());
                Log.wtf("User Points", ""+ParseUser.getCurrentUser().get(USER_POINTS));

                ParseQuery<ParseObject> songQuery = ParseQuery.getQuery(CLASS_SONG);
                songQuery.setLimit(3);
                songQuery.addDescendingOrder(SONG_PLAYS);
                songQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null){
                            for (int i = 0; i < objects.size() ; i++){
                                ParseObject song = objects.get(i);
                                Log.wtf("Song Info", "Plays: "+song.get(SONG_PLAYS)+", "+song.get(SONG_NAME)+" by "+song.get(SONG_ARTIST));
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
