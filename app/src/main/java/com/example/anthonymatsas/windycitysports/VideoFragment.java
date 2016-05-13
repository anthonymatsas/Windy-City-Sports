package com.example.anthonymatsas.windycitysports;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by anthonymatsas on 4/28/16.
 */
public class VideoFragment extends android.support.v4.app.Fragment{
    //class level variables
    private Team team;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> videoIDs = new ArrayList<>();
    private String channelID = "";


    public VideoFragment() {
        //Empt ctor
    }

    public static VideoFragment newInstance(UUID id) {
        //Get id for each team and return fragment
        VideoFragment videoFragment = new VideoFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("id", id);
        videoFragment.setArguments(bundle);

        return videoFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate view and set layout for each teams video tab
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        UUID id = (UUID)getArguments().getSerializable("id");

        team = TeamList.get(rootView.getContext()).getTeam(id);
        channelID = team.getYoutubeUsrName();
        String trimmedChannelID = channelID.trim();
        //FOR DEBUG ONLY
        //Log.i("Channel ID:", channelID + "");
        String urlString = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyBtMXmlA5u50POjWjhZVOONmZ_IACNbGW0&channelId=" + trimmedChannelID + "&part=snippet,id&order=date&maxResults=5&alt=json";

        GetVideoData getVideoData = new GetVideoData();
        getVideoData.execute(urlString);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(rootView.getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerAdapter(rootView.getContext(), videoIDs);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private class GetVideoData extends AsyncTask<String, Void, String>{
        //Grab data from YouTubeAPI using JSON
        public ArrayList<String> Ids = new ArrayList<>();
        protected String doInBackground(String... params) {
            String jsnData = "";

            try{
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                String line = "";
                line = bufferedReader.readLine();
                while(line != null) {
                    jsnData += line;
                    line = bufferedReader.readLine();
                }

                httpURLConnection.disconnect();

                JSONObject json = new JSONObject(jsnData);
                JSONArray jsonArray = json.getJSONArray("items");
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    JSONObject snippet = item.getJSONObject("id");
                    String videoId = snippet.getString("videoId");
                    Ids.add(videoId);
                }

            }catch (Exception e){
                //DEBUG
                Log.e("Error", e.getMessage().toString());
            }
            return null;
        }

        protected void onPostExecute(String s) {
            videoIDs = Ids;
        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VideoInfoHolder> {
        //RecyclerAdapter to hold youtube player view
        ArrayList<String> VideoID = new ArrayList<>();
        Context ctx;

        public RecyclerAdapter(Context context, ArrayList<String> ids) {
            this.ctx = context;
            this.VideoID = ids;
        }

        @Override
        public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row_item, parent, false);
            return new VideoInfoHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final VideoInfoHolder holder, final int position) {

            final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                }

                @Override
                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                    holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                }
            };

            holder.youTubeThumbnailView.initialize("AIzaSyBtMXmlA5u50POjWjhZVOONmZ_IACNbGW0", new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {

                    youTubeThumbnailLoader.setVideo(VideoID.get(position));
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        public void onThumbnailLoaded(YouTubeThumbnailView ytv, String s) {
                            youTubeThumbnailLoader.release();
                        }

                        public void onThumbnailError(YouTubeThumbnailView youTubeView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                        }
                    });

                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return VideoID.size();
        }

        public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            //Youtube player holder
            protected LinearLayout relativeLayoutOverYouTubeThumbnailView;
            YouTubeThumbnailView youTubeThumbnailView;
            protected ImageView playButton;

            public VideoInfoHolder(View itemView) {
                super(itemView);
                playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
                playButton.setOnClickListener(this);
                relativeLayoutOverYouTubeThumbnailView = (LinearLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
                youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
            }

            @Override
            public void onClick(View v) {
                //Launch video intent when clicking on video thumbnail
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, "AIzaSyBtMXmlA5u50POjWjhZVOONmZ_IACNbGW0", VideoID.get(getAdapterPosition()));
                ctx.startActivity(intent);
                ((Activity) ctx).finish();
            }
        }
    }
}