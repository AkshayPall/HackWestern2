package memes.dank.com.hackwestern2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;
import static io.fabric.sdk.android.services.network.UrlUtils.urlEncode;

/**
 * Created by rohitsharma on 2015-11-28.
 */
public class TwitterAdapter extends RecyclerView.Adapter<TwitterAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView mHashtag;
        private final ImageView mImage;
//        private final TextView mTweet;

        public ViewHolder(View itemView){
            super(itemView);
            mHashtag = (TextView)itemView.findViewById(R.id.hashtag);
            mImage = (ImageView)itemView.findViewById(R.id.tweet_points);
        }
    }

    private List<String> mHashtags;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tweet_feed_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final String hashtag = mHashtags.get(i);
        //show hashtags

        viewHolder.mHashtag.setText("#"+hashtag);
        viewHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf("SW", "clicked is " + hashtag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHashtags.size();
    }

    public TwitterAdapter(List<String> hashtags){
        mHashtags = hashtags;
    }
}
