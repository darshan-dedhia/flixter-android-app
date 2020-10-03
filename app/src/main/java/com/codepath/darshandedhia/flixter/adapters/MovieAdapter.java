package com.codepath.darshandedhia.flixter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.darshandedhia.flixter.DetailActivity;
import com.codepath.darshandedhia.flixter.R;
import com.codepath.darshandedhia.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView movieTitle;
        TextView movieDescription;
        ImageView moviePoster;
        RelativeLayout container;
        View detailActivity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.movieTitle = itemView.findViewById(R.id.movieTitle);
            this.movieDescription = itemView.findViewById(R.id.movieDescription);
            this.moviePoster = itemView.findViewById(R.id.imageView);
            this.container = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(final Movie movie) {
            this.movieTitle.setText(movie.getTitle());
            this.movieDescription.setText(movie.getOverview());
            String image;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                image = movie.getBackdropPath();
            } else {
                image = movie.getPosterPath();
            }
            Glide.with(context).load(image).placeholder(R.drawable.ic_launcher_foreground).transform(new RoundedCornersTransformation(30, 10)).into(this.moviePoster);

            if (movie.getRating() >= 6) {

            }

            this.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    Pair<View, String> p1 = Pair.create((View) moviePoster, "profile");
                    Pair<View, String> p2 = Pair.create((View) movieDescription, "movieDesc");
                    Pair<View, String> p3 = Pair.create((View) movieTitle, "movieTitle");
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context,p1, p2, p3);
                    context.startActivity(i, options.toBundle());
                }
            });
        }
    }
}
