package memes.dank.com.hackwestern2;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import javax.security.auth.callback.Callback;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Kenny on 2015-11-28.
 */
public class MyTwitterApiClient extends TwitterApiClient {
    public MyTwitterApiClient(TwitterSession session){
        super(session);
    }

    RestAdapter mRestAdapter = new RestAdapter.Builder()
            .setEndpoint("https://api.twitter.com")
            .build();

    public CustomService getCustomService(){
        return getService(CustomService.class);
    }
}

interface CustomService {
    @GET("/1.1/trends/available.json")
    void available(retrofit.Callback<List<Tweet>> cb);
}
