package memes.dank.com.hackwestern2;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by rohitsharma on 2015-11-28.
 */
public class Location {

    private final String mMessage;
    private final String mName;
    private final String mCountry;

    public Location (ParseObject location){
        ParseGeoPoint latlng = location.getParseGeoPoint("GeoLocation");
        mMessage = location.getString("EventMessage");
        mName = location.getString("RegionName");
        mCountry = location.getString("Country");
    }

    public String getMessage() {
        return mMessage;
    }

    public String getName() {
        return mName;
    }

    public String getCountry() {
        return mCountry;
    }
}
