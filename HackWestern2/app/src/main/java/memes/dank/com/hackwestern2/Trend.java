package memes.dank.com.hackwestern2;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kenny on 2015-11-28.
 */
public class Trend {

    @SerializedName("name")
    private String mName;

    @SerializedName("query")
    private String mQuery;

    @SerializedName("url")
    private String mUrl;

    public String getName() {
        return mName;
    }

    public String getQuery() {
        return mQuery;
    }

    public String getUrl() {
        return mUrl;
    }
}
