package com.codepath.darshandedhia.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.darshandedhia.flixter.adapters.MovieAdapter;
import com.codepath.darshandedhia.flixter.constants.Constants;
import com.codepath.darshandedhia.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Constants.NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(Constants.TAG, "Success fetching NOW_PLAYING");
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    movies.addAll(Movie.movieList(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(Constants.TAG, "List Size: " + movies.size());
                } catch (JSONException e) {
                    Log.e(Constants.TAG, "Error while parsing JSON results" + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(Constants.TAG, "Error occured while fetching NOW_PLAYING");
            }
        });
    }
}