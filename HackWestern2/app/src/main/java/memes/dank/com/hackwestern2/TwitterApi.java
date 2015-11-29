package memes.dank.com.hackwestern2;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.oauth.AppAuthToken;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Kenny on 2015-11-28.
 */
public class TwitterApi extends TwitterApiClient {

    public TwitterApi (TwitterSession session){
        super(session);
    }

    //creating the adapter for the endpoint
    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint("https://api.twitter.com")
            .build();

    private static final CustomService SERVICE = REST_ADAPTER.create(CustomService.class);

    public static CustomService getService (){
        return SERVICE;
    }
}

//for getting the available trending tweets
interface CustomService {
    @GET("/1.1/trends/place.json")
    void place(@Query("id") int id, retrofit.Callback<List<Trend>> cb);
    @POST("/oauth/request_token")
    void oauthCallback(String ouathToken, String ouathTokenSecret, boolean ouathCallbackConfirmed);

}
