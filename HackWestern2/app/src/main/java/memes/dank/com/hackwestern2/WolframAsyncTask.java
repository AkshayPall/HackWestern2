package memes.dank.com.hackwestern2;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rohitsharma on 2015-11-28.
 */
public  class WolframAsyncTask extends AsyncTask<String, String, String> {

    private static final String APP_ID = "W7THVP-YKQ6JE4TPH";

    @Override
    protected String doInBackground(String... params) {
        String urlString=params[0]; // URL to call

        String resultToDisplay = "";

        InputStream in = null;

        // HTTP Get
        try {

            URL url = new URL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            in = new BufferedInputStream(urlConnection.getInputStream());

        } catch (Exception e ) {

            System.out.println(e.getMessage());

            return e.getMessage();

        }

        return resultToDisplay;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
