package com.example.flixster1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster1.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {
    TextView tvTitle;
    TextView tvOverview;
    RatingBar tvRating;
    YouTubePlayerView youTubePlayerView;
    Movie movie;

    private   static  final  String YOUTUBE_API_KEy="AIzaSyCPVoZwgUCpTtEa1Gev37iZVPu7ZHQBnvU";
    public static final String VIDEOS_URL="https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.mvtitle);
        tvOverview = findViewById(R.id.mvOverview);
        tvRating = findViewById(R.id.mvrating);
        youTubePlayerView = findViewById(R.id.youtubePlayer);

//        String title = getIntent().getStringExtra("title");
        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvRating.setRating((float) movie.getVote_average());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if(results.length() == 0){
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity",youtubeKey);
                    initializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });


    }

    private void initializeYoutube(final String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEy, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity","onInitializationSuccess");
                if ((float) movie.getVote_average() > 5){
                    youTubePlayer.loadVideo(youtubeKey);

                }
                else{
                    youTubePlayer.cueVideo(youtubeKey);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}