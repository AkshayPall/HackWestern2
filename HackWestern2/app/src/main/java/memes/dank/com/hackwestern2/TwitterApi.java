package memes.dank.com.hackwestern2;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Kenny on 2015-11-28.
 */
public class TwitterApi extends TwitterApiClient {

    public TwitterApi (TwitterSession session){
        super(session);
    }

    //creating the adapter for the endpoint
    RestAdapter mRestAdapter = new RestAdapter.Builder()
            .setEndpoint("https://api.twitter.com")
            .build();

    public CustomService getCustomService(){
        return getService(CustomService.class);
    }
}

//for getting the available trending tweets
interface CustomService {
    @GET("/1.1/trends/available.json")
    void available(@Query("name")retrofit.Callback<List<Tweet>> cb);
}
