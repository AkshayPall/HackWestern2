package memes.dank.com.hackwestern2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "H5yaDTRzUmuYHZ2PPngfqDIiV";
    private static final String TWITTER_SECRET = "u3M8OXkbC5Va2yLmCJ8v3hSAaaIajvfdPhnFRsLrwUxxc0PCbZ";


    private static final String USER_POINTS = "Points";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseFacebookUtils.initialize(this);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

//        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
//        StatusesService statusesService = twitterApiClient.getStatusesService();
//        statusesService.show(524971209851543553L, null, null, null, new Callback<Tweet>() {
//            @Override
//            public void success(Result<Tweet> result) {
//
//            }
//
//            @Override
//            public void failure(TwitterException e) {
//
//            }
//        });

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "rEopROdu5tjjipAGOzcM40l9FO89tO0CyTTrJa6v", "xG5LPIGLxpeacda1audJUZwI1dP2naXjt6JsKfHl");

        ParseUser.logInInBackground("John Cena", "password", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                //query points, song plays

                Log.wtf("User Name", ""+ParseUser.getCurrentUser().getUsername());
                Log.wtf("User Points", ""+ParseUser.getCurrentUser().get(USER_POINTS));

                final List<Song> songs = new ArrayList<Song>();

                ParseQuery<ParseObject> songQuery = ParseQuery.getQuery(Song.CLASS_SONG);
                songQuery.setLimit(3);
                songQuery.addDescendingOrder(Song.SONG_PLAYS);
                songQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null){
                            for (int i = 0; i < objects.size() ; i++){
                                ParseObject song = objects.get(i);
                                songs.add(new Song(song));
                                Log.wtf("Song Info", "Plays: "+song.get(Song.SONG_PLAYS)+", "+song.getString(Song.SONG_NAME)+" by "+song.getString(Song.SONG_ARTIST));
                            }

                            RecyclerView songsList = (RecyclerView)findViewById(R.id.song_recyclerview);
                            songsList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            songsList.setAdapter(new SongAdapter(songs));
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
