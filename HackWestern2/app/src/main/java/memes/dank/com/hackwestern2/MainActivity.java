package memes.dank.com.hackwestern2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
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

import static android.support.v4.app.ActivityCompat.startActivity;
public class MainActivity extends AppCompatActivity implements TwitterAdapter.HashtagListener, SongAdapter.SongListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "H5yaDTRzUmuYHZ2PPngfqDIiV";
    private static final String TWITTER_SECRET = "u3M8OXkbC5Va2yLmCJ8v3hSAaaIajvfdPhnFRsLrwUxxc0PCbZ";


    private static final String USER_POINTS = "Points";
    private RecyclerView MTweetsList;
    private RecyclerView MSongsList;
    private TextView mPointsView;
    private ProgressDialog mLoadingDialog;
    private TextView mUsername;
    private int mPoints;
    private TextView mTopUserName;
    private TextView mTopUserPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ParseFacebookUtils.initialize(this);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_data_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                mLoadingDialog.show();
                getData();
            }
        });

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

        mPointsView = (TextView)findViewById(R.id.user_points);

        MSongsList = (RecyclerView)findViewById(R.id.song_recyclerview);
        MSongsList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        MSongsList.setVisibility(View.INVISIBLE);

        MTweetsList = (RecyclerView)findViewById(R.id.hashtag_recyclerview);
        MTweetsList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        MTweetsList.setVisibility(View.INVISIBLE);

        mLoadingDialog = new ProgressDialog(this);
        mLoadingDialog.setTitle("Loading");
        mLoadingDialog.show();

        mTopUserName = (TextView)findViewById(R.id.top_user_name);
        mTopUserPoints = (TextView)findViewById(R.id.top_user_points);

        mUsername = (TextView)findViewById(R.id.user_name);

        ParseUser.logInInBackground("John Cena", "password", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                getData();
            }
        });
    }

    private void getData() {
//query points, song plays

        Log.wtf("User Name", ""+ParseUser.getCurrentUser().getUsername());
        Log.wtf("User Points", ""+ParseUser.getCurrentUser().get(USER_POINTS));

        mPoints = (int)ParseUser.getCurrentUser().get(USER_POINTS);

        mPointsView.setText("Points: "+mPoints);

        mUsername.setText(ParseUser.getCurrentUser().getUsername());

        final List<Song> songs = new ArrayList<Song>();


        //TODO: query the hashtags
        final List<String> hashtags = new ArrayList<String>(); //fake data
        hashtags.add("WeLoveYouCalum");
        hashtags.add("BrouisIsFake");
        hashtags.add("ClimateMarch");

        ParseQuery<ParseUser> topUserQuery = ParseQuery.getQuery("User");
        topUserQuery.addDescendingOrder("Points");
        topUserQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null & objects.size() > 0){
                    ParseUser topUser = objects.get(0);
                    mTopUserName.setText(topUser.getUsername());
                    mTopUserName.setText("#1: "+topUser.getInt("Points")+" Points");
                }
            }
        });


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

                    MSongsList.setVisibility(View.VISIBLE);
                    if (MSongsList.getAdapter() == null) {
                        MSongsList.setAdapter(new SongAdapter(songs, MainActivity.this));
                    } else {
                        MSongsList.getAdapter().notifyDataSetChanged();
                    }

                    MTweetsList.setVisibility(View.VISIBLE);
                    if (MTweetsList.getAdapter() == null) {
                        MTweetsList.setAdapter(new TwitterAdapter(hashtags, MainActivity.this));
                    } else {
                        MTweetsList.getAdapter().notifyDataSetChanged();
                    }

                    mLoadingDialog.dismiss();
                }
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
                                if (!ParseUser.getCurrentUser().getBoolean("HasSeenEventMessage")){
                                    mPoints+=5;
                                    mPointsView.setText("Points: "+mPoints);
                                    ParseUser.getCurrentUser().put("Points", mPoints);
                                    ParseUser.getCurrentUser().put("HasSeenEventMessage", true);
                                    ParseUser.getCurrentUser().saveInBackground();
                                }
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

    @Override
    public void moveToTwitter(String hashtag) {
        updatePoints(10);

        Intent k = new Intent(android.content.Intent.ACTION_SEND);
        k.setType("text/plain");
        k.putExtra(Intent.EXTRA_SUBJECT, "Soshul Discussion");
        k.putExtra(Intent.EXTRA_TEXT, hashtag);
        startActivity(Intent.createChooser(k, "Soshul Discussion"));
    }

    private void updatePoints(int points) {
        //add 10 points to user
        mPoints+=points;
        mPointsView.setText("Points: "+mPoints);
        ParseUser.getCurrentUser().put("Points", mPoints);
        ParseUser.getCurrentUser().saveInBackground();
    }

    @Override
    public void playSong(Song song) {
        updatePoints(5);
        Intent launcher = new Intent( Intent.ACTION_VIEW, Uri.parse(song.getUri()) );
        startActivity(launcher);
    }
}
