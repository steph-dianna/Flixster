package com.example.flixster1.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster1.DetailActivity;
import com.example.flixster1.R;

import java.util.List;

//import com.example.flixster1.databinding.ItemMovieBinding;
import com.example.flixster1.models.Movie;

import org.parceler.Parcels;


public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from xml and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("MovieAdapter", "onCreateViewHolder ");
//        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
//        return new ViewHolder(movieView);
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 1) {
            View film = inflater.inflate(R.layout.plus_populaire, parent, false);
            viewHolder = new ViewHolder1(film);
        } else {
            View film2 = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(film2);
        }


        return  viewHolder;
    }

    // Involves populating data into the item in the list
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i("MovieAdapter", "onBindViewHolder" + position);
        //Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Find the movie data into the ViewHolder
        if (holder.getItemViewType() == 1){
            ViewHolder1 view1 = (ViewHolder1) holder;
            view1.bind(movie);
        }else {
            ViewHolder view2 = (ViewHolder) holder;
            view2.bind(movie);
        }
    }

    //Returns the local count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        int radius;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            radius = 50;


        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = back drop image
                imageUrl = movie.getBackdropPath();
            } else {
                // else imageUrl = poster image
                imageUrl = movie.getPosterPath();
            }
            Glide.with(context)
                    .load(imageUrl)
                    .fitCenter() // scale image to fill the entire ImageView
                    .transform(new RoundedCorners(radius))
                    .placeholder(R.drawable.terre)
                    .into(ivPoster);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // first parameter is the context, second is the class of the activity to launch
                    Intent intent = new Intent(context, DetailActivity.class);
                    // put "extras" into the bundle for access in the second activity
                    intent.putExtra("movie", Parcels.wrap(movie));
                    // brings up the second activity
                    ActivityOptions options = ActivityOptions.
                            makeSceneTransitionAnimation((Activity)context, ivPoster, "transition");
                    context.startActivity(intent, options.toBundle());
//                    context.startActivity(intent);

                }
            });

        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView backdropPath;
        RelativeLayout container2;
        int radius;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            backdropPath = itemView.findViewById(R.id.imageView);
            container2 = itemView.findViewById(R.id.container2);
            radius = 50;

        }


        public void bind(Movie movie) {
            String imageUrl;
            imageUrl = movie.getBackdropPath();
            Glide.with(context)
                    .load(imageUrl)
                    .fitCenter() // scale image to fill the entire ImageView
                    .transform(new RoundedCorners(radius))
                    .placeholder(R.drawable.terre)
                    .into(backdropPath);

            container2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // first parameter is the context, second is the class of the activity to launch
                    Intent intent2 = new Intent(context, DetailActivity.class);
                    // put "extras" into the bundle for access in the second activity
                    intent2.putExtra("movie", Parcels.wrap(movie));
                    // brings up the second activity
                    ActivityOptions options2 = ActivityOptions.
                            makeSceneTransitionAnimation((Activity)context, backdropPath, "transition");
                    context.startActivity(intent2, options2.toBundle());
//                    context.startActivity(intent2);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        //More to come
        if (movies.get(position).getVote_average() < 7.5) {
            return 0;
        } else if (movies.get(position).getVote_average() >= 7.5) {
            return 1;

        }
        return position;
    }
}