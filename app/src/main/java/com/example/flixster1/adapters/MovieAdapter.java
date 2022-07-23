package com.example.flixster1.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster1.R;

import java.util.List;

import com.example.flixster1.models.Movie;



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

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);


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
                    .placeholder(R.drawable.terre)
                    .into(ivPoster);


        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView backdropPath;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            backdropPath = itemView.findViewById(R.id.imageView);

        }


        public void bind(Movie movie) {
            String imageUrl;
            imageUrl = movie.getBackdropPath();
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.terre)
                    .into(backdropPath);
        }
    }


    @Override
    public int getItemViewType(int position) {
        //More to come
        if (movies.get(position).getVote_average() < 5) {
            return 0;
        } else if (movies.get(position).getVote_average() >= 5) {
            return 1;

        }
        return position;
    }
}