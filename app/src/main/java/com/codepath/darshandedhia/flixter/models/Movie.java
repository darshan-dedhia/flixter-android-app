package com.codepath.darshandedhia.flixter.models;

import com.codepath.darshandedhia.flixter.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String posterPath;
    private String backdropPath;
    private String title;
    private String overview;

    public Movie (JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.title = jsonObject.getString("title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
    }

    public static List<Movie> movieList(JSONArray movies) throws JSONException {
        List<Movie> movieList = new ArrayList<>();
        for (int i=0; i < movies.length(); i++) {
            movieList.add(new Movie(movies.getJSONObject(i)));
        }
        return movieList;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/" + Constants.IMAGE_SIZE + "/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/" + Constants.IMAGE_SIZE + "/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
