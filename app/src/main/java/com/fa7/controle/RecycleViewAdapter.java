package com.fa7.controle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fa7.modelo.Movie;
import com.fa7.popularmovies.R;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolderMovies> {

    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private ItemClickListener clickListener;

    public RecycleViewAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolderMovies onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolderMovies(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovies holder, int position) {
        final Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        //holder.cityImage.setImageDrawable(context.getResources().getDrawable(movie.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolderMovies extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView title;
        private com.fa7.controle.ItemClickListener clickListener;

        public ViewHolderMovies(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
}


/*
private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private ViewHolderMovies.ItemClickListener clickListener;

    public RecycleViewAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(rowLayout, parent, false);
        ViewHolderMovies viewHolderMovies = new ViewHolderMovies(view);

        return viewHolderMovies;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderMovies viewHolderMovies = (ViewHolderMovies) holder;
        Movie movie = movies.get(position);
        viewHolderMovies.title.setText(movie.getTitle());
        //viewHolderMovies.overview.setText(movie.getOverview());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setClickListener(ViewHolderMovies.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
*/