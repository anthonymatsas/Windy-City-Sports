package com.example.anthonymatsas.windycitysports;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by anthonymatsas on 4/24/16.
 */
public class FeedFragment extends Fragment{
    //Class level variables
    private ListView tweetList;
    private Team team;
    private ImageView image;

    public FeedFragment() {
        //empty ctor
    }

    public static FeedFragment newInstance(UUID id) {
        //Initialize fragment and get id of team
        FeedFragment feedFragment = new FeedFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("id", id);
        feedFragment.setArguments(bundle);

        return feedFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Set layout elements and inflate the fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        image = (ImageView)rootView.findViewById(R.id.profile_pic);
        tweetList = (ListView)rootView.findViewById(R.id.tweet_list);

        UUID id = (UUID)getArguments().getSerializable("id");

        team = TeamList.get(getContext().getApplicationContext()).getTeam(id);

        GetTweets getTweets = new GetTweets();
        getTweets.execute(team.getTwitterName());

        return rootView;
    }

    private class GetTweets extends AsyncTask<String, Void, String> {
        //Using Twitter4j library grab tweets and return results in a list
        ArrayList<String> results = new ArrayList<>();

        protected String doInBackground(String... team) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("YMZLzH6XdJjacZO1KoGg7Jwe0")
                    .setOAuthConsumerSecret(
                            "9WFRPYne45W8OopvI3mzQTrx2e5BZPTKYgMxhSVNOmUiuVrDcf")
                    .setOAuthAccessToken(
                            "49257150-aWdKve6fntESEnoNuGgVOYJ4BuMmAofobKcO1KPjS")
                    .setOAuthAccessTokenSecret(
                            "2huqRaw60zzGl3wNa7GKQ5JUrYQ6QrM6entmainW1DPvR")
                    .setIncludeEntitiesEnabled(true);

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            try {
                List<twitter4j.Status> statuses = null;
                statuses = twitter.getUserTimeline(team[0]);

                for(twitter4j.Status status : statuses) {
                    results.add(status.getText());
                }
            } catch (TwitterException te) {
                te.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s) {
            //Show list of tweets in listview
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, results);

            tweetList.setAdapter(arrayAdapter);

            //Change image based on team
            if(team.getImage().equals("bulls.png")) {
                image.setImageResource(R.drawable.bullspic);
            } else if(team.getImage().equals("sky.png")) {
                image.setImageResource(R.drawable.skypic);
            } else if(team.getImage().equals("cubs.png")) {
                image.setImageResource(R.drawable.cubspic);
            } else if(team.getImage().equals("sox.png")) {
                image.setImageResource(R.drawable.soxpic);
            } else if(team.getImage().equals("bears.png")) {
                image.setImageResource(R.drawable.bearspic);
            } else if(team.getImage().equals("hawks.png")) {
                image.setImageResource(R.drawable.hawkspic);
            }
        }
    }

}
