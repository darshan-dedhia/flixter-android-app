package com.codepath.darshandedhia.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.darshandedhia.flixter.R;
import com.codepath.darshandedhia.flixter.models.Movie;

import java.util.List;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.movieTitle = itemView.findViewById(R.id.movieTitle);
            this.movieDescription = itemView.findViewById(R.id.movieDescription);
            this.moviePoster = itemView.findViewById(R.id.imageView);
        }

        public void bind(Movie movie) {
            this.movieTitle.setText(movie.getTitle());
            this.movieDescription.setText(movie.getOverview());
            String image;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                image = movie.getBackdropPath();
            } else {
                image = movie.getPosterPath();
            }
            Glide.with(context).load(image).placeholder(R.drawable.ic_launcher_foreground).into(this.moviePoster);
        }
    }
}
