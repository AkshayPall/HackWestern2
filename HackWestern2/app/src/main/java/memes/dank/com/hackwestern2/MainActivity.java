package memes.dank.com.hackwestern2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "rEopROdu5tjjipAGOzcM40l9FO89tO0CyTTrJa6v", "xG5LPIGLxpeacda1audJUZwI1dP2naXjt6JsKfHl");

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.show();

        ParseUser.logInInBackground("John Cena", "password", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                //query points, song plays

                Log.wtf("User Name", ""+ParseUser.getCurrentUser().getUsername());
                Log.wtf("User Points", ""+ParseUser.getCurrentUser().get(USER_POINTS));

                TextView pointsView = (TextView)findViewById(R.id.user_points);
                pointsView.setText("Points: "+ParseUser.getCurrentUser().get(USER_POINTS));

                TextView username = (TextView)findViewById(R.id.user_name);
                username.setText(ParseUser.getCurrentUser().getUsername());

                final List<Song> songs = new ArrayList<Song>();

                final RecyclerView songsList = (RecyclerView)findViewById(R.id.song_recyclerview);
                songsList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                songsList.setVisibility(View.INVISIBLE);

                final RecyclerView tweetsList = (RecyclerView)findViewById(R.id.hashtag_recyclerview);
                tweetsList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                tweetsList.setVisibility(View.INVISIBLE);

                //TODO: query the hashtags
                final List<String> hashtags = new ArrayList<String>(); //fake data
                hashtags.add("SyrianRefugees");
                hashtags.add("DonaldTrump");
                hashtags.add("JustNotReadyTrudeau");


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

                            songsList.setVisibility(View.VISIBLE);
                            songsList.setAdapter(new SongAdapter(songs));

                            tweetsList.setVisibility(View.VISIBLE);
                            tweetsList.setAdapter(new TwitterAdapter(hashtags));

                            dialog.dismiss();
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
        } else if (id == R.id.action_event){
            final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading...");
            dialog.show();

            //load location event info
            ParseQuery<ParseObject> locationQuery = ParseQuery.getQuery("Location");
            locationQuery.setLimit(1);
            locationQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    dialog.dismiss();
                    if (e == null && objects.size() > 0) {
                        Location currentLocation = new Location(objects.get(0));
                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(MainActivity.this);
                        dialog1.setTitle("Message for "+currentLocation.getName());
                        dialog1.setMessage(currentLocation.getMessage());
                        dialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog1.show();
                    }
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }
}
