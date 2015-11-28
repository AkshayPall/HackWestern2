package memes.dank.com.hackwestern2;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;

/**
 * Created by Kenny on 2015-11-28.
 */
public class TwitterApi extends TwitterApiClient {
    public TwitterApi (TwitterSession session){
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
