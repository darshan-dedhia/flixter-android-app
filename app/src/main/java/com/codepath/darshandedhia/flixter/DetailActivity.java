package com.codepath.darshandedhia.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.darshandedhia.flixter.constants.Constants;
import com.codepath.darshandedhia.flixter.databinding.ActivityDetailBinding;
import com.codepath.darshandedhia.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    public static final String youtubeAPIKey = "AIzaSyDC15qHm06kSN3c4qeuNzVdlHyWHyWAKOc";
    public static String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key="+ Constants.API_KEY +"&language=en-US";

    TextView txtTitle;
    TextView txtDescription;
    RatingBar ratingBar;
    TextView releaseDate;
    TextView rating;
    ImageView moviePoster;
    YouTubePlayerView youtubePlayerView;
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        txtTitle = binding.textView;
        txtDescription = binding.txtDescription;
        ratingBar = binding.ratingBar;
        releaseDate = binding.txtReleaseDate;
        rating = binding.txtAverageRating;
        moviePoster = binding.moviePoster;
        youtubePlayerView = binding.player;

        final Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        txtTitle.setText(movie.getTitle());
        txtDescription.setText(movie.getOverview());
        ratingBar.setRating((float)movie.getRating());
        rating.setText(movie.getRating() + "/10");
        releaseDate.setText(movie.getReleaseDate());
        String image = movie.getPosterPath();
        Glide.with(this).load(image).transform(new RoundedCornersTransformation(30, 10)).into(this.moviePoster);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieid()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    }
                    String youtubeKey =  results.getJSONObject(0).getString("key");
                    initializeYoutube(youtubeKey, movie);
                    Log.d("YoutubeKey", youtubeKey);
                } catch (JSONException e) {
                    Log.e("DetailActivity", "Error occured while fetching youtube link");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }

    private void initializeYoutube(final String youtubeKey, final Movie mov) {
        youtubePlayerView.initialize(youtubeAPIKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (mov.getRating() >= 6) {
                    youTubePlayer.loadVideo(youtubeKey);
                } else {
                    youTubePlayer.cueVideo(youtubeKey);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("DetailActivitYoutubeInit", "Error occurred while initializing youtube");
            }
        });
    }
}