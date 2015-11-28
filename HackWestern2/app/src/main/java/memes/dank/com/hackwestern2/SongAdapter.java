package memes.dank.com.hackwestern2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rohitsharma on 2015-11-28.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView mName;
        private final TextView mArtist;
        private final TextView mPlays;

        public ViewHolder(View itemView){
            super(itemView);
            mName = (TextView)itemView.findViewById(R.id.song_name);
            mArtist = (TextView)itemView.findViewById(R.id.song_artist);
            mPlays = (TextView)itemView.findViewById(R.id.song_plays);
        }
    }

    private List<Song> mSongs;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_feed_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Song song = mSongs.get(i);
        viewHolder.mName.setText(song.getName());
        viewHolder.mArtist.setText(song.getArtist());
        viewHolder.mPlays.setText(song.getPlays()+" plays");
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public SongAdapter(List<Song> songs){
        mSongs = songs;
    }
}
