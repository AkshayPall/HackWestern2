package memes.dank.com.hackwestern2;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rohitsharma on 2015-11-28.
 */
public class WolframAsyncTask extends AsyncTask<String, String, String> {

    public static final String APP_ID = "W7THVP-YKQ6JE4TPH";

    //tags
    private static final String IMG = "img";
    private static final String SOURCE = "src";

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        String resultToDisplay;
        ArrayList<WolframData> results = new ArrayList<>();
        InputStream in = null;

        // HTTP Get
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        // Parse XML
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            results = parseXML(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Simple logic to determine if the email is dangerous, invalid, or valid
        if (results == null) {
            resultToDisplay = "Exception Occured";
        } else {
            resultToDisplay = "Success!";
        }

        return resultToDisplay;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    private ArrayList<WolframData> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {

        int eventType = parser.getEventType();
        ArrayList<WolframData> mData = new ArrayList<>();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equals("Error")) {
                        System.out.println("Web API Error!");
                    } else if (name.equalsIgnoreCase(IMG)) {
                        WolframData newImage = new WolframData();
                        newImage.img = parser.nextText();
                        mData.add(newImage);
                        //get source string from name
                        Log.wtf("NOTEEEE", "TEXT: "+ newImage.img);
                    }

                    break;

                case XmlPullParser.END_TAG:
                    break;
            } // end switch

            eventType = parser.next();
        } // end while

        return mData;
    }
}
